package me.udnek.rpgu.item.block;

import com.destroystokyo.paper.MaterialTags;
import me.udnek.coreu.custom.component.instance.RightClickableItem;
import me.udnek.coreu.custom.component.instance.TranslatableThing;
import me.udnek.coreu.custom.item.ConstructableCustomItem;
import me.udnek.rpgu.entity.EntityTypes;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NullMarked;

import java.util.Objects;
import java.util.function.Consumer;

@NullMarked
public class InvisibleItemFrameItem extends ConstructableCustomItem {
    @Override
    public String getRawId() {
        return "invisible_item_frame";
    }

    @Override
    public @Nullable TranslatableThing getTranslations() {
        return TranslatableThing.ofEngAndRu("Invisible Item Frame", "Невидимая рамка");
    }

    @Override
    protected void generateRecipes(Consumer<Recipe> consumer) {
        ShapedRecipe recipe = new ShapedRecipe(getNewRecipeKey(), getItem());
        recipe.shape(
                "SSS",
                "SGS",
                "SSS");

        recipe.setIngredient('G', new RecipeChoice.MaterialChoice(MaterialTags.GLASS_PANES));
        recipe.setIngredient('S', new RecipeChoice.MaterialChoice(Material.STICK));

        consumer.accept(recipe);
    }

    @Override
    public void initializeComponents() {
        super.initializeComponents();
        getComponents().set((RightClickableItem) (customItem, playerInteractEvent) -> {
            if (!playerInteractEvent.hasBlock()) return;
            ItemStack item = playerInteractEvent.getItem();
            assert item != null;
            item.setAmount(item.getAmount() - 1);
            EntityTypes.INVISIBLE_ITEM_FRAME.spawn(Objects.requireNonNull(playerInteractEvent.getInteractionPoint()));
        });
    }
}
