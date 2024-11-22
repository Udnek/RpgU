package me.udnek.rpgu.item.equipment.melee;

import me.udnek.rpgu.util.AttributeManaging;
import org.bukkit.Material;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

public class MeleeLeggings extends MeleeArmor {
    @Override
    public @NotNull String getRawId() {return "melee_leggings";}

    @Override
    public @NotNull Material getMaterial() {return Material.DIAMOND_LEGGINGS;}

    @Override
    public void initializeAttributes(@NotNull ItemMeta itemMeta) {
        super.initializeAttributes(itemMeta);
        AttributeManaging.applyDefaultArmorAttribute(itemMeta, Material.IRON_LEGGINGS, true, false);
    }
}