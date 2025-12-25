package me.udnek.rpgu.block;

import me.udnek.coreu.custom.entitylike.block.CustomBlockType;
import me.udnek.coreu.custom.registry.CustomRegistries;
import me.udnek.rpgu.RpgU;
import org.jetbrains.annotations.NotNull;

public class Blocks {

    public static final CustomBlockType SOUL_BINDER = register(new SoulBinderBlock());


    private static @NotNull <T extends CustomBlockType> T register(@NotNull T type){
        return CustomRegistries.BLOCK_TYPE.register(RpgU.getInstance(), type);
    }

}
