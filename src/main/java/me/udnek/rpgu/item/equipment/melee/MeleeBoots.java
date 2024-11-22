package me.udnek.rpgu.item.equipment.melee;

import me.udnek.rpgu.util.AttributeManaging;
import org.bukkit.Material;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

public class MeleeBoots extends MeleeArmor {
    @Override
    public @NotNull Material getMaterial() {return Material.DIAMOND_BOOTS;}

    @Override
    public @NotNull String getRawId() {return "melee_boots";}

    @Override
    public void initializeAttributes(@NotNull ItemMeta itemMeta) {
        super.initializeAttributes(itemMeta);
        AttributeManaging.applyDefaultArmorAttribute(itemMeta, Material.IRON_BOOTS, true, false);
    }

}
