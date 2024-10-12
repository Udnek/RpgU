package me.udnek.rpgu.item;

import me.udnek.itemscoreu.customblock.CustomBlock;
import me.udnek.itemscoreu.customitem.ConstructableCustomItem;
import me.udnek.itemscoreu.customitem.CustomBlockItem;
import me.udnek.rpgu.block.Blocks;
import org.bukkit.Material;

public class TestBlockItem extends ConstructableCustomItem implements RpgUCustomItem, CustomBlockItem {
    @Override
    public String getRawId() {
        return "test_block_item";
    }
    @Override
    public Material getMaterial() {return Material.SPAWNER;}
    @Override
    public CustomBlock getBlock() {
        return Blocks.TEST;
    }
}
