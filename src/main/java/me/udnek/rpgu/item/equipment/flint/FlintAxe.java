package me.udnek.rpgu.item.equipment.flint;

import me.udnek.rpgu.item.Items;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class FlintAxe extends FlintTool{
    @Override
    public @NotNull String getRawId() {return "flint_axe";}
    @Override
    public @NotNull Material getMaterial() {return Material.STONE_AXE;}
    @Override
    public ItemFlag[] getTooltipHides() {return new ItemFlag[]{ItemFlag.HIDE_ATTRIBUTES};}
    @Override
    public boolean getAddDefaultAttributes() {return true;}

    @Override
    protected void generateRecipes(@NotNull Consumer<@NotNull Recipe> consumer) {
        ShapedRecipe recipe = new ShapedRecipe(getNewRecipeKey(), getItem());
        recipe.shape(
                "FF ",
                "FSK",
                " S ");

        RecipeChoice.MaterialChoice flint = new RecipeChoice.MaterialChoice(Material.FLINT);
        RecipeChoice.MaterialChoice stick = new RecipeChoice.MaterialChoice(Material.STICK);
        RecipeChoice.ExactChoice fabric = new RecipeChoice.ExactChoice(Items.FABRIC.getItem());
        recipe.setIngredient('K', fabric);
        recipe.setIngredient('F', flint);
        recipe.setIngredient('S', stick);

        consumer.accept(recipe);
    }
}
