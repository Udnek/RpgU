package me.udnek.rpgu.component;

import me.udnek.itemscoreu.customitem.CustomItem;
import me.udnek.itemscoreu.util.LoreBuilder;
import me.udnek.rpgu.attribute.Attributes;
import me.udnek.rpgu.lore.ActiveAbilityLorePart;
import me.udnek.rpgu.util.Utils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public interface ConstructableActiveAbilityComponent<Context> extends ActiveAbilityComponent<Context> {

    int getBaseCooldown();
    default int getCooldown(@NotNull Player player){
        int baseCooldown = getBaseCooldown();
        if (baseCooldown <= 0) return 0;
        return (int) Attributes.COOLDOWN_TIME.calculateWithBase(player, baseCooldown);
    }

    int getBaseCastRange();
    default int getCastRange(@NotNull Player player){
        int baseCastRange = getBaseCastRange();
        if (baseCastRange <= 0) return 0;
        return (int) Attributes.CAST_RANGE.calculateWithBase(player, baseCastRange);
    }

    int getBaseCastTime();

    default void addLoreLines(@NotNull ActiveAbilityLorePart componentable){
        addCooldownLine(componentable);
        addCastRangeLine(componentable);
        addCastTimeLine(componentable);
    }
    default void addCooldownLine(@NotNull ActiveAbilityLorePart componentable){
        Utils.consumeIfPositive(getBaseCooldown(), cooldown ->
                componentable.addWithFormat(Component.translatable("active_ability.rpgu.cooldown", Component.text(me.udnek.itemscoreu.util.Utils.roundToTwoDigits(cooldown)))));
    }
    default void addCastRangeLine(@NotNull ActiveAbilityLorePart componentable){
        Utils.consumeIfPositive(getBaseCastRange(), value ->
            componentable.addWithFormat(Component.translatable("active_ability.rpgu.cast_range", Component.text(me.udnek.itemscoreu.util.Utils.roundToTwoDigits(value)))));
    }
    default void addCastTimeLine(@NotNull ActiveAbilityLorePart componentable){
        Utils.consumeIfPositive(getBaseCastTime(), value ->
                componentable.addWithFormat(Component.translatable("active_ability.rpgu.cast_time", Component.text(me.udnek.itemscoreu.util.Utils.roundToTwoDigits(value/20d)))));
    }

    @Override
    default void getLore(@NotNull LoreBuilder loreBuilder){
        ActiveAbilityLorePart componentable = new ActiveAbilityLorePart();
        loreBuilder.set(55, componentable);
        componentable.addEmptyAboveHeader();
        componentable.setHeader(Component.translatable("active_ability.rpgu.title").color(NamedTextColor.GREEN).decoration(TextDecoration.ITALIC, false));
        addLoreLines(componentable);
    }

    @Override
    default void activate(@NotNull CustomItem customItem, @NotNull Player player, @NotNull Context context){
        if (customItem.hasCooldown(player)) return;
        ActionResult result = action(customItem, player, context);
        if (!(result.applyCooldown)) return;
        Utils.consumeIfPositive(getCooldown(player), cooldown -> {
            customItem.setCooldown(player, cooldown);
        });
    }

    @NotNull ConstructableActiveAbilityComponent.ActionResult action(@NotNull CustomItem customItem, @NotNull Player player, @NotNull Context context);

    enum ActionResult {
        APPLY_COOLDOWN(true),
        NO_COOLDOWN(false);

        public final boolean applyCooldown;
        ActionResult(boolean applyCooldown){
            this.applyCooldown = applyCooldown;
        }
    }
}
