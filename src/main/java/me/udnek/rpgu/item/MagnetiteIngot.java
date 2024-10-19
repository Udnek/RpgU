package me.udnek.rpgu.item;

import me.udnek.itemscoreu.customitem.ConstructableCustomItem;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class MagnetiteIngot extends ConstructableCustomItem implements RpgUCustomItem{
    @Override
    public @Nullable Integer getCustomModelData() {return 3110;}
    @Override
    public @NotNull String getRawId() {return "magnetite_ingot";}
    @Override
    public @NotNull Material getMaterial() {return Material.GUNPOWDER;}

    @Override
    protected void generateRecipes(@NotNull Consumer<@NotNull Recipe> consumer) {
        RecipeChoice.ExactChoice rawMagnetite = new RecipeChoice.ExactChoice(Items.RAW_MAGNETITE.getItem());

        FurnaceRecipe recipe = new FurnaceRecipe(
                getRecipeNamespace(0),
                getItem(),
                rawMagnetite,
                 0.7f,
                 200
        );

        consumer.accept(recipe);
    }
}
