package me.udnek.rpgu.block;

import io.papermc.paper.datacomponent.DataComponentTypes;
import me.udnek.coreu.custom.entitylike.block.CustomBlockEntityType;
import me.udnek.rpgu.RpgU;
import me.udnek.rpgu.mechanic.machine.AbstractMachineBlockEntity;
import me.udnek.rpgu.mechanic.machine.crusher.CrusherInventory;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.inventory.ItemStack;
import org.jspecify.annotations.NonNull;

import java.util.Objects;

public class CrusherBlockEntity extends AbstractMachineBlockEntity {

    private boolean lit;
    private boolean working;

    public CrusherBlockEntity() {
        machine = new CrusherInventory(this);
    }

    public void setLit(boolean lit) {
        changeState(working, lit);
    }

    public void setWorking(boolean working) {
        changeState(working, lit);
    }

    public void changeState(boolean working, boolean lit){
        if (this.lit == lit && this.working == working) return;
        this.working = working;
        this.lit = lit;
        ItemDisplay display = Objects.requireNonNull(Blocks.CRUSHER.getDisplay(getReal().getBlock()));
        ItemStack newDisplay = display.getItemStack();
        if (working) {
            if (lit) {
                newDisplay.setData(DataComponentTypes.ITEM_MODEL, new NamespacedKey(RpgU.getInstance(), "crusher_working"));
            } else {
                throw new RuntimeException("off lit when start working");
            }
        }else {
            if (lit) {
                newDisplay.setData(DataComponentTypes.ITEM_MODEL, new NamespacedKey(RpgU.getInstance(), "crusher_lit"));
            } else {
                newDisplay.setData(DataComponentTypes.ITEM_MODEL, new NamespacedKey(RpgU.getInstance(), "crusher"));
            }
        }
        display.setItemStack(newDisplay);
    }

    @Override
    public @NonNull CustomBlockEntityType getType() {
        return Blocks.CRUSHER;
    }
}
