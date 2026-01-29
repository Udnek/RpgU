package me.udnek.rpgu.block;

import me.udnek.coreu.custom.entitylike.block.CustomBlockEntityType;
import me.udnek.rpgu.mechanic.machine.AbstractMachineBlockEntity;
import me.udnek.rpgu.mechanic.machine.alloying.AlloyForgeInventory;
import org.jetbrains.annotations.NotNull;

public class AlloyForgeBlockEntity extends AbstractMachineBlockEntity {

    public AlloyForgeBlockEntity() {
        machine = new AlloyForgeInventory(this);
    }

    public void setLit(boolean lit) {
        // todo
    }

    @Override
    public @NotNull CustomBlockEntityType getType() {
        return Blocks.ALLOY_FORGE;
    }
}
