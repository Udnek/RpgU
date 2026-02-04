package me.udnek.rpgu.item.equipment.ferrudam.tool;

import me.udnek.coreu.custom.component.instance.TranslatableThing;
import me.udnek.coreu.custom.recipe.choice.CustomCompatibleRecipeChoice;
import me.udnek.coreu.custom.recipe.choice.CustomSingleRecipeChoice;
import me.udnek.rpgu.item.Items;
import me.udnek.rpgu.mechanic.machine.alloying.AlloyingRecipe;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.inventory.Recipe;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

public class FerrudamHoe extends FerrudamTool {
    @Override
    public @NotNull String getRawId() {
        return "ferrudam_hoe";
    }

    @Override
    public @Nullable TranslatableThing getTranslations() {
        return TranslatableThing.ofEngAndRu("Ferrudam Hoe", "Ферродамовая мотыга");
    }

    @Override
    public @NotNull Material getMaterial() {
        return Material.DIAMOND_HOE;
    }
    @Override
    protected void generateRecipes(@NotNull Consumer<Recipe> consumer) {
        AlloyingRecipe recipeAlloy = new AlloyingRecipe(
                getNewRecipeKey(),
                List.of(new CustomSingleRecipeChoice(Items.FERRUDAM_UPGRADE)),
                new CustomCompatibleRecipeChoice(Set.of(), Tag.ITEMS_COALS.getValues()),
                List.of(new CustomSingleRecipeChoice(Items.FERRUDAM_INGOT)),
                new CustomSingleRecipeChoice(Material.IRON_HOE),
                getItem()
        );

        consumer.accept(recipeAlloy);
    }
}
