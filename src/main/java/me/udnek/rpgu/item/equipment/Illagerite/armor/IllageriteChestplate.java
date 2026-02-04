package me.udnek.rpgu.item.equipment.Illagerite.armor;

import me.udnek.coreu.custom.recipe.choice.CustomCompatibleRecipeChoice;
import me.udnek.coreu.custom.recipe.choice.CustomSingleRecipeChoice;
import me.udnek.rpgu.item.Items;
import me.udnek.rpgu.mechanic.machine.alloying.AlloyingRecipe;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.inventory.Recipe;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

public class IllageriteChestplate extends IllageriteArmor {

    public IllageriteChestplate() {
        super(Material.DIAMOND_CHESTPLATE, "illagerite_chestplate", "Illagerite Chestplate", "Злодеянитовый нагрудник");
    }

    @Override
    protected void generateRecipes(@NotNull Consumer<Recipe> consumer) {
        var ingot = new CustomSingleRecipeChoice(Items.ILLAGERITE_INGOT);

        AlloyingRecipe recipeAlloy = new AlloyingRecipe(
                getNewRecipeKey(),
                List.of(new CustomSingleRecipeChoice(Items.ILLAGERITE_UPGRADE)),
                new CustomCompatibleRecipeChoice(Set.of(), Tag.ITEMS_COALS.getValues()),
                List.of(ingot, ingot, ingot, ingot),
                new CustomSingleRecipeChoice(Items.FERRUDAM_CHESTPLATE),
                getItem()
        );

        consumer.accept(recipeAlloy);
    }
}
