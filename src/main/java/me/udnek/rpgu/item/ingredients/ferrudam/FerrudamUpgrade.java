package me.udnek.rpgu.item.ingredients.ferrudam;

import me.udnek.coreu.custom.component.instance.TranslatableThing;
import me.udnek.coreu.custom.item.ConstructableCustomItem;
import org.bukkit.Material;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class FerrudamUpgrade extends ConstructableCustomItem {
    @Override
    public @NotNull String getRawId() {
        return "ferrudam_upgrade";
    }
    @Override
    public @Nullable TranslatableThing getTranslations() {
        return TranslatableThing.ofEngAndRu("Ferrudam Upgrade", "Феродамовое улучшение");
    }

    @Override
    protected void generateRecipes(@NotNull Consumer<Recipe> consumer) {
        ShapedRecipe recipe = new ShapedRecipe(getNewRecipeKey(), getItem().asQuantity(2));
        recipe.shape(
                "DID",
                "DAD",
                "DDD");

        recipe.setIngredient('I', new RecipeChoice.ExactChoice(getItem()));
        recipe.setIngredient('D',  new RecipeChoice.MaterialChoice(Material.DEEPSLATE));
        recipe.setIngredient('A', new RecipeChoice.MaterialChoice(Material.DIAMOND));

        consumer.accept(recipe);
    }
}
