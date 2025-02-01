package me.udnek.rpgu.component.instance;

import me.udnek.itemscoreu.customequipmentslot.CustomEquipmentSlot;
import me.udnek.itemscoreu.customitem.CustomItem;
import me.udnek.rpgu.component.EquippableItemComponent;
import me.udnek.rpgu.mechanic.damaging.DamageInstance;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CloseActivator implements EquippableItemComponent {
    CustomEquipmentSlot equipmentSlot;
    int cooldown = 2 * 20;
    public CloseActivator(CustomEquipmentSlot equipmentSlot, int cooldown){
        this.equipmentSlot = equipmentSlot;
        this.cooldown = cooldown;
    }

    @Override
    public boolean isAppropriateSlot(@NotNull CustomEquipmentSlot slot) {
        return equipmentSlot.test(slot);
    }

    @Override
    public void onPlayerReceivesDamageWhenEquipped(@NotNull CustomItem item, @NotNull Player player, @NotNull CustomEquipmentSlot slot, @NotNull DamageInstance damageInstance) {
        item.setCooldown(player, cooldown);
    }
}
