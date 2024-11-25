package me.udnek.rpgu.item.ingredients;

import me.udnek.itemscoreu.customitem.ConstructableCustomItem;
import me.udnek.itemscoreu.nms.Nms;
import me.udnek.itemscoreu.nms.loot.entry.NmsCustomLootEntryBuilder;
import me.udnek.itemscoreu.nms.loot.pool.NmsLootPoolBuilder;
import me.udnek.itemscoreu.nms.loot.util.ItemStackCreator;
import me.udnek.rpgu.item.Items;
import org.bukkit.Material;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.loot.LootTables;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class MagnetiteIngot extends ConstructableCustomItem {
    @Override
    public @NotNull String getRawId() {return "magnetite_ingot";}
    @Override
    public @NotNull Material getMaterial() {return Material.GUNPOWDER;}

    @Override
    protected void generateRecipes(@NotNull Consumer<@NotNull Recipe> consumer) {
        RecipeChoice.ExactChoice rawMagnetite = new RecipeChoice.ExactChoice(Items.RAW_MAGNETITE.getItem());

        FurnaceRecipe recipe = new FurnaceRecipe(
                getNewRecipeKey(),
                getItem(),
                rawMagnetite,
                 0.7f,
                 200
        );

        consumer.accept(recipe);
    }
    @Override
    public void afterInitialization() {
        super.afterInitialization();
        addToLootTables(LootTables.SIMPLE_DUNGEON, LootTables.ABANDONED_MINESHAFT, LootTables.JUNGLE_TEMPLE);
    }

    private void addToLootTables(LootTables ...lootTables) {
        for (LootTables lootTable : lootTables) {
            Nms.get().getLootTableContainer(lootTable.getLootTable()).addPool(
                    new NmsLootPoolBuilder(
                            NmsCustomLootEntryBuilder.fromVanilla(
                                    lootTable.getLootTable(),
                                    itemStack -> itemStack.getType() == Material.IRON_INGOT,
                                    new ItemStackCreator.Custom(Items.MAGNETITE_INGOT)
                            )
                    )
            );
        }
    }
}


