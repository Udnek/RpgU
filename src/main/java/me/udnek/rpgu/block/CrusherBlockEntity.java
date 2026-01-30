package me.udnek.rpgu.block;

import io.papermc.paper.datacomponent.DataComponentTypes;
import me.udnek.coreu.custom.entitylike.block.CustomBlockEntityType;
import me.udnek.rpgu.RpgU;
import me.udnek.rpgu.mechanic.machine.AbstractMachineBlockEntity;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.inventory.ItemStack;
import org.jspecify.annotations.NonNull;

import java.util.Objects;

public class CrusherBlockEntity extends AbstractMachineBlockEntity {

    public CrusherBlockEntity() {
        machine = null;
    }

    public void setState(boolean isLit, boolean isWorking) {
        ItemDisplay display = Objects.requireNonNull(Blocks.CRUSHER.getDisplay(getReal().getBlock()));
        ItemStack newDisplay = display.getItemStack();
        if (isLit) {
            if (isWorking) {
                newDisplay.setData(DataComponentTypes.ITEM_MODEL, new NamespacedKey(RpgU.getInstance(), "crusher_working"));
            }
            else {
                newDisplay.setData(DataComponentTypes.ITEM_MODEL, new NamespacedKey(RpgU.getInstance(), "crusher_lit"));
            }
        } else {
            newDisplay.setData(DataComponentTypes.ITEM_MODEL, new NamespacedKey(RpgU.getInstance(), "crusher"));
        }
        display.setItemStack(newDisplay);
    }

    @Override
    public @NonNull CustomBlockEntityType getType() {
        return Blocks.CRUSHER;
    }
}
