package me.udnek.rpgu.equipment.slot;

import me.udnek.itemscoreu.customequipmentslot.AbstractCustomEquipmentSlot;
import me.udnek.itemscoreu.customequipmentslot.CustomEquipmentSlot;
import me.udnek.itemscoreu.customequipmentslot.SingleSlot;
import me.udnek.rpgu.lore.TranslationKeys;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.EquipmentSlotGroup;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class OriginSlot extends AbstractCustomEquipmentSlot implements SingleSlot {
    public OriginSlot() {
        super("origin");
    }
    @Override
    public boolean isAppropriateSlot(Entity entity, int slot) {
        return false;
    }
    @Override
    public @Nullable EquipmentSlotGroup getVanillaAlternative() {
        return null;
    }
    @Override
    public @NotNull String translationKey() {
        return TranslationKeys.whenEquippedAsOrigin;
    }

    @Override
    public @Nullable Integer getSlot(@NotNull Entity entity) {
        return null;
    }

    @Override
    public void getAllSlots(@NotNull Entity entity, @NotNull Consumer<@NotNull Integer> consumer) {}

    @Override
    public boolean test(@NotNull CustomEquipmentSlot customEquipmentSlot) {
        return customEquipmentSlot == this;
    }
}
