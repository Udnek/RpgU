package me.udnek.rpgu.block;

import me.udnek.coreu.custom.entitylike.block.ConstructableCustomBlockEntity;
import me.udnek.coreu.custom.entitylike.block.CustomBlockEntityType;
import org.bukkit.block.ShulkerBox;
import org.jspecify.annotations.NonNull;

public class MaceratorBlockEntity extends ConstructableCustomBlockEntity<ShulkerBox> {
    @Override
    public void delayedTick() {

    }

    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public @NonNull CustomBlockEntityType getType() {
        return Blocks.MACERATOR;
    }
}
