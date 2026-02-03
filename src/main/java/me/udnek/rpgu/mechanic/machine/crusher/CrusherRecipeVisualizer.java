package me.udnek.rpgu.mechanic.machine.crusher;

import me.udnek.jeiu.item.Items;
import me.udnek.jeiu.menu.RecipesMenu;
import me.udnek.jeiu.visualizer.implementation.AbstractRecipeVisualizer;
import me.udnek.rpgu.RpgU;
import net.kyori.adventure.text.Component;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CrusherRecipeVisualizer extends AbstractRecipeVisualizer {
    public static final int OFFSET = RecipesMenu.VISUALIZER_X_OFFSET - 1;

    protected @NotNull CrusherRecipe recipe;
    public CrusherRecipeVisualizer(@NotNull CrusherRecipe recipe){
        this.recipe = recipe;
    }
    @Override
    public void visualize(@NotNull RecipesMenu recipesMenu){
        super.visualize(recipesMenu);

        setChoice(CrusherInventory.STUFF_SLOT + OFFSET, recipe.getStuff().getFirst());

        List<ItemStack> results = recipe.getResultsWithChance();
        for (int index = 0; index < results.size(); index++) {
            menu.setItem(CrusherInventory.RESULT_SLOTS[index] + OFFSET, results.get(index));
        }

        menu.setItem(RecipesMenu.getRecipeStationPosition(), me.udnek.rpgu.item.Items.CRUSHER);
        menu.setThemedItem(CrusherInventory.FUEL_SLOT + OFFSET + 2, Items.FIRE_ICON);
        ItemStack banner = Items.BANNER.getItem();
        banner.editMeta(itemMeta -> itemMeta.setItemModel(new NamespacedKey(RpgU.getInstance(), "gui/crusher/banner")));
        menu.setThemedItem(RecipesMenu.getBannerPosition(), banner);
    }
    @Override
    public @Nullable List<Component> getInformation(){
        return List.of(Component.text(recipe.getKey().asString()));
    }
}
