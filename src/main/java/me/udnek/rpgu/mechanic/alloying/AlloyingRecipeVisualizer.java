package me.udnek.rpgu.mechanic.alloying;

import me.udnek.itemscoreu.customrecipe.choice.CustomRecipeChoice;
import me.udnek.itemscoreu.customrecipe.choice.CustomSingleRecipeChoice;
import me.udnek.jeiu.item.Items;
import me.udnek.jeiu.menu.RecipesMenu;
import me.udnek.jeiu.visualizer.abstraction.AbstractVisualizer;
import me.udnek.rpgu.RpgU;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class AlloyingRecipeVisualizer extends AbstractVisualizer {
    public static final int OFFSET = 9;

    protected AlloyingRecipe recipe;
    public AlloyingRecipeVisualizer(AlloyingRecipe recipe){
        this.recipe = recipe;
    }
    @Override
    public void visualize(@NotNull RecipesMenu recipesMenu){
        super.visualize(recipesMenu);

        List<CustomSingleRecipeChoice> alloys = recipe.getAlloys();
        for (int i = 0; i < alloys.size(); i++) {
            CustomSingleRecipeChoice alloy = alloys.get(i);
            setAlloy(i, alloy);
        }
        setChoice(AlloyForgeInventory.FUEL_SLOT + OFFSET, recipe.getFuel());
        setChoice(AlloyForgeInventory.ADDITION_SLOT + OFFSET, recipe.getAddition());
        menu.setItem(AlloyForgeInventory.RESULT_SLOT + OFFSET-1, recipe.getResult());

        menu.setItem(RecipesMenu.getRecipeStationPosition(), Material.BLAST_FURNACE);
        menu.setThemedItem(AlloyForgeInventory.FUEL_SLOT + OFFSET - 9, Items.FIRE_ICON);
        ItemStack banner = Items.BANNER.getItem();
        banner.editMeta(itemMeta -> itemMeta.setItemModel(new NamespacedKey(RpgU.getInstance(), "gui/alloying/banner")));
        menu.setThemedItem(RecipesMenu.getBannerPosition(), banner);
    }
    public void setAlloy(int index, CustomRecipeChoice choice){
        setChoice(AlloyForgeInventory.ALLOYS_SLOTS[index] + OFFSET, choice);
    }
    @Override
    public @Nullable List<Component> getInformation(){
        return List.of(Component.text(recipe.getKey().asString()));
    }
}










