package me.udnek.rpgu.equipment.slot;


import me.udnek.itemscoreu.customequipmentslot.slot.*;
import me.udnek.itemscoreu.customequipmentslot.universal.BaseUniversalSlot;
import me.udnek.itemscoreu.customregistry.CustomRegistries;
import me.udnek.rpgu.RpgU;
import me.udnek.rpgu.lore.TranslationKeys;

import java.util.Set;

public class EquipmentSlots {
    public static final SingleSlot FIRST_ARTIFACT = (SingleSlot) register(new ConstructableSingleSlot("first_artifact", null, null, new BaseUniversalSlot(9), TranslationKeys.whenEquippedAsFirstArtifact));
    public static final SingleSlot SECOND_ARTIFACT = (SingleSlot) register(new ConstructableSingleSlot("second_artifact", null,null, new BaseUniversalSlot(10), TranslationKeys.whenEquippedAsSecondArtifact));
    public static final SingleSlot THIRD_ARTIFACT = (SingleSlot) register(new ConstructableSingleSlot("third_artifact", null,null, new BaseUniversalSlot(11), TranslationKeys.whenEquippedAsThirdArtifact));
    public static final GroupSlot ARTIFACTS;


    static {
        ARTIFACTS = (GroupSlot) register(new ConstructableGroupSlot("artifacts", Set.of(
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
