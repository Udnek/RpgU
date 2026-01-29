package me.udnek.rpgu.item.block;

import me.udnek.coreu.custom.component.instance.BlockPlacingItem;
import me.udnek.coreu.custom.component.instance.TranslatableThing;
import me.udnek.coreu.custom.item.ConstructableCustomItem;
import me.udnek.rpgu.block.Blocks;
import me.udnek.rpgu.item.Items;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.inventory.ItemRarity;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class MaceratorItem extends ConstructableCustomItem {

    @Override
    public @NotNull Material getMaterial() {
        return Material.BARRIER;
    }


    @Override
    public @NotNull String getRawId() {
        return "macerator";
    }

    @Override
    public @Nullable TranslatableThing getTranslations() {
        return TranslatableThing.ofEngAndRu("Macerator", "Дробитель");
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
