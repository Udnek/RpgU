package me.udnek.rpgu.component.ability;

import me.udnek.itemscoreu.customcomponent.CustomComponent;
import me.udnek.itemscoreu.customcomponent.OptimizedComponentHolder;
import me.udnek.itemscoreu.customitem.CustomItem;
import me.udnek.itemscoreu.util.LogUtils;
import me.udnek.itemscoreu.util.LoreBuilder;
import me.udnek.rpgu.component.ComponentTypes;
import me.udnek.rpgu.component.ability.property.AbilityProperty;
import me.udnek.rpgu.lore.ActiveAbilityLorePart;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public abstract class ConstructableActiveAbilityComponent<ActivationContext> extends OptimizedComponentHolder<ActiveAbilityComponent<?>> implements ActiveAbilityComponent<ActivationContext> {

    public void addLoreLines(@NotNull ActiveAbilityLorePart componentable){
        for (CustomComponent<ActiveAbilityComponent<?>> component : getComponents()) {
            if (component instanceof AbilityProperty<?,?> abilityProperty){
                abilityProperty.describe(componentable);
            }
        }
    }

    @Override
    public void getLore(@NotNull LoreBuilder loreBuilder){
        ActiveAbilityLorePart componentable = new ActiveAbilityLorePart();
        loreBuilder.set(55, componentable);
        componentable.addEmptyAboveHeader();
        componentable.setHeader(Component.translatable("active_ability.rpgu.title").color(NamedTextColor.GREEN).decoration(TextDecoration.ITALIC, false));
        addLoreLines(componentable);
    }

    @Override
    public void activate(@NotNull CustomItem customItem, @NotNull Player player, @NotNull ActivationContext activationContext){
        if (customItem.hasCooldown(player)) return;
        ActionResult result = action(customItem, player, activationContext);
        if (result == ActionResult.FULL_COOLDOWN || result == ActionResult.PENALTY_COOLDOWN){
            double cooldown = getComponents().getOrDefault(ComponentTypes.ABILITY_COOLDOWN).get(player);
            if (result == ActionResult.PENALTY_COOLDOWN) cooldown = cooldown * getComponents().getOrDefault(ComponentTypes.ABILITY_MISS_USAGE_COOLDOWN_MULTIPLIER).get(player);
            if (cooldown > 0) customItem.setCooldown(player, (int) cooldown);
        }
    }

    public abstract @NotNull ConstructableActiveAbilityComponent.ActionResult action(@NotNull CustomItem customItem, @NotNull Player player, @NotNull ActivationContext activationContext);

    public enum ActionResult {
        FULL_COOLDOWN,
        PENALTY_COOLDOWN,
        NO_COOLDOWN
    }
}
