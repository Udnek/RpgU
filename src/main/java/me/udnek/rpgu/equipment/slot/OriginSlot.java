/*package me.udnek.rpgu.equipment.slot;


import me.udnek.coreu.custom.equipmentslot.slot.AbstractCustomEquipmentSlot;
import me.udnek.coreu.custom.equipmentslot.slot.SingleSlot;
import me.udnek.coreu.custom.equipmentslot.universal.UniversalInventorySlot;
import me.udnek.rpgu.lore.TranslationKeys;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.EquipmentSlotGroup;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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

    @Override
    public @Nullable UniversalInventorySlot getUniversal() {
        return null;
    }
}*/
