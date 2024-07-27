package me.udnek.rpgu.block;

import me.udnek.itemscoreu.customblock.CustomBlock;
import me.udnek.itemscoreu.customblock.CustomBlockRegistry;
import me.udnek.rpgu.RpgU;

public class Blocks {

    public static final CustomBlock TEST = register(new TestBlock());


    private static CustomBlock register(CustomBlock customBlock){
        return CustomBlockRegistry.getInstance().register(RpgU.getInstance(), customBlock);
    }
}
