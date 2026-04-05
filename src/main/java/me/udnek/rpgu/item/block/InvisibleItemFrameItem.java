package me.udnek.rpgu.item.block;

import com.destroystokyo.paper.MaterialTags;
import me.udnek.coreu.custom.component.instance.SpawnEggItem;
import me.udnek.coreu.custom.component.instance.TranslatableThing;
import me.udnek.coreu.custom.item.ConstructableCustomItem;
import me.udnek.rpgu.entity.EntityTypes;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ItemFrame;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NullMarked;

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

        getComponents().set(new SpawnEggItem(EntityTypes.INVISIBLE_ITEM_FRAME) {
            @Override
            public @Nullable Entity onPlace(PlayerInteractEvent event) {
                ItemFrame frame = (ItemFrame) super.onPlace(event);
                if (frame != null) frame.setFacingDirection(event.getBlockFace(), true);
                return frame;
            }
        });
    }
}
