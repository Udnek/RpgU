package me.udnek.rpgu.mechanic.machine.crusher;

import io.papermc.paper.datacomponent.DataComponentTypes;
import io.papermc.paper.datacomponent.item.ItemLore;
import me.udnek.coreu.util.Utils;
import me.udnek.jeiu.item.Items;
import me.udnek.jeiu.menu.RecipesMenu;
import me.udnek.jeiu.visualizer.implementation.AbstractRecipeVisualizer;
import me.udnek.rpgu.RpgU;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NullMarked;

import java.util.ArrayList;
import java.util.List;

@NullMarked
public class CrusherRecipeVisualizer extends AbstractRecipeVisualizer {
    public static final int OFFSET = RecipesMenu.VISUALIZER_X_OFFSET - 1;

    protected CrusherRecipe recipe;

    public CrusherRecipeVisualizer(CrusherRecipe recipe){
        this.recipe = recipe;
    }
    @Override
    public void visualize(RecipesMenu recipesMenu){
        super.visualize(recipesMenu);

        setChoice(CrusherInventory.STUFF_SLOT + OFFSET, recipe.getStuff().getFirst());

        List<ItemStack> results = getResultsWithChance(recipe);
        for (int index = 0; index < results.size(); index++) {
            menu.setItem(CrusherInventory.RESULT_SLOTS[index] + OFFSET, results.get(index));
        }

        menu.setItem(RecipesMenu.getRecipeStationPosition(), me.udnek.rpgu.item.Items.CRUSHER);
        menu.setThemedItem(CrusherInventory.FUEL_SLOT + OFFSET + 2, Items.FIRE_ICON);
        ItemStack banner = Items.BANNER.getItem();
        banner.editMeta(itemMeta -> itemMeta.setItemModel(new NamespacedKey(RpgU.getInstance(), "gui/crusher/banner")));
        menu.setThemedItem(RecipesMenu.getBannerPosition(), banner);
    }

    public List<ItemStack> getResultsWithChance(CrusherRecipe recipe) {
        List<ItemStack> newResults = new ArrayList<>();
        for (CrusherRecipe.ResultEntry resultEntry : recipe.results) {
            ItemStack result = resultEntry.getItem().clone();
            List<Component> lore = new ArrayList<>(result.getDataOrDefault(DataComponentTypes.LORE, ItemLore.lore().build()).lines());
            lore.add(Component.translatable("crusher.rpgu.probability",
                    List.of(Component.text(Utils.roundToTwoDigits(resultEntry.chance * 100)))).color(NamedTextColor.YELLOW));
            result.setData(DataComponentTypes.LORE, ItemLore.lore(lore));
            newResults.add(result);
        }
        return newResults;
    }

    @Override
    public @Nullable List<Component> getInformation(){
        return List.of(Component.text(recipe.getKey().asString()));
    }
}
