package me.udnek.rpgu.attribute.equipmentslot;

import me.udnek.itemscoreu.customattribute.equipmentslot.CustomEquipmentSlot;
import me.udnek.rpgu.lore.TranslationKeys;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;

public class OriginSlot extends CustomEquipmentSlot {
    @Override
    public boolean isAppropriateSlot(Entity entity, int i) {return false;}
    @Override
    public int[] getAllSlots(Entity entity) {return new int[0];}

    @Override
    public @NotNull String translationKey() {
        return TranslationKeys.whenEquippedAsOrigin;
    }
}
