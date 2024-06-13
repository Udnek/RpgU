package me.udnek.rpgu.item;

import me.udnek.itemscoreu.customblock.CustomBlock;
import me.udnek.itemscoreu.customitem.CustomBlockItem;
import me.udnek.rpgu.block.Blocks;

public class TestBlockItem extends CustomBlockItem {

    @Override
    public String getRawId() {
        return "test_block_item";
    }

    @Override
    public String getRawItemName() {
        return "Eshkere";
    }

    @Override
    public CustomBlock getBlock() {
        return Blocks.testBlock;
    }
}
