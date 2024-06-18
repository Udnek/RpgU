package me.udnek.rpgu.item.abstraction;

import me.udnek.itemscoreu.customattribute.equipmentslot.CustomEquipmentSlot;
import me.udnek.itemscoreu.customattribute.equipmentslot.CustomEquipmentSlots;
import me.udnek.rpgu.attribute.equipmentslot.EquipmentSlots;
import me.udnek.rpgu.equipment.PlayerEquipmentDatabase;
import org.bukkit.entity.Player;

public interface OffHandItem extends EquippableItem{

    @Override
    default boolean isAppropriateSlot(CustomEquipmentSlot slot){
        return slot == CustomEquipmentSlots.OFF_HAND;
    }

    @Override
    default boolean isEquippedInAppropriateSlot(Player player){
        return PlayerEquipmentDatabase.get(player).getOffHand() == this;
    }

}
