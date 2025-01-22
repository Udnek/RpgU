package me.udnek.rpgu.block;

import me.udnek.itemscoreu.customentitylike.block.CustomBlockType;
import me.udnek.itemscoreu.customregistry.CustomRegistries;
import me.udnek.rpgu.RpgU;
import org.bukkit.persistence.PersistentDataHolder;
import org.jetbrains.annotations.NotNull;

public class BlockTypes {

    public static final CustomBlockType SOUL_BINDER = register(new SoulBinderBlock());


    private static @NotNull <T extends CustomBlockType> T register(@NotNull T type){
        return CustomRegistries.BLOCK_TYPE.register(RpgU.getInstance(), type);
    }

}
