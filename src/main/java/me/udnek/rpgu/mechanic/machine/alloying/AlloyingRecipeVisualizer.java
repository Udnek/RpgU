package me.udnek.rpgu.mechanic.machine.alloying;

import me.udnek.coreu.custom.recipe.choice.CustomSingleRecipeChoice;
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

public class AlloyingRecipeVisualizer extends AbstractRecipeVisualizer {
    public static final int OFFSET = RecipesMenu.VISUALIZER_X_OFFSET + 1;

    protected @NotNull AlloyingRecipe recipe;
    public AlloyingRecipeVisualizer(@NotNull AlloyingRecipe recipe){
        this.recipe = recipe;
    }
    @Override
    public void visualize(@NotNull RecipesMenu recipesMenu){
        super.visualize(recipesMenu);

        List<CustomSingleRecipeChoice> stuffs = recipe.getStuff();
        for (int index = 0; index < stuffs.size(); index++) {
            setChoice(AlloyForgeInventory.STUFF_SLOTS[index] + OFFSET, stuffs.get(index));
        }

        List<CustomSingleRecipeChoice> alloys = recipe.getAlloys();
        for (int index = 0; index < alloys.size(); index++) {
            setChoice(AlloyForgeInventory.ALLOYS_SLOTS[index] + OFFSET, alloys.get(index));
        }

        setChoice(AlloyForgeInventory.FUEL_SLOT + OFFSET, recipe.getFuel());
        setChoice(AlloyForgeInventory.ADDITION_SLOT + OFFSET, recipe.getAddition());
        menu.setItem(AlloyForgeInventory.RESULT_SLOT + OFFSET-1, recipe.getResult());

        menu.setItem(RecipesMenu.getRecipeStationPosition(), me.udnek.rpgu.item.Items.ALLOY_FORGE);
        menu.setThemedItem(AlloyForgeInventory.FUEL_SLOT + OFFSET - 9, Items.FIRE_ICON);
        ItemStack banner = Items.BANNER.getItem();
        banner.editMeta(itemMeta -> itemMeta.setItemModel(new NamespacedKey(RpgU.getInstance(), "gui/alloying/banner")));
        menu.setThemedItem(RecipesMenu.getBannerPosition(), banner);
    }
    @Override
    public @Nullable List<Component> getInformation(){
        return List.of(Component.text(recipe.getKey().asString()));
    }
}










