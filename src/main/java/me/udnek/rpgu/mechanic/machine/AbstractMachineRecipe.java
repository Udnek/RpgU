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

    protected final List<CustomSingleRecipeChoice> stuffs;
    protected final CustomRecipeChoice fuel;
    protected ItemStack result;
    protected final NamespacedKey id;
    protected final int craftDuration = 20 * 20;

    public AbstractMachineRecipe(@NotNull NamespacedKey key, @NotNull List<CustomSingleRecipeChoice> stuffs, @NotNull CustomRecipeChoice fuel, @NotNull ItemStack result) {
        this.stuffs = new ArrayList<>(stuffs);
        this.fuel = fuel;
        this.result = result.clone();
        this.id = key;
    }

    protected boolean isMatchesAllRequiredInputs(@NotNull List<CustomSingleRecipeChoice> requiredInputs, @NotNull List<ItemStack> inputs) {
        List<CustomSingleRecipeChoice> inputsLeft = new ArrayList<>(requiredInputs);
        CustomSingleRecipeChoice toRemove = null;
        for (ItemStack itemStack : inputs) {
            if (inputsLeft.isEmpty()) return false;
            for (CustomSingleRecipeChoice choice : inputsLeft) {
                if (choice.test(itemStack)) {
                    toRemove = choice;
                    break;
                }
            }
            if (toRemove == null) return false;
            inputsLeft.remove(toRemove);
            toRemove = null;
        }
        return inputsLeft.isEmpty();
    }

    public @NotNull CustomRecipeChoice getFuel() {return fuel;}
    public @NotNull List<CustomSingleRecipeChoice> getStuff() {return new ArrayList<>(stuffs);}
    public int getCraftDuration() {return craftDuration;}

    @Override
    public void replaceItem(@NotNull ItemStack oldItem, @NotNull ItemStack newItem) {
        for (CustomSingleRecipeChoice stuff : stuffs) {
            stuff.replaceItem(oldItem, newItem);
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
        for (CustomRecipeChoice input : stuffs) {
            if (input.test(itemStack)) return true;
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
