package me.udnek.rpgu.block;

import com.destroystokyo.paper.event.block.BlockDestroyEvent;
import me.udnek.itemscoreu.customblock.CustomBlock;
import me.udnek.itemscoreu.customitem.CustomItem;
import me.udnek.itemscoreu.utils.LogUtils;
import org.bukkit.Material;
import org.bukkit.block.Block;
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
