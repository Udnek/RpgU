package me.udnek.rpgu.component;

import me.udnek.itemscoreu.customcomponent.AbstractComponentHolder;
import me.udnek.itemscoreu.customcomponent.ComponentHolder;
import me.udnek.itemscoreu.customcomponent.CustomComponent;
import me.udnek.itemscoreu.customequipmentslot.slot.SingleSlot;
import me.udnek.itemscoreu.customequipmentslot.universal.UniversalInventorySlot;
import me.udnek.itemscoreu.customitem.CustomItem;
import me.udnek.itemscoreu.util.Either;
import me.udnek.rpgu.component.ability.AbilityComponent;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.jetbrains.annotations.NotNull;

public abstract class AbilityActivationHandler<ActivationContext> extends AbstractComponentHolder<AbilityComponent<?>, CustomComponent<AbilityComponent<?>>> {

    public void activate(@NotNull CustomItem customItem, @NotNull LivingEntity livingEntity, boolean canselIfCooldown,
                         @NotNull Either<UniversalInventorySlot, SingleSlot> slot, @NotNull ActivationContext activationContext){
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
    public void activate(@NotNull CustomItem customItem, @NotNull LivingEntity livingEntity, @NotNull Either<UniversalInventorySlot, SingleSlot> slot, @NotNull ActivationContext activationContext){
        activate(customItem, livingEntity, false, slot, activationContext);
    }

    public abstract @NotNull ActionResult action(@NotNull CustomItem customItem, @NotNull LivingEntity livingEntity,
                                                 @NotNull Either<UniversalInventorySlot, SingleSlot> slot, @NotNull ActivationContext activationContext);

    public enum ActionResult {
        FULL_COOLDOWN,
        PENALTY_COOLDOWN,
        NO_COOLDOWN
    }
}
