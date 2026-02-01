package me.udnek.rpgu.item.equipment.ferrudam.tool;

import me.udnek.coreu.custom.component.instance.TranslatableThing;
import me.udnek.coreu.custom.recipe.choice.CustomCompatibleRecipeChoice;
import me.udnek.coreu.custom.recipe.choice.CustomSingleRecipeChoice;
import me.udnek.rpgu.item.Items;
import me.udnek.rpgu.mechanic.machine.alloying.AlloyingRecipe;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

public class FerrudamSword extends FerrudamTool {
    @Override
    public @NotNull String getRawId() {
        return "ferrudam_sword";
    }

    @Override
    public @Nullable TranslatableThing getTranslations() {
        return TranslatableThing.ofEngAndRu("Ferrudam Sword", "Ферродамовый меч");
    }

    @Override
    public @NotNull Material getMaterial() {
        return Material.DIAMOND_SWORD;
    }
    @Override
    protected void generateRecipes(@NotNull Consumer<Recipe> consumer) {
        ShapedRecipe recipe = new ShapedRecipe(getNewRecipeKey(), getItem());
        recipe.shape(
                "F",
                "F",
                "S");

        RecipeChoice.ExactChoice ferrudam = new RecipeChoice.ExactChoice(Items.FERRUDAM_INGOT.getItem());
        RecipeChoice.MaterialChoice stick = new RecipeChoice.MaterialChoice(Material.STICK);
        recipe.setIngredient('F', ferrudam);
        recipe.setIngredient('S', stick);

        consumer.accept(recipe);



        AlloyingRecipe recipeAlloy = new AlloyingRecipe(
                getNewRecipeKey(),
                List.of(new CustomSingleRecipeChoice(Items.FERRUDAM_INGOT)),
                new CustomCompatibleRecipeChoice(Set.of(), Tag.ITEMS_COALS.getValues()),
                List.of(),
                new CustomSingleRecipeChoice(Material.IRON_SWORD),
                getItem()
        );

        consumer.accept(recipeAlloy);
    }
}
