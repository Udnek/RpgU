package me.udnek.rpgu.item.block;

import me.udnek.coreu.custom.component.instance.BlockPlacingItem;
import me.udnek.coreu.custom.component.instance.TranslatableThing;
import me.udnek.coreu.custom.item.ConstructableCustomItem;
import me.udnek.rpgu.block.Blocks;
import org.bukkit.Material;
import org.bukkit.inventory.ItemRarity;
import org.bukkit.inventory.Recipe;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class AlloyForgeItem extends ConstructableCustomItem {

    @Override
    public @NotNull Material getMaterial() {
        return Material.BARRIER;
    }


    @Override
    public @NotNull String getRawId() {
        return "alloy_forge";
    }

    @Override
    public @Nullable TranslatableThing getTranslations() {
        return TranslatableThing.ofEngAndRu("", "");//TODO
    }

    @Override
    public @Nullable DataSupplier<ItemRarity> getRarity() {
        return DataSupplier.of(ItemRarity.COMMON);
    }

    @Override
    public void initializeComponents() {
        super.initializeComponents();
        getComponents().set(new BlockPlacingItem(Blocks.ALLOY_FORGE));
    }

    @Override
    protected void generateRecipes(@NotNull Consumer<Recipe> consumer) {
        //TODO
    }
}
