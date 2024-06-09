package me.udnek.rpgu.attribute.equipmentslot;

import me.udnek.itemscoreu.customattribute.equipmentslot.CustomEquipmentSlot;
import org.bukkit.entity.Entity;

public class ArtifactSlot extends CustomEquipmentSlot {
    @Override
    public boolean isAppropriateSlot(Entity entity, int slot) {
        switch (slot){
            case 9, 10, 11 -> {return true;}
            default -> {return false;}
        }
    }

    @Override
    public int[] getAllSlots(Entity entity) {
        return new int[]{9, 10, 11};
    }
}
