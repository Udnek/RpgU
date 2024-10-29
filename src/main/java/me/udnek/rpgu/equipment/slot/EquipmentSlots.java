package me.udnek.rpgu.equipment.slot;

import me.udnek.itemscoreu.customequipmentslot.CustomEquipmentSlot;
import me.udnek.itemscoreu.customequipmentslot.GroupSlot;
import me.udnek.itemscoreu.customequipmentslot.SingleSlot;
import me.udnek.itemscoreu.customequipmentslot.instance.ConstructableGroupSlot;
import me.udnek.itemscoreu.customequipmentslot.instance.ConstructableSingleSlot;
import me.udnek.itemscoreu.customregistry.CustomRegistries;
import me.udnek.itemscoreu.util.LogUtils;
import me.udnek.rpgu.RpgU;
import me.udnek.rpgu.lore.TranslationKeys;

import java.util.Set;

public class EquipmentSlots {
    public static final SingleSlot FIRST_ARTIFACT = (SingleSlot) register(new ConstructableSingleSlot("first_artifact", 9,null, null, TranslationKeys.whenEquippedAsFirstArtifact));
    public static final SingleSlot SECOND_ARTIFACT = (SingleSlot) register(new ConstructableSingleSlot("second_artifact", 10,null, null, TranslationKeys.whenEquippedAsSecondArtifact));
    public static final SingleSlot THIRD_ARTIFACT = (SingleSlot) register(new ConstructableSingleSlot("third_artifact", 11,null, null, TranslationKeys.whenEquippedAsThirdArtifact));
    public static final GroupSlot ARTIFACTS;
    public static final SingleSlot ORIGIN = (SingleSlot) register(new OriginSlot());


    static {
        LogUtils.pluginLog(FIRST_ARTIFACT);
        LogUtils.pluginLog(SECOND_ARTIFACT);
        LogUtils.pluginLog(THIRD_ARTIFACT);
        ARTIFACTS = (GroupSlot) register(new ConstructableGroupSlot("hand", Set.of(
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
