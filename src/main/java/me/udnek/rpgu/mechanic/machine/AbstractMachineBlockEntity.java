package me.udnek.rpgu.mechanic.machine;

import me.udnek.coreu.custom.entitylike.block.ConstructableCustomBlockEntity;
import org.bukkit.block.ShulkerBox;
import org.bukkit.block.TileState;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractMachineBlockEntity extends ConstructableCustomBlockEntity<ShulkerBox> {

    public @NotNull AbstractMachine machine;



    @Override
    public void delayedTick() {
        machine.tick(getTickDelay());
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
}
