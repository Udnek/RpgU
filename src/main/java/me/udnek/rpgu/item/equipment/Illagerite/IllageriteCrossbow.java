package me.udnek.rpgu.item.equipment.Illagerite;

import me.udnek.coreu.custom.component.instance.AutoGeneratingFilesItem;
import me.udnek.coreu.custom.component.instance.TranslatableThing;
import me.udnek.coreu.custom.item.ConstructableCustomItem;
import me.udnek.coreu.custom.item.RepairData;
import me.udnek.coreu.custom.recipe.choice.CustomCompatibleRecipeChoice;
import me.udnek.coreu.custom.recipe.choice.CustomSingleRecipeChoice;
import me.udnek.coreu.rpgu.component.RPGUComponents;
import me.udnek.rpgu.component.ability.vanilla.CrossbowAbility;
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

public class IllageriteCrossbow extends ConstructableCustomItem {

    @Override
    public @NotNull Material getMaterial() {
        return Material.CROSSBOW;
    }
    @Override
    public @NotNull String getRawId() {
        return "illagerite_crossbow";
    }
    @Override
    public @Nullable TranslatableThing getTranslations() {
        return TranslatableThing.ofEngAndRu("Iillagerite Crossbow", "Злодеянитовый арбалет");
    }

    @Override
    protected void generateRecipes(@NotNull Consumer<Recipe> consumer) {
        var ingot = new CustomSingleRecipeChoice(Items.ILLAGERITE_INGOT);

        AlloyingRecipe recipeAlloy = new AlloyingRecipe(
                getNewRecipeKey(),
                List.of(new CustomSingleRecipeChoice(Items.ILLAGERITE_UPGRADE)),
                new CustomCompatibleRecipeChoice(Set.of(), Tag.ITEMS_COALS.getValues()),
                List.of(ingot, ingot),
                new CustomSingleRecipeChoice(Material.CROSSBOW),
                getItem()
        );

        consumer.accept(recipeAlloy);
    }

    @Override
    public void initializeComponents() {
        super.initializeComponents();
        getComponents().getOrCreateDefault(RPGUComponents.ACTIVE_ABILITY_ITEM).getComponents().set(new CrossbowAbility());
        getComponents().set(AutoGeneratingFilesItem.CROSSBOW);
    }

    @Override
    public @Nullable RepairData initializeRepairData() {
        return new RepairData(Items.ILLAGERITE_INGOT);
    }
}
