package me.udnek.rpgu.item.abstraction;

import me.udnek.itemscoreu.customattribute.CustomAttributesContainer;
import me.udnek.itemscoreu.customattribute.DefaultCustomAttributeHolder;
import me.udnek.itemscoreu.customattribute.equipmentslot.CustomEquipmentSlot;
import me.udnek.rpgu.attribute.DefaultVanillaAttributeHolder;
import me.udnek.rpgu.attribute.VanillaAttributeContainer;
import me.udnek.rpgu.equipment.Equippable;
import org.bukkit.entity.Player;

public interface EquippableItem extends RpgUCustomItem, Equippable, DefaultCustomAttributeHolder, DefaultVanillaAttributeHolder {
    // TODO: 6/13/2024 ADD INIT DEFAULT DefaultCustomAttributeHolder
    boolean isEquippedInAppropriateSlot(Player player);
    boolean isAppropriateSlot(CustomEquipmentSlot slot);
    @Override
    default CustomAttributesContainer getDefaultCustomAttributes() {return null;}
    @Override
    default VanillaAttributeContainer getDefaultVanillaAttributes() {return null;}
}
