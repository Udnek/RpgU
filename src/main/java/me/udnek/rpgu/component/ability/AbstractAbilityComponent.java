package me.udnek.rpgu.component.ability;

import me.udnek.itemscoreu.customcomponent.OptimizedComponentHolder;
import me.udnek.itemscoreu.customitem.CustomItem;
import me.udnek.rpgu.component.ComponentTypes;
import me.udnek.rpgu.component.ability.active.ConstructableActiveAbilityComponent;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractAbilityComponent<ActivationContext> extends OptimizedComponentHolder<AbilityComponent<?>> implements AbilityComponent<ActivationContext>{

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

    public abstract @NotNull ActionResult action(@NotNull CustomItem customItem, @NotNull Player player, @NotNull ActivationContext activationContext);

    public enum ActionResult {
        FULL_COOLDOWN,
        PENALTY_COOLDOWN,
        NO_COOLDOWN
    }
}
