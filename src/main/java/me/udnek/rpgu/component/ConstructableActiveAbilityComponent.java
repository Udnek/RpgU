package me.udnek.rpgu.component;

import me.udnek.itemscoreu.customitem.CustomItem;
import me.udnek.itemscoreu.util.LoreBuilder;
import me.udnek.rpgu.attribute.Attributes;
import me.udnek.rpgu.lore.ActiveAbilityLorePart;
import me.udnek.rpgu.mechanic.damaging.Damage;
import me.udnek.rpgu.util.Utils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public interface ConstructableActiveAbilityComponent<ActivationContext, DamageContext> extends ActiveAbilityComponent<ActivationContext>, ActiveAbilityProperties<DamageContext> {

    default @NotNull Damage calculateDamage(@NotNull DamageContext context){
        return Objects.requireNonNull(getDamage()).calculate(context);
    }

    @Override
    default int getBaseCooldown(){return 0;}
    default int getCooldown(@NotNull Player player){
        int baseCooldown = getBaseCooldown();
        if (baseCooldown <= 0) return 0;
        return (int) Attributes.COOLDOWN_TIME.calculateWithBase(player, baseCooldown);
    }

    @Override
    default double getBaseCastRange(){return 0;}
    default double getCastRange(@NotNull Player player){
        double baseCastRange = getBaseCastRange();
        if (baseCastRange <= 0) return 0;
        return Attributes.CAST_RANGE.calculateWithBase(player, baseCastRange);
    }

    @Override
    default double getBaseAreaOfEffect(){return 0;}
    default double getAreaOfEffect(@NotNull Player player){
        double baseAreaOfEffect = getBaseAreaOfEffect();
        if (baseAreaOfEffect <= 0) return 0;
        return Attributes.AREA_OF_EFFECT.calculateWithBase(player, baseAreaOfEffect);
    }

    @Override
    default int getBaseCastTime(){return 0;}

    default void addLoreLines(@NotNull ActiveAbilityLorePart componentable){
        addDamageLine(componentable);
        addCooldownLine(componentable);
        addCastRangeLine(componentable);
        addCastTimeLine(componentable);
        addAreaOfEffectLine(componentable);
    }
    default void addCooldownLine(@NotNull ActiveAbilityLorePart componentable){
        Utils.consumeIfPositive(getBaseCooldown(), value ->
                componentable.addWithFormat(Component.translatable("active_ability.rpgu.cooldown", Component.text(me.udnek.itemscoreu.util.Utils.roundToTwoDigits(value)))));
    }
    default void addAreaOfEffectLine(@NotNull ActiveAbilityLorePart componentable){
        Utils.consumeIfPositive(getBaseAreaOfEffect(), value ->
                componentable.addWithFormat(Component.translatable("active_ability.rpgu.area_of_effect", Component.text(me.udnek.itemscoreu.util.Utils.roundToTwoDigits(value)))));
    }
    default void addCastRangeLine(@NotNull ActiveAbilityLorePart componentable){
        Utils.consumeIfPositive(getBaseCastRange(), value ->
            componentable.addWithFormat(Component.translatable("active_ability.rpgu.cast_range", Component.text(me.udnek.itemscoreu.util.Utils.roundToTwoDigits(value)))));
    }
    default void addCastTimeLine(@NotNull ActiveAbilityLorePart componentable){
        Utils.consumeIfPositive(getBaseCastTime(), value ->
                componentable.addWithFormat(Component.translatable("active_ability.rpgu.cast_time", Component.text(me.udnek.itemscoreu.util.Utils.roundToTwoDigits(value/20d)))));
    }
    default void addDamageLine(@NotNull ActiveAbilityLorePart componentable){
        me.udnek.itemscoreu.util.Utils.consumeIfNotNull(getDamage(), value ->
                value.description(componentable::addWithFormat));
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
    default void activate(@NotNull CustomItem customItem, @NotNull Player player, @NotNull ActivationContext activationContext){
        if (customItem.hasCooldown(player)) return;
        ActionResult result = action(customItem, player, activationContext);
        if (!(result.applyCooldown)) return;
        Utils.consumeIfPositive(getCooldown(player), cooldown -> customItem.setCooldown(player, cooldown));
    }

    @NotNull ConstructableActiveAbilityComponent.ActionResult action(@NotNull CustomItem customItem, @NotNull Player player, @NotNull ActivationContext activationContext);

    enum ActionResult {
        APPLY_COOLDOWN(true),
        NO_COOLDOWN(false);

        public final boolean applyCooldown;
        ActionResult(boolean applyCooldown){
            this.applyCooldown = applyCooldown;
        }
    }
}
