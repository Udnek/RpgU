package me.udnek.rpgu.item.abstraction;

import me.udnek.itemscoreu.customattribute.equipmentslot.CustomEquipmentSlot;
import me.udnek.itemscoreu.customattribute.equipmentslot.CustomEquipmentSlots;
import me.udnek.rpgu.equipment.PlayerEquipmentDatabase;
import org.bukkit.entity.Player;

public interface MainHandItem extends EquippableItem{

    @Override
    default boolean isAppropriateSlot(CustomEquipmentSlot slot){
        return slot == CustomEquipmentSlots.MAIN_HAND;
    }
    @Override
    default boolean isEquippedInAppropriateSlot(Player player){
        return PlayerEquipmentDatabase.get(player).getMainHand() == this;
    }
}
