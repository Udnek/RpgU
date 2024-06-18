package me.udnek.rpgu.item;

import me.udnek.itemscoreu.customblock.CustomBlock;
import me.udnek.itemscoreu.customitem.CustomBlockItem;
import me.udnek.rpgu.block.Blocks;
import me.udnek.rpgu.item.abstraction.RpgUCustomItem;

public class TestBlockItem extends CustomBlockItem implements RpgUCustomItem {

    @Override
    public String getRawId() {
        return "test_block_item";
    }
    @Override
    public CustomBlock getBlock() {
        return Blocks.testBlock;
    }
}
