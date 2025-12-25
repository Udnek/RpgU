package me.udnek.rpgu.item.equipment.ferrudam.tool;

import me.udnek.coreu.custom.recipe.choice.CustomCompatibleRecipeChoice;
import me.udnek.coreu.custom.recipe.choice.CustomSingleRecipeChoice;
import me.udnek.rpgu.item.Items;
import me.udnek.rpgu.mechanic.alloying.AlloyingRecipe;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

public class FerrudamPickaxe extends FerrudamTool {
    @Override
    public @NotNull String getRawId() {return "ferrudam_pickaxe";}
    @Override
    public @NotNull Material getMaterial() {return Material.DIAMOND_PICKAXE;}
    @Override
    protected void generateRecipes(@NotNull Consumer<@NotNull Recipe> consumer) {
        ShapedRecipe recipe = new ShapedRecipe(getNewRecipeKey(), getItem());
        recipe.shape(
                "FFF",
                " S ",
                " S ");

        RecipeChoice.ExactChoice ferrudam = new RecipeChoice.ExactChoice(Items.FERRUDAM_INGOT.getItem());
        RecipeChoice.MaterialChoice stick = new RecipeChoice.MaterialChoice(Material.STICK);
        recipe.setIngredient('F', ferrudam);
        recipe.setIngredient('S', stick);

        consumer.accept(recipe);

        var ingot = new CustomSingleRecipeChoice(Items.FERRUDAM_INGOT);

        AlloyingRecipe recipeAlloy = new AlloyingRecipe(
                getNewRecipeKey(),
                List.of(ingot, ingot),
                new CustomCompatibleRecipeChoice(Set.of(), Tag.ITEMS_COALS.getValues()),
                new CustomSingleRecipeChoice(Material.IRON_PICKAXE),
                getItem()
        );

        consumer.accept(recipeAlloy);
    }
}
