package me.udnek.rpgu.equipment.slot;

import me.udnek.itemscoreu.customattribute.equipmentslot.CustomEquipmentSlot;
import me.udnek.rpgu.lore.TranslationKeys;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;

public class ArtifactSlot extends CustomEquipmentSlot {
    @Override
    public boolean isAppropriateSlot(Entity entity, int slot) {
        switch (slot){
            case 9, 10, 11 -> {return true;}
            default -> {return false;}
        }
    }

    @Override
    public @NotNull String translationKey() {
        return TranslationKeys.whenEquippedAsArtifact;
    }

    @Override
    public int[] getAllSlots(Entity entity) {
        return new int[]{9, 10, 11};
    }
}
