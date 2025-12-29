package me.udnek.rpgu.item.ingredients;

import me.udnek.coreu.custom.component.instance.TranslatableThing;
import me.udnek.coreu.custom.item.ConstructableCustomItem;
import org.bukkit.Material;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class IngotMold extends ConstructableCustomItem {
    @Override
    public @NotNull String getRawId() {return "ingot_mold";}
    @Override
    public @Nullable TranslatableThing getTranslations() {return TranslatableThing.ofEngAndRu("Ingot Mold", "Литейная форма");}

    @Override
    protected void generateRecipes(@NotNull Consumer<@NotNull Recipe> consumer) {
        ShapedRecipe recipe = new ShapedRecipe(getNewRecipeKey(), getItem());
        recipe.shape(
                "C C",
                " C ");

        RecipeChoice.MaterialChoice clayBall = new RecipeChoice.MaterialChoice(Material.CLAY_BALL);
        recipe.setIngredient('C', clayBall);

        consumer.accept(recipe);
    }
}
