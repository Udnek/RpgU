package me.udnek.rpgu.attribute.equipmentslot;

import me.udnek.itemscoreu.customattribute.equipmentslot.CustomEquipmentSlot;
import me.udnek.itemscoreu.customattribute.equipmentslot.CustomEquipmentSlots;

public class EquipmentSlots {

    public static final CustomEquipmentSlot ARTIFACT = CustomEquipmentSlots.register(new ArtifactSlot());
    public static final CustomEquipmentSlot ORIGIN = CustomEquipmentSlots.register(new ArtifactSlot());
}
