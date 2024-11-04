package me.udnek.rpgu.component;

import me.udnek.itemscoreu.customitem.CustomItem;
import me.udnek.itemscoreu.util.LoreBuilder;
import me.udnek.itemscoreu.util.Utils;
import me.udnek.rpgu.attribute.Attributes;
import me.udnek.rpgu.lore.ActiveAbilityLorePart;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface ConstructableActiveAbilityComponent<Context> extends ActiveAbilityComponent<Context> {


    @Nullable Integer getBaseCooldown();
    default @Nullable Integer getCooldown(@NotNull Player player){
        Integer baseCooldown = getBaseCooldown();
        if (baseCooldown == null) return null;
        return (int) Attributes.COOLDOWN_TIME.calculateWithBase(player, baseCooldown);
    }

    int getBaseCastRange();
    default int getCastRange(@NotNull Player player){
        int baseCastRange = getBaseCastRange();
        if (baseCastRange <= 0) return 0;
        return (int) Attributes.CAST_RANGE.calculateWithBase(player, baseCastRange);
    }

    default void addLoreLines(@NotNull ActiveAbilityLorePart componentable){
        addCooldownLine(componentable);
        addCastRangeLine(componentable);
    }
    default void addCooldownLine(@NotNull ActiveAbilityLorePart componentable){
        Utils.consumeIfNotNull(getBaseCooldown(), cooldown ->
                componentable.addWithFormat(Component.translatable("active_ability.rpgu.cooldown", Component.text(Utils.roundToTwoDigits(cooldown)))));

    }
    default void addCastRangeLine(@NotNull ActiveAbilityLorePart componentable){
        if (getBaseCastRange() > 0){
            componentable.addWithFormat(Component.translatable("active_ability.rpgu.cast_range", Component.text(Utils.roundToTwoDigits(getBaseCastRange()))));
        }
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
        ActivationResult result = action(customItem, player, context);
        if (!(result.applyCooldown)) return;
        Utils.consumeIfNotNull(getCooldown(player), cooldown -> customItem.setCooldown(player, cooldown));
    }

    @NotNull ActivationResult action(@NotNull CustomItem customItem, @NotNull Player player, @NotNull Context context);

    enum ActivationResult{
        SUCCESSFUL(true),
        UNSUCCESSFUL(false),
        UNSUCCESSFUL_WITH_COOLDOWN_PENALTY(true);

        public final boolean applyCooldown;
        ActivationResult(boolean applyCooldown){
            this.applyCooldown = applyCooldown;
        }
    }
}
