package me.udnek.rpgu.equipment.slot;


import me.udnek.coreu.custom.equipmentslot.slot.ConstructableGroupSlot;
import me.udnek.coreu.custom.equipmentslot.slot.ConstructableSingleSlot;
import me.udnek.coreu.custom.equipmentslot.slot.CustomEquipmentSlot;
import me.udnek.coreu.custom.equipmentslot.universal.BaseUniversalSlot;
import me.udnek.coreu.custom.registry.CustomRegistries;
import me.udnek.rpgu.RpgU;

import java.util.Set;

public class EquipmentSlots {
    public static final CustomEquipmentSlot.Single FIRST_ARTIFACT = (CustomEquipmentSlot.Single)
            register(new ConstructableSingleSlot("first_artifact", null, null, new BaseUniversalSlot(9), "item.modifiers.first_artifact"));
    public static final CustomEquipmentSlot.Single SECOND_ARTIFACT = (CustomEquipmentSlot.Single)
            register(new ConstructableSingleSlot("second_artifact", null,null, new BaseUniversalSlot(10), "item.modifiers.second_artifact"));
    public static final CustomEquipmentSlot.Single THIRD_ARTIFACT = (CustomEquipmentSlot.Single)
            register(new ConstructableSingleSlot("third_artifact", null,null, new BaseUniversalSlot(11), "item.modifiers.third_artifact"));
    public static final CustomEquipmentSlot.Group ARTIFACTS;


    static {
        ARTIFACTS = (CustomEquipmentSlot.Group) register(new ConstructableGroupSlot("artifacts", Set.of(
                FIRST_ARTIFACT,
                SECOND_ARTIFACT,
                THIRD_ARTIFACT),
                null,
                null,
                "item.modifiers.artifacts")
        );
    }

    private static CustomEquipmentSlot register(CustomEquipmentSlot slot){
        return CustomRegistries.EQUIPMENT_SLOT.register(RpgU.getInstance(), slot);
    }
}
