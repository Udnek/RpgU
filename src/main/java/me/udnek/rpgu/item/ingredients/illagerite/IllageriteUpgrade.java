package me.udnek.rpgu.item.ingredients.illagerite;

import me.udnek.coreu.custom.component.instance.TranslatableThing;
import me.udnek.coreu.custom.item.ConstructableCustomItem;
import org.bukkit.Material;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class IllageriteUpgrade extends ConstructableCustomItem {
    @Override
    public @NotNull String getRawId() {
        return "illagerite_upgrade";
    }
    @Override
    public @Nullable TranslatableThing getTranslations() {
        return TranslatableThing.ofEngAndRu("Illagerite upgrade", "Злодеянитовое улучшение");}

    @Override
    protected void generateRecipes(@NotNull Consumer<Recipe> consumer) {
        ShapedRecipe recipe = new ShapedRecipe(getNewRecipeKey(), getItem().asQuantity(2));
        recipe.shape(
                "EIE",
                "EDE",
                "EEE");

        recipe.setIngredient('I', new RecipeChoice.ExactChoice(getItem()));
        recipe.setIngredient('E',  new RecipeChoice.MaterialChoice(Material.EMERALD));
        recipe.setIngredient('D', new RecipeChoice.MaterialChoice(Material.DIAMOND));

        consumer.accept(recipe);
    }
}
