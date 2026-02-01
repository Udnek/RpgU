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
import org.bukkit.Tag;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.loot.LootTables;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class Fabric extends ConstructableCustomItem {
    @Override
    public @NotNull String getRawId() {
        return "fabric";}
    @Override
    public @Nullable TranslatableThing getTranslations() {
        return TranslatableThing.ofEngAndRu("Fabric", "Ткань");}

    @Override
    protected void generateRecipes(@NotNull Consumer<Recipe> consumer) {
        ShapedRecipe recipe = new ShapedRecipe(getNewRecipeKey(), getItem().add());
        recipe.shape("SWS");

        RecipeChoice.MaterialChoice string = new RecipeChoice.MaterialChoice(Material.STRING);
        RecipeChoice.MaterialChoice wool = new RecipeChoice.MaterialChoice(Tag.WOOL);
        recipe.setIngredient('S', string);
        recipe.setIngredient('W', wool);

        consumer.accept(recipe);
    }
    @Override
    public void globalInitialization() {
        super.globalInitialization();
        PoolWrapper lootPoolBuilder = new PoolWrapper.Builder(
                new NmsCustomEntry.Builder(new ItemStackCreator.Custom(Items.FABRIC)).fromVanilla(
                        LootTables.BLAZE.getLootTable(),
                        itemStack -> ItemUtils.isVanillaMaterial(itemStack, Material.BLAZE_ROD)
                ).buildAndWrap()
        ).build();

        Nms.get().getLootTableWrapper(LootTables.ZOMBIE.getLootTable()).addPool(lootPoolBuilder);
        Nms.get().getLootTableWrapper(LootTables.HUSK.getLootTable()).addPool(lootPoolBuilder);
        Nms.get().getLootTableWrapper(LootTables.DROWNED.getLootTable()).addPool(lootPoolBuilder);
    }
}
