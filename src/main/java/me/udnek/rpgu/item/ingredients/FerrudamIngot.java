package me.udnek.rpgu.item.ingredients;

import me.udnek.itemscoreu.customitem.ConstructableCustomItem;
import me.udnek.itemscoreu.customrecipe.choice.CustomSingleRecipeChoice;
import me.udnek.rpgu.item.Items;
import me.udnek.rpgu.item.RpgUCustomItem;
import me.udnek.rpgu.mechanic.alloying.AlloyingRecipe;
import org.bukkit.Material;
import org.bukkit.inventory.Recipe;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class FerrudamIngot extends ConstructableCustomItem implements RpgUCustomItem {
    @Override
    public @NotNull String getRawId() {return "ferrudam_ingot";}
    @Override
    public @NotNull Material getMaterial() {return Material.GUNPOWDER;}
    @Override
    protected void generateRecipes(@NotNull Consumer<@NotNull Recipe> consumer) {
        List<CustomSingleRecipeChoice> alloys = new ArrayList<>();
        CustomSingleRecipeChoice ironIngot = new CustomSingleRecipeChoice(Material.IRON_INGOT);
        alloys.add(ironIngot);
        alloys.add(ironIngot);
        alloys.add(ironIngot);
        CustomSingleRecipeChoice diamondIngot = new CustomSingleRecipeChoice(Material.DIAMOND);
        alloys.add(diamondIngot);
        alloys.add(diamondIngot);

        AlloyingRecipe recipe = new AlloyingRecipe(
                getNewRecipeKey(),
                alloys,
                new CustomSingleRecipeChoice(Items.BLAST_COAL),
                new CustomSingleRecipeChoice(Items.INGOT_MOLD),
                getItem()
        );

        consumer.accept(recipe);
    }
}
