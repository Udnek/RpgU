package me.udnek.rpgu.lore.ability;

import me.udnek.itemscoreu.customequipmentslot.slot.CustomEquipmentSlot;
import org.jetbrains.annotations.NotNull;

public interface PassiveAbilityLorePart extends AbilityLorePart{

    void setEquipmentSlot(@NotNull CustomEquipmentSlot slot);
}
