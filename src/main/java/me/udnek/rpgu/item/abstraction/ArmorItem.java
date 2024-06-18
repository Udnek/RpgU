package me.udnek.rpgu.item.abstraction;

import me.udnek.itemscoreu.customattribute.equipmentslot.CustomEquipmentSlot;
import me.udnek.itemscoreu.customattribute.equipmentslot.CustomEquipmentSlots;
import me.udnek.rpgu.equipment.PlayerEquipmentDatabase;
import org.bukkit.entity.Player;

public interface ArmorItem extends EquippableItem{

    @Override
    default boolean isAppropriateSlot(CustomEquipmentSlot slot){
        return
                CustomEquipmentSlots.HEAD == slot ||
                CustomEquipmentSlots.CHEST == slot ||
                CustomEquipmentSlots.LEGS == slot ||
                CustomEquipmentSlots.FEET == slot;
    }

    @Override
    default boolean isEquippedInAppropriateSlot(Player player) {
        return PlayerEquipmentDatabase.get(player).isEquippedAsArmor(this);
    }
}
