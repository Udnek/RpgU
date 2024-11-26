package me.udnek.rpgu.item.equipment.wolf;

import me.udnek.rpgu.item.Items;
import me.udnek.rpgu.util.AttributeManaging;
import org.bukkit.Material;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class WolfHelmet extends WolfArmor {
    @Override
    public @NotNull String getRawId() {return "wolf_helmet";}

    @Override
    public @NotNull Material getMaterial() {return Material.DIAMOND_HELMET;}

    @Override
    public void initializeAttributes(@NotNull ItemMeta itemMeta) {
        super.initializeAttributes(itemMeta);
        AttributeManaging.applyDefaultArmorAttribute(itemMeta, Material.LEATHER_HELMET);
    }

    @Override
    protected void generateRecipes(@NotNull Consumer<@NotNull Recipe> consumer) {
        ShapedRecipe recipe = new ShapedRecipe(getNewRecipeKey(), this.getItem());
        recipe.shape(
                "WWW",
                "W W");

        recipe.setIngredient('W', new RecipeChoice.ExactChoice(Items.WOLF_PELT.getItem()));

        consumer.accept(recipe);
    }
}