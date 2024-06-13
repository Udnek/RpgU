package me.udnek.rpgu.block;

import me.udnek.itemscoreu.customblock.CustomBlock;
import me.udnek.itemscoreu.customblock.CustomBlockManager;
import me.udnek.rpgu.RpgU;

public class Blocks {

    public static final CustomBlock testBlock = register(new TestBlock());


    private static CustomBlock register(CustomBlock customBlock){
        return CustomBlockManager.register(RpgU.getInstance(), customBlock);
    }
}
