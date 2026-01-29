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

public class CrusherItem extends ConstructableCustomItem {

    @Override
    public @NotNull Material getMaterial() {
        return Material.BARRIER;
    }


    @Override
    public @NotNull String getRawId() {
        return "crusher";
    }

    @Override
    public @Nullable TranslatableThing getTranslations() {
        return TranslatableThing.ofEngAndRu("Crusher", "Дробитель");
    }

    @Override
    public @Nullable DataSupplier<ItemRarity> getRarity() {
        return DataSupplier.of(ItemRarity.COMMON);
    }

    @Override
    public void initializeComponents() {
        super.initializeComponents();
        getComponents().set(new BlockPlacingItem(Blocks.CRUSHER));
    }

    @Override
    protected void generateRecipes(@NotNull Consumer<Recipe> consumer) {
        //TODO
    }
}
