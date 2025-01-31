package me.udnek.rpgu.block;

import me.udnek.itemscoreu.customentitylike.block.constructabletype.DisplayBasedConstructableBlockType;
import me.udnek.itemscoreu.util.Either;
import me.udnek.rpgu.item.Items;
import org.bukkit.Material;
import org.bukkit.block.TileState;
import org.bukkit.inventory.ItemStack;
import org.bukkit.loot.LootTable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SoulBinderBlock extends DisplayBasedConstructableBlockType{


    @Override
    public @NotNull ItemStack getFakeDisplay() {
        return Items.SOUL_BINDER.getItem();
    }

    @Override
    public @NotNull String getRawId() {
        return "soul_binder";
    }

    @Override
    public @Nullable Either<LootTable, List<ItemStack>> getLoot() {
        return new Either<>(null, List.of(Items.SOUL_BINDER.getItem()));
    }

    @Override
    public @NotNull Material getBreakSpeedBaseBlock() {
        return Material.IRON_BARS;
    }

    @Override
    public void load(@NotNull TileState tileState) {}

    @Override
    public void unload(@NotNull TileState tileState) {}
}
