package me.udnek.rpgu.item;

import me.udnek.itemscoreu.customitem.ConstructableCustomItem;
import org.bukkit.Material;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class TotemOfSaving extends ConstructableCustomItem implements RpgUCustomItem {
    @Override
    public @NotNull String getRawId() {return "totem_of_saving";}

    @Override
    public @NotNull Material getMaterial() {return Material.GUNPOWDER;}

    @Override
    protected void generateRecipes(@NotNull Consumer<@NotNull Recipe> consumer) {
        ShapedRecipe recipe = new ShapedRecipe(getNewRecipeKey(), this.getItem().add());
        recipe.shape(
                " P ",
                "LCL");

        RecipeChoice.MaterialChoice lapis = new RecipeChoice.MaterialChoice(Material.LAPIS_LAZULI);
        RecipeChoice.MaterialChoice chest = new RecipeChoice.MaterialChoice(Material.CHEST);
        RecipeChoice.MaterialChoice pumpkin = new RecipeChoice.MaterialChoice(Material.CARVED_PUMPKIN);
        recipe.setIngredient('L', lapis);
        recipe.setIngredient('C', chest);
        recipe.setIngredient('P', pumpkin);

        consumer.accept(recipe);
    }
}
