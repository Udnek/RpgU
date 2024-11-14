package me.udnek.rpgu.item.equipment.shaman;

import me.udnek.itemscoreu.customequipmentslot.CustomEquipmentSlot;
import me.udnek.itemscoreu.util.LoreBuilder;
import me.udnek.rpgu.util.AttributeManaging;
import org.bukkit.Material;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ShamanBoots extends ShamanArmor {
    @Override
    public @NotNull Material getMaterial() {return Material.DIAMOND_BOOTS;}

    @Override
    public @NotNull String getRawId() {return "shaman_boots";}

    @Override
    public @Nullable LoreBuilder getLoreBuilder() {return getLoreBuilder(CustomEquipmentSlot.FEET);}

    @Override
    public void initializeAttributes(@NotNull ItemMeta itemMeta) {
        super.initializeAttributes(itemMeta);
        AttributeManaging.applyDefaultArmorAttribute(itemMeta, Material.LEATHER_BOOTS);
    }

}
