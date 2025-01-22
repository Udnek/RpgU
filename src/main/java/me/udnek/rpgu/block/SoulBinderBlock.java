package me.udnek.rpgu.block;

import io.papermc.paper.datacomponent.DataComponentTypes;
import me.udnek.itemscoreu.customentitylike.block.constructabletype.DisplayBasedConstructableBlockType;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.ShulkerBox;
import org.bukkit.block.TileState;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class SoulBinderBlock extends DisplayBasedConstructableBlockType {

    @Override
    public @NotNull ItemStack getVisualItem() {
        return new ItemStack(Material.STRIPPED_PALE_OAK_LOG);
    }

    @Override
    public @NotNull String getRawId() {
        return "soul_binder";
    }

    @Override
    public @NotNull Material getMaterial() {
        return Material.SPAWNER;
    }


    @Override
    public void place(@NotNull Location location) {
        super.place(location);
/*        ShulkerBox state = (ShulkerBox) location.getBlock().getState();
        ItemStack lockItem = new ItemStack(Material.BARRIER);
        lockItem.setData(DataComponentTypes.ITEM_NAME, Component.text("LOCK"));
        state.setLockItem(lockItem);
        state.update(true, false);*/
    }

    @Override
    public void onDestroy(@NotNull Block block) {
        super.onDestroy(block);
    }

    @Override
    public void load(@NotNull TileState tileState) {

    }

    @Override
    public void unload(@NotNull TileState tileState) {

    }
}
