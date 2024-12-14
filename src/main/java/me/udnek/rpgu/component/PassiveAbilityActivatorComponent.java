package me.udnek.rpgu.component;

import me.udnek.itemscoreu.customequipmentslot.CustomEquipmentSlot;
import me.udnek.itemscoreu.customitem.CustomItem;
import me.udnek.rpgu.component.ability.passive.EquippableActivatablePassiveComponent;
import me.udnek.rpgu.component.ability.passive.PassiveAbilityComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public abstract class PassiveAbilityActivatorComponent implements EquippableItemComponent  {

    public abstract int getTickRate();

    @Override
    public void tickBeingEquipped(@NotNull CustomItem item, @NotNull Player player, @NotNull CustomEquipmentSlot slot) {
        if (Bukkit.getCurrentTick() % getTickRate() != 0) return;
        PassiveAbilityComponent<?> passive = item.getComponents().getOrDefault(ComponentTypes.PASSIVE_ABILITY_ITEM);
        if (passive instanceof EquippableActivatablePassiveComponent equippable){
            equippable.activate(item, player, new Object());
        }
    }
}
