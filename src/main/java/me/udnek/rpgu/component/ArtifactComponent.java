package me.udnek.rpgu.component;

import me.udnek.itemscoreu.customequipmentslot.CustomEquipmentSlot;
import me.udnek.rpgu.equipment.slot.EquipmentSlots;
import org.jetbrains.annotations.NotNull;

public interface ArtifactComponent extends EquippableItemComponent {
    @Override
    default boolean isAppropriateSlot(@NotNull CustomEquipmentSlot slot) {
        return EquipmentSlots.ARTIFACTS.test(slot);
    }
}
