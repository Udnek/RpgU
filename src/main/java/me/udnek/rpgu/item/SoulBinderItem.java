package me.udnek.rpgu.item;

import me.udnek.itemscoreu.customcomponent.instance.BlockPlacingItem;
import me.udnek.itemscoreu.customitem.ConstructableCustomItem;
import me.udnek.rpgu.block.BlockTypes;
import org.bukkit.Material;
import org.bukkit.inventory.ItemRarity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SoulBinderItem extends ConstructableCustomItem {

    @Override
    public @NotNull Material getMaterial() {
        return Material.BARRIER;
    }

    @Override
    public @NotNull String getRawId() {
        return "soul_binder";
    }

    @Override
    public @Nullable DataSupplier<ItemRarity> getRarity() {
        return DataSupplier.of(ItemRarity.COMMON);
    }

    @Override
    public void initializeComponents() {
        super.initializeComponents();
        getComponents().set(new BlockPlacingItem(BlockTypes.SOUL_BINDER));
    }
}
