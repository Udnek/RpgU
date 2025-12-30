package me.udnek.rpgu.mechanic.enchanting;

import me.udnek.coreu.custom.recipe.choice.CustomRecipeChoice;
import me.udnek.coreu.custom.recipe.choice.CustomSingleRecipeChoice;
import me.udnek.jeiu.JeiU;
import me.udnek.jeiu.item.Items;
import me.udnek.jeiu.menu.RecipesMenu;
import me.udnek.jeiu.visualizer.implementation.AbstractRecipeVisualizer;
import me.udnek.rpgu.RpgU;
import me.udnek.rpgu.mechanic.enchanting.upgrade.EnchantingTableUpgrade;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class EnchantingRecipeVisualizer extends AbstractRecipeVisualizer {

    public static final int PASSION_OFFSET = 16+1+ RecipesMenu.VISUALIZER_X_OFFSET;
    public static final int UPGRADES_OFFSET = -2+1+ RecipesMenu.VISUALIZER_X_OFFSET;
    public static final int LAPIS_SLOT = 0+1+ RecipesMenu.VISUALIZER_X_OFFSET;
    public static final int BOOK_SLOT = 1+1+ RecipesMenu.VISUALIZER_X_OFFSET;
    public static final int RESULT_SLOT = 9*5+1+1+ RecipesMenu.VISUALIZER_X_OFFSET;

    protected @NotNull EnchantingRecipe recipe;
    public EnchantingRecipeVisualizer(@NotNull EnchantingRecipe recipe){
        this.recipe = recipe;
    }
    @Override
    public void visualize(@NotNull RecipesMenu recipesMenu){
        super.visualize(recipesMenu);
        List<CustomSingleRecipeChoice> inputs = recipe.getPassions();
        for (int i = 0; i < inputs.size(); i++) {
            setPassion(i, inputs.get(i));
        }

        List<EnchantingTableUpgrade> upgrades = new ArrayList<>(recipe.getUpgrades());
        for (int i = 0; i < upgrades.size(); i++) {
            setUpgrade(i, upgrades.get(i));
        }
        menu.setItem(LAPIS_SLOT, new ItemStack(Material.LAPIS_LAZULI));
        menu.setItem(BOOK_SLOT, new ItemStack(Material.BOOK));
        menu.setItem(RESULT_SLOT, recipe.getResult());

        menu.setItem(RecipesMenu.getRecipeStationPosition(), Material.ENCHANTING_TABLE);

        ItemStack banner = Items.BANNER.getItem();
        banner.editMeta(itemMeta -> itemMeta.setItemModel(new NamespacedKey(RpgU.getInstance(), "gui/enchanting/banner")));
        menu.setThemedItem(RecipesMenu.getBannerPosition(), banner);
    }
    public void setPassion(int index, @NotNull CustomRecipeChoice choice){
        setChoice(EnchantingTableInventory.PASSION_SLOTS[index] + PASSION_OFFSET, choice);
    }

    public void setUpgrade(int index, @NotNull EnchantingTableUpgrade upgrade){
        menu.setItem(EnchantingTableInventory.UPGRADE_SLOTS[index] + UPGRADES_OFFSET, upgrade.getIcon());
    }
    @Override
    public @Nullable List<Component> getInformation(){
        return List.of(Component.text(recipe.getKey().asString()));
    }

}
