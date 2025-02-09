package me.udnek.rpgu.item.equipment.flint;

import me.udnek.rpgu.item.Items;
import org.bukkit.Material;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class FlintPickaxe extends FlintTool {
    @Override
    public @NotNull String getRawId() {return "flint_pickaxe";}
    @Override
    public @NotNull Material getMaterial() {return Material.STONE_PICKAXE;}

    @Override
    protected void generateRecipes(@NotNull Consumer<@NotNull Recipe> consumer) {
        ShapedRecipe recipe = new ShapedRecipe(getNewRecipeKey(), getItem());
        recipe.shape(
                "FCF",
                "AS ",
                " S ");

        recipe.setIngredient('S', new RecipeChoice.MaterialChoice(Material.STICK));
        recipe.setIngredient('A', new RecipeChoice.ExactChoice(Items.FABRIC.getItem()));
        recipe.setIngredient('F', new RecipeChoice.MaterialChoice(Material.FLINT));
        recipe.setIngredient('C', new RecipeChoice.MaterialChoice(Material.COBBLESTONE));

        consumer.accept(recipe);
    }
}
