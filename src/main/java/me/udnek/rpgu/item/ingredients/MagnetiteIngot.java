package me.udnek.rpgu.item.ingredients;

import me.udnek.coreu.custom.component.instance.TranslatableThing;
import me.udnek.coreu.custom.item.ConstructableCustomItem;
import me.udnek.coreu.custom.item.ItemUtils;
import me.udnek.coreu.nms.Nms;
import me.udnek.coreu.nms.loot.entry.NmsCustomEntry;
import me.udnek.coreu.nms.loot.pool.PoolWrapper;
import me.udnek.coreu.nms.loot.util.ItemStackCreator;
import me.udnek.rpgu.item.Items;
import org.bukkit.Material;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.loot.LootTables;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class MagnetiteIngot extends ConstructableCustomItem {

    @Override
    public @NotNull String getRawId() {return "magnetite_ingot";}

    @Override
    public @Nullable TranslatableThing getTranslations() {
        return TranslatableThing.ofEngAndRu("Magnetite Ingot", "Магнетитовый слиток");
    }

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
    public void globalInitialization() {
        super.globalInitialization();
        addToLootTables(LootTables.SIMPLE_DUNGEON, LootTables.ABANDONED_MINESHAFT, LootTables.JUNGLE_TEMPLE);
    }

    private void addToLootTables(@NotNull LootTables ...lootTables) {
        for (LootTables lootTable : lootTables) {
            Nms.get().getLootTableWrapper(lootTable.getLootTable()).addPool(
                    new PoolWrapper.Builder(
                            new NmsCustomEntry.Builder(new ItemStackCreator.Custom(this)).fromVanilla(
                                    lootTable.getLootTable(),
                                    stack -> ItemUtils.isVanillaMaterial(stack, Material.IRON_INGOT)
                            ).buildAndWrap()
                    ).build()
            );
        }
    }
}


