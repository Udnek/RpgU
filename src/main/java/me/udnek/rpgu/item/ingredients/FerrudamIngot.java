package me.udnek.rpgu.item.ingredients;

import me.udnek.coreu.custom.component.instance.TranslatableThing;
import me.udnek.coreu.custom.item.ConstructableCustomItem;
import me.udnek.coreu.custom.recipe.choice.CustomSingleRecipeChoice;
import me.udnek.rpgu.item.Items;
import me.udnek.rpgu.mechanic.alloying.AlloyingRecipe;
import org.bukkit.Material;
import org.bukkit.inventory.Recipe;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class FerrudamIngot extends ConstructableCustomItem {
    @Override
    public @NotNull String getRawId() {return "ferrudam_ingot";}
    @Override
    public @Nullable TranslatableThing getTranslations() {return TranslatableThing.ofEngAndRu("Ferrudam Ingot", "Ферродамовый слиток");}
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