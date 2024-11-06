package me.udnek.rpgu.item.utility;

import me.udnek.itemscoreu.customitem.ConstructableCustomItem;
import me.udnek.rpgu.item.RpgUCustomItem;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class TotemOfSavingItem extends ConstructableCustomItem implements RpgUCustomItem {
    @Override
    public @NotNull String getRawId() {return "totem_of_saving";}

    @Override
    public @NotNull Material getMaterial() {return Material.GUNPOWDER;}

    @Override
    public void getLore(@NotNull Consumer<Component> consumer) {
        consumer.accept(Component.translatable(getRawItemName() + ".description.0").decoration(TextDecoration.ITALIC, false).color(NamedTextColor.GRAY));
        consumer.accept(Component.translatable(getRawItemName() + ".description.1").decoration(TextDecoration.ITALIC, false).color(NamedTextColor.GRAY));
    }

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
