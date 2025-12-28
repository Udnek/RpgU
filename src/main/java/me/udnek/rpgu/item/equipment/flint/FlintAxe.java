package me.udnek.rpgu.item.equipment.flint;

import me.udnek.coreu.custom.component.instance.TranslatableThing;
import me.udnek.rpgu.item.Items;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class FlintAxe extends FlintTool{
    @Override
    public @NotNull String getRawId() {return "flint_axe";}
    @Override
    public @NotNull Material getMaterial() {return Material.STONE_AXE;}
    @Override
    public @Nullable TranslatableThing getTranslations() {return TranslatableThing.ofEngAndRu("Flint Axe", "Кремневый топор");}

    @Override
    protected void generateRecipes(@NotNull Consumer<@NotNull Recipe> consumer) {
        ShapedRecipe recipe = new ShapedRecipe(getNewRecipeKey(), getItem());
        recipe.shape(
                "FC ",
                "FSA",
                " S ");

        recipe.setIngredient('S', new RecipeChoice.MaterialChoice(Material.STICK));
        recipe.setIngredient('A', new RecipeChoice.ExactChoice(Items.FABRIC.getItem()));
        recipe.setIngredient('F', new RecipeChoice.MaterialChoice(Material.FLINT));
        recipe.setIngredient('C', new RecipeChoice.MaterialChoice(Tag.ITEMS_STONE_TOOL_MATERIALS));

        consumer.accept(recipe);
    }
}
