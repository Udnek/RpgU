package me.udnek.rpgu.block;

import me.udnek.coreu.custom.entitylike.block.ConstructableCustomBlockEntity;
import me.udnek.coreu.custom.entitylike.block.CustomBlockEntityType;
import me.udnek.rpgu.mechanic.alloying.AlloyForgeInventory;
import me.udnek.rpgu.mechanic.alloying.AlloyForgeMachine;
import org.bukkit.block.ShulkerBox;
import org.bukkit.block.TileState;
import org.jetbrains.annotations.NotNull;

public class AlloyForgeBlockEntity extends ConstructableCustomBlockEntity<ShulkerBox> {

    public @NotNull AlloyForgeMachine machine;

    public AlloyForgeBlockEntity() {
        machine = new AlloyForgeInventory(this);
    }

    @Override
    public void delayedTick() {
        machine.tick();
    }

    public void setLit(boolean lit) {
        // todo
    }

    @Override
    public void load(@NotNull TileState tileState) {
        super.load(tileState);
        machine.load();
    }

    @Override
    public void unload() {
        super.unload();
        machine.unload();
    }

    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public @NotNull CustomBlockEntityType getType() {
        return Blocks.ALLOY_FORGE;
    }
}
