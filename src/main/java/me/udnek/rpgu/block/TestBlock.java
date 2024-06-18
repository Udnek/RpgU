package me.udnek.rpgu.block;

import me.udnek.itemscoreu.customblock.CustomBlock;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class TestBlock extends CustomBlock {
    @Override
    public String getRawId() {
        return "test_block";
    }

    @Override
    public ItemStack getVisualItem() {
        return new ItemStack(Material.SAND);
    }
}
