package me.udnek.rpgu.item.block;

import me.udnek.coreu.custom.component.instance.BlockPlacingItem;
import me.udnek.coreu.custom.component.instance.TranslatableThing;
import me.udnek.coreu.custom.item.ConstructableCustomItem;
import me.udnek.rpgu.block.Blocks;
import me.udnek.rpgu.item.Items;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.inventory.ItemRarity;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class SoulBinderItem extends ConstructableCustomItem {

    @Override
    public @NotNull Material getMaterial() {
        return Material.BARRIER;
    }

    @Override
    public @NotNull String getRawId() {
        return "soul_binder";
    }

    @Override
    public @Nullable TranslatableThing getTranslations() {
        return TranslatableThing.ofEngAndRu("Soul Binder", "Связыватель души");
    }

    @Override
    public @Nullable DataSupplier<ItemRarity> getRarity() {
        return DataSupplier.of(ItemRarity.COMMON);
    }

    @Override
    public void initializeComponents() {
        super.initializeComponents();
        getComponents().set(new BlockPlacingItem(Blocks.SOUL_BINDER));
    }

    @Override
    public void getLore(@NotNull Consumer<Component> consumer) {
        consumer.accept(Component.translatable(translationKey()+".description.0")
                .decoration(TextDecoration.ITALIC, false).color(NamedTextColor.GRAY));
    }

    @Override
    protected void generateRecipes(@NotNull Consumer<@NotNull Recipe> consumer) {
        ShapedRecipe recipe = new ShapedRecipe(getNewRecipeKey(), getItem());
        recipe.shape(
                "MSM",
                "MSM",
                "MMM");

        recipe.setIngredient('M', new RecipeChoice.ExactChoice(Items.MAGNETITE_INGOT.getItem()));
        recipe.setIngredient('S', new RecipeChoice.MaterialChoice(Material.SOUL_SAND));
        consumer.accept(recipe);
    }
}
