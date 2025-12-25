package me.udnek.rpgu.equipment.slot;


import me.udnek.coreu.custom.equipmentslot.slot.*;
import me.udnek.coreu.custom.equipmentslot.universal.BaseUniversalSlot;
import me.udnek.coreu.custom.registry.CustomRegistries;
import me.udnek.rpgu.RpgU;
import me.udnek.rpgu.lore.TranslationKeys;

import java.util.Set;

public class EquipmentSlots {
    public static final CustomEquipmentSlot.Single FIRST_ARTIFACT = (CustomEquipmentSlot.Single)
            register(new ConstructableSingleSlot("first_artifact", null, null, new BaseUniversalSlot(9), TranslationKeys.whenEquippedAsFirstArtifact));
    public static final CustomEquipmentSlot.Single SECOND_ARTIFACT = (CustomEquipmentSlot.Single)
            register(new ConstructableSingleSlot("second_artifact", null,null, new BaseUniversalSlot(10), TranslationKeys.whenEquippedAsSecondArtifact));
    public static final CustomEquipmentSlot.Single THIRD_ARTIFACT = (CustomEquipmentSlot.Single)
            register(new ConstructableSingleSlot("third_artifact", null,null, new BaseUniversalSlot(11), TranslationKeys.whenEquippedAsThirdArtifact));
    public static final CustomEquipmentSlot.Group ARTIFACTS;


    static {
        ARTIFACTS = (CustomEquipmentSlot.Group) register(new ConstructableGroupSlot("artifacts", Set.of(
                FIRST_ARTIFACT,
                SECOND_ARTIFACT,
                THIRD_ARTIFACT),
                null,
                null,
                TranslationKeys.whenEquippedAsArtifact)
        );
    }

    private static CustomEquipmentSlot register(CustomEquipmentSlot slot){
        return CustomRegistries.EQUIPMENT_SLOT.register(RpgU.getInstance(), slot);
    }
}
