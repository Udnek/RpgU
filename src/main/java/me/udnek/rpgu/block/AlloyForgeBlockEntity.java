package me.udnek.rpgu.block;

import io.papermc.paper.datacomponent.DataComponentTypes;
import me.udnek.coreu.custom.entitylike.block.CustomBlockEntityType;
import me.udnek.rpgu.RpgU;
import me.udnek.rpgu.mechanic.machine.AbstractMachineBlockEntity;
import me.udnek.rpgu.mechanic.machine.alloying.AlloyForgeInventory;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class AlloyForgeBlockEntity extends AbstractMachineBlockEntity {

    public AlloyForgeBlockEntity() {
        machine = new AlloyForgeInventory(this);
    }

    public void setLit(boolean lit) {
        ItemDisplay display = Objects.requireNonNull(Blocks.ALLOY_FORGE.getDisplay(getReal().getBlock()));
        ItemStack newDisplay = display.getItemStack();
        if (lit) {
            newDisplay.setData(DataComponentTypes.ITEM_MODEL, new NamespacedKey(RpgU.getInstance(), "alloy_forge_lit"));
        } else {
            newDisplay.setData(DataComponentTypes.ITEM_MODEL, new NamespacedKey(RpgU.getInstance(), "alloy_forge"));
        }
        display.setItemStack(newDisplay);
    }

    @Override
    public @NotNull CustomBlockEntityType getType() {
        return Blocks.ALLOY_FORGE;
    }
}
