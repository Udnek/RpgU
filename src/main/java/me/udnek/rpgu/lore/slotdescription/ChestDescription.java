package me.udnek.rpgu.lore.slotdescription;

import me.udnek.rpgu.lore.TranslationKeys;
import me.udnek.rpgu.lore.slotdescription.abstracts.EquipmentDescription;
import org.bukkit.inventory.EquipmentSlot;

public class ChestDescription extends EquipmentDescription {
    @Override
    public String getHeaderKey() {
        return TranslationKeys.whenOnBody;
    }

    @Override
    public EquipmentSlot getSlot() {
        return EquipmentSlot.CHEST;
    }
}
