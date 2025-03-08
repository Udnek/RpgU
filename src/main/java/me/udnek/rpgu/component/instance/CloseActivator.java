package me.udnek.rpgu.component.instance;

import me.udnek.itemscoreu.customequipmentslot.CustomEquipmentSlot;
import me.udnek.itemscoreu.customequipmentslot.SingleSlot;
import me.udnek.itemscoreu.customitem.CustomItem;
import me.udnek.rpgu.component.ConstructableEquippableItemComponent;
import me.udnek.rpgu.mechanic.damaging.DamageInstance;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CloseActivator extends ConstructableEquippableItemComponent {
    CustomEquipmentSlot equipmentSlot;
    int cooldown;
    public CloseActivator(@NotNull CustomEquipmentSlot equipmentSlot, int cooldown){
        this.equipmentSlot = equipmentSlot;
        this.cooldown = cooldown;
    }

    @Override
    public boolean isAppropriateSlot(@NotNull CustomEquipmentSlot slot) {
        return equipmentSlot.test(slot);
    }

    @Override
    public void onPlayerReceivesDamageWhenEquipped(@NotNull CustomItem item, @NotNull Player player, @NotNull SingleSlot slot, @NotNull DamageInstance damageInstance) {
        item.setCooldown(player, cooldown);
    }
}
