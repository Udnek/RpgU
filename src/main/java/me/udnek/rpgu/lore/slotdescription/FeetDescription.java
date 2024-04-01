package me.udnek.rpgu.lore.slotdescription;

import me.udnek.rpgu.lore.TranslationKeys;
import me.udnek.rpgu.lore.slotdescription.abstracts.EquipmentDescription;
import org.bukkit.inventory.EquipmentSlot;

public class FeetDescription extends EquipmentDescription {
    @Override
    public String getHeaderKey() {
        return TranslationKeys.whenOnFeet;
    }

    @Override
    public EquipmentSlot getSlot() {
        return EquipmentSlot.FEET;
    }
}
