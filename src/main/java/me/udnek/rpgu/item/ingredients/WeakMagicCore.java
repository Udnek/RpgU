package me.udnek.rpgu.item.ingredients;

import me.udnek.coreu.custom.component.instance.TranslatableThing;
import me.udnek.coreu.custom.item.ConstructableCustomItem;
import me.udnek.rpgu.item.Items;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class WeakMagicCore extends ConstructableCustomItem {
    @Override
    public @NotNull String getRawId() {return "weak_magic_core";}

    @Override
    public @Nullable TranslatableThing getTranslations() {
        return TranslatableThing.ofEngAndRu("Weak Magic Core", "Слабое магическое ядро");
    }

    @Override
    protected void generateRecipes(@NotNull Consumer<@NotNull Recipe> consumer) {
        ShapedRecipe recipe = new ShapedRecipe(getNewRecipeKey(), getItem());
        recipe.shape(
                "EA",
                "AE");

        recipe.setIngredient('A', new RecipeChoice.MaterialChoice(Material.AMETHYST_SHARD));
        recipe.setIngredient('E', new RecipeChoice.ExactChoice(Items.ESOTERIC_SALVE.getItem()));

        consumer.accept(recipe);
    }
}
