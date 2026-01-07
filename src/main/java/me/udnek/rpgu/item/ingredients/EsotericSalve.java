package me.udnek.rpgu.item.ingredients;

import me.udnek.coreu.custom.component.instance.TranslatableThing;
import me.udnek.coreu.custom.item.ConstructableCustomItem;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapelessRecipe;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class EsotericSalve extends ConstructableCustomItem {
    @Override
    public @NotNull String getRawId() {return "esoteric_salve";}

    @Override
    public @Nullable TranslatableThing getTranslations() {
        return TranslatableThing.ofEngAndRu("Esoteric Salve", "Эзотерическая мазь");
    }

    @Override
    protected void generateRecipes(@NotNull Consumer<Recipe> consumer) {
        ShapelessRecipe recipe = new ShapelessRecipe(getNewRecipeKey(), getItem().add(1));

        recipe.addIngredient(new RecipeChoice.MaterialChoice(Material.GLOW_INK_SAC));
        recipe.addIngredient(new RecipeChoice.MaterialChoice(Material.SWEET_BERRIES));
        recipe.addIngredient(new RecipeChoice.MaterialChoice(Tag.ITEMS_FLOWERS));

        consumer.accept(recipe);
    }
}
