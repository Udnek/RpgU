package me.udnek.rpgu.component.ability;

import me.udnek.itemscoreu.customcomponent.AbstractComponentHolder;
import me.udnek.itemscoreu.customequipmentslot.UniversalInventorySlot;
import me.udnek.itemscoreu.customitem.CustomItem;
import me.udnek.rpgu.component.ComponentTypes;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractAbilityComponent<ActivationContext> extends AbstractComponentHolder<AbilityComponent<?>> implements AbilityComponent<ActivationContext>{

    @Override
    public void activate(@NotNull CustomItem customItem, @NotNull LivingEntity livingEntity, boolean canselIfCooldown,
                         @NotNull UniversalInventorySlot slot, @NotNull ActivationContext activationContext){
        if (!(livingEntity instanceof Player player)) {
            action(customItem, livingEntity, slot, activationContext);
            return;
        }
        if (customItem.hasCooldown(player)) {
            if (activationContext instanceof Cancellable cancellable && canselIfCooldown){
                cancellable.setCancelled(true);
            }
            return;
        }
        ActionResult result = action(customItem, player, slot, activationContext);
        if (result == ActionResult.FULL_COOLDOWN || result == ActionResult.PENALTY_COOLDOWN){
            double cooldown = getComponents().getOrDefault(ComponentTypes.ABILITY_COOLDOWN).get(player);
            if (result == ActionResult.PENALTY_COOLDOWN) cooldown = cooldown * getComponents().getOrDefault(ComponentTypes.ABILITY_MISS_USAGE_COOLDOWN_MULTIPLIER).get(player);
            if (cooldown > 0) customItem.setCooldown(player, (int) cooldown);
        }
    }

    public abstract @NotNull ActionResult action(@NotNull CustomItem customItem, @NotNull LivingEntity livingEntity,
                                                 @NotNull UniversalInventorySlot slot, @NotNull ActivationContext activationContext);

    public enum ActionResult {
        FULL_COOLDOWN,
        PENALTY_COOLDOWN,
        NO_COOLDOWN
    }
}
