package me.udnek.rpgu.component;

import me.udnek.coreu.custom.component.AbstractComponentHolder;
import me.udnek.coreu.custom.component.ComponentHolder;
import me.udnek.coreu.custom.component.CustomComponent;
import me.udnek.coreu.custom.equipmentslot.slot.CustomEquipmentSlot.Single;
import me.udnek.coreu.custom.equipmentslot.universal.UniversalInventorySlot;
import me.udnek.coreu.custom.item.CustomItem;
import me.udnek.coreu.util.Either;
import me.udnek.rpgu.component.ability.AbilityComponent;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.jetbrains.annotations.NotNull;

public abstract class AbilityActivationHandler<ActivationContext> extends AbstractComponentHolder<AbilityComponent<?>, CustomComponent<AbilityComponent<?>>> {

    public void activate(@NotNull CustomItem custom.Item, @NotNull LivingEntity livingEntity, boolean canselIfCooldown,
                         @NotNull Either<UniversalInventorySlot, CustomEquipmentSlot.Single> slot, @NotNull ActivationContext activationContext){
        if (!(livingEntity instanceof Player player)) {
            action(custom.Item, livingEntity, slot, activationContext);
            return;
        }
        if (custom.Item.hasCooldown(player)) {
            if (activationContext instanceof Cancellable cancellable && canselIfCooldown){
                cancellable.setCancelled(true);
            }
            return;
        }
        ActionResult result = action(custom.Item, player, slot, activationContext);
        if (result == ActionResult.FULL_COOLDOWN || result == ActionResult.PENALTY_COOLDOWN){
            double cooldown = getComponents().getOrDefault(ComponentTypes.ABILITY_COOLDOWN).get(player);
            if (result == ActionResult.PENALTY_COOLDOWN) cooldown = cooldown * getComponents().getOrDefault(ComponentTypes.ABILITY_MISS_USAGE_COOLDOWN_MULTIPLIER).get(player);
            if (cooldown > 0) custom.Item.setCooldown(player, (int) cooldown);
        }
    }
    public void activate(@NotNull CustomItem custom.Item, @NotNull LivingEntity livingEntity, @NotNull Either<UniversalInventorySlot, CustomEquipmentSlot.Single> slot, @NotNull ActivationContext activationContext){
        activate(custom.Item, livingEntity, false, slot, activationContext);
    }

    public abstract @NotNull ActionResult action(@NotNull CustomItem custom.Item, @NotNull LivingEntity livingEntity,
                                                 @NotNull Either<UniversalInventorySlot, CustomEquipmentSlot.Single> slot, @NotNull ActivationContext activationContext);

    public enum ActionResult {
        FULL_COOLDOWN,
        PENALTY_COOLDOWN,
        NO_COOLDOWN
    }
}
