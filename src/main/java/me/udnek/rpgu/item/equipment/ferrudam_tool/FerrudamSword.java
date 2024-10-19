package me.udnek.rpgu.item.equipment.ferrudam_tool;

import me.udnek.itemscoreu.customitem.ConstructableCustomItem;
import me.udnek.itemscoreu.customrecipe.RecipeManager;
import me.udnek.itemscoreu.customrecipe.choice.CustomCompatibleRecipeChoice;
import me.udnek.itemscoreu.customrecipe.choice.CustomSingleRecipeChoice;
import me.udnek.rpgu.item.Items;
import me.udnek.rpgu.item.RpgUCustomItem;
import me.udnek.rpgu.mechanic.alloying.AlloyingRecipe;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Tag;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class FerrudamSword extends ConstructableCustomItem implements RpgUCustomItem {
    @Override
    public @Nullable Integer getCustomModelData() {return 1000;}
    @Override
    public @NotNull String getRawId() {return "ferrudam_sword";}
    @Override
    public @NotNull Material getMaterial() {return Material.DIAMOND_SWORD;}
    @Override
    public ItemFlag[] getTooltipHides() {return new ItemFlag[]{ItemFlag.HIDE_ATTRIBUTES};}
    @Override
    public boolean getAddDefaultAttributes() {return true;}
    @Override
    protected void generateRecipes(@NotNull Consumer<@NotNull Recipe> consumer) {
        ShapedRecipe recipe = new ShapedRecipe(this.getRecipeNamespace(0), getItem());
        recipe.shape(
                "F",
                "F",
                "S");

        RecipeChoice.ExactChoice flint = new RecipeChoice.ExactChoice(Items.FERRUDAM_INGOT.getItem());
        RecipeChoice.MaterialChoice stick = new RecipeChoice.MaterialChoice(Material.STICK);
        recipe.setIngredient('F', flint);
        recipe.setIngredient('S', stick);

        consumer.accept(recipe);

        List<CustomSingleRecipeChoice> alloys = new ArrayList<>();
        CustomSingleRecipeChoice magnetiteIngot = new CustomSingleRecipeChoice(Items.FERRUDAM_INGOT);
        alloys.add(magnetiteIngot);


        AlloyingRecipe recipeAlloy = new AlloyingRecipe(
                getRecipeNamespace(0),
                alloys,
                new CustomCompatibleRecipeChoice(List.of(), new ArrayList<>(Tag.ITEMS_COALS.getValues())),
                new CustomSingleRecipeChoice(Material.IRON_SWORD),
                getItem()
        );

        consumer.accept(recipeAlloy);
    }
}
