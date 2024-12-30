package me.udnek.rpgu.item.equipment.wolf;

import me.udnek.rpgu.item.Items;
import me.udnek.rpgu.vanilla.AttributeManaging;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class WolfChestplate extends AbstractWolfArmor {
    @Override
    public @NotNull String getRawId() {return "wolf_chestplate";}

    @Override
    public @NotNull Material getMaterial() {return Material.DIAMOND_CHESTPLATE;}

    @Override
    public void initializeAdditionalAttributes(@NotNull ItemStack itemStack) {
        super.initializeAdditionalAttributes(itemStack);
        AttributeManaging.applyDefaultArmorAttribute(itemStack, Material.LEATHER_CHESTPLATE);
    }

    @Override
    protected void generateRecipes(@NotNull Consumer<@NotNull Recipe> consumer) {
        ShapedRecipe recipe = new ShapedRecipe(getNewRecipeKey(), this.getItem());
        recipe.shape(
                "W W",
                "WWW",
                "WWW");

        recipe.setIngredient('W', new RecipeChoice.ExactChoice(Items.WOLF_PELT.getItem()));

        consumer.accept(recipe);
    }
}