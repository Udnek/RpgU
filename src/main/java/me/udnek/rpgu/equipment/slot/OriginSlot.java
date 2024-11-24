package me.udnek.rpgu.equipment.slot;

import me.udnek.itemscoreu.customequipmentslot.AbstractCustomEquipmentSlot;
import me.udnek.itemscoreu.customequipmentslot.CustomEquipmentSlot;
import me.udnek.itemscoreu.customequipmentslot.SingleSlot;
import me.udnek.rpgu.lore.TranslationKeys;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.EquipmentSlotGroup;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class OriginSlot extends AbstractCustomEquipmentSlot implements SingleSlot {
    public OriginSlot() {
        super("origin");
    }
    @Override
    public boolean isAppropriateSlot(@NotNull LivingEntity entity, int slot) {
        return false;
    }
    @Override
    public @Nullable EquipmentSlotGroup getVanillaGroup() {
        return null;
    }
    @Override
    public @Nullable EquipmentSlot getVanillaSlot() {return null;}
    @Override
    public @NotNull String translationKey() {
        return TranslationKeys.whenEquippedAsOrigin;
    }
    @Override
    public @Nullable Integer getSlot(@NotNull LivingEntity entity) {return null;}
}
