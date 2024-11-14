package me.udnek.rpgu.item.equipment.shaman;

import me.udnek.itemscoreu.customequipmentslot.CustomEquipmentSlot;
import me.udnek.itemscoreu.util.LoreBuilder;
import me.udnek.rpgu.util.AttributeManaging;
import org.bukkit.Material;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ShamanHelmet extends ShamanArmor {
    @Override
    public @NotNull String getRawId() {return "shaman_helmet";}

    @Override
    public @NotNull Material getMaterial() {return Material.DIAMOND_HELMET;}

    @Override
    public @Nullable LoreBuilder getLoreBuilder() {return getLoreBuilder(CustomEquipmentSlot.HEAD);}

    @Override
    public void initializeAttributes(@NotNull ItemMeta itemMeta) {
        super.initializeAttributes(itemMeta);
        AttributeManaging.applyDefaultArmorAttribute(itemMeta, Material.LEATHER_HELMET);
    }
}