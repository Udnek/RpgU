package me.udnek.rpgu.mechanic.machine;

import me.udnek.coreu.custom.item.ItemUtils;
import me.udnek.coreu.custom.recipe.CustomRecipe;
import me.udnek.coreu.custom.recipe.choice.CustomRecipeChoice;
import me.udnek.coreu.custom.recipe.choice.CustomSingleRecipeChoice;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public abstract class AbstractMachineRecipe implements CustomRecipe {

    protected final List<CustomSingleRecipeChoice> alloys;
    protected final CustomRecipeChoice fuel;
    protected ItemStack result;
    protected final NamespacedKey id;
    protected final int craftDuration = 20 * 20;

    public AbstractMachineRecipe(@NotNull NamespacedKey key, @NotNull List<CustomSingleRecipeChoice> alloys, @NotNull CustomRecipeChoice fuel, @NotNull ItemStack result) {
        this.alloys = new ArrayList<>(alloys);
        this.fuel = fuel;
        this.result = result.clone();
        this.id = key;
    }

    public @NotNull CustomRecipeChoice getFuel() {return fuel;}
    public @NotNull List<CustomSingleRecipeChoice> getAlloys() {return new ArrayList<>(alloys);}
    public int getCraftDuration() {return craftDuration;}

    @Override
    public void replaceItem(@NotNull ItemStack oldItem, @NotNull ItemStack newItem) {
        for (CustomSingleRecipeChoice alloy : alloys) {
            alloy.replaceItem(oldItem, newItem);
        }
        fuel.replaceItem(oldItem, newItem);
        if (isResult(oldItem)){
            result = newItem.clone();
        }
    }
    @Override
    public @NotNull Collection<ItemStack> getResults() {
        return Collections.singleton(getResult());
    }
    @Override
    public boolean isResult(@NotNull ItemStack itemStack) {
        return ItemUtils.isSameIds(result, itemStack);
    }
    @Override
    public boolean isIngredient(@NotNull ItemStack itemStack) {
        for (CustomRecipeChoice alloyInput : alloys) {
            if (alloyInput.test(itemStack)) return true;
        }
        return fuel.test(itemStack);
    }
    @Override
    public @NotNull ItemStack getResult() {
        return result.clone();
    }
    @Override
    public @NotNull NamespacedKey getKey() {
        return id;
    }
}
