package me.udnek.rpgu.item.ingredients;

import me.udnek.itemscoreu.customitem.ConstructableCustomItem;
import org.bukkit.Material;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapelessRecipe;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class EsotericSalve extends ConstructableCustomItem {
    @Override
    public @NotNull String getRawId() {return "esoteric_salve";}

    @Override
    public @NotNull Material getMaterial() {return Material.GUNPOWDER;}

    @Override
    protected void generateRecipes(@NotNull Consumer<@NotNull Recipe> consumer) {
        ShapelessRecipe recipe = new ShapelessRecipe(getNewRecipeKey(), getItem());

        recipe.addIngredient(new RecipeChoice.MaterialChoice(Material.GLOW_INK_SAC));
        recipe.addIngredient(new RecipeChoice.MaterialChoice(Material.SWEET_BERRIES));

        consumer.accept(recipe);
    }
}
