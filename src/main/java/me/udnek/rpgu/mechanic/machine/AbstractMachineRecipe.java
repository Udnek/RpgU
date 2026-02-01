package me.udnek.rpgu.mechanic.machine;

import me.udnek.coreu.custom.item.ItemUtils;
import me.udnek.coreu.custom.recipe.CustomRecipe;
import me.udnek.coreu.custom.recipe.choice.CustomRecipeChoice;
import me.udnek.coreu.custom.recipe.choice.CustomSingleRecipeChoice;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public abstract class AbstractMachineRecipe implements CustomRecipe {

    protected final List<CustomSingleRecipeChoice> stuff;
    protected final CustomRecipeChoice fuel;
    protected ItemStack result;
    protected final NamespacedKey id;
    protected final int craftDuration = 20 * 20;

    public AbstractMachineRecipe(@NotNull NamespacedKey key, @NotNull List<CustomSingleRecipeChoice> stuffs, @NotNull CustomRecipeChoice fuel, @NotNull ItemStack result) {
        this.stuff = new ArrayList<>(stuffs);
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

    public @NotNull List<CustomSingleRecipeChoice> getStuff() {return new ArrayList<>(stuff);}

    public int getCraftDuration() {return craftDuration;}

    @Override
    public void replaceItem(@NotNull ItemStack oldItem, @NotNull ItemStack newItem) {
        for (CustomSingleRecipeChoice stuff : stuff) {
            stuff.replaceItem(oldItem, newItem);
        }
        fuel.replaceItem(oldItem, newItem);
        if (ItemUtils.isSameIds(result, oldItem)){
            result = newItem.clone();
        }
    }

    @Override
    public void getPossibleIngredients(@NotNull Consumer<CustomRecipeChoice> consumer) {
        for (CustomSingleRecipeChoice choice : stuff) {
            consumer.accept(choice);
        }
        consumer.accept(fuel);
    }

    @Override
    public void getPossibleResults(@NotNull Consumer<ItemStack> consumer) {
        consumer.accept(result.clone());
    }

    @Override
    public @NotNull NamespacedKey getKey() {
        return id;
    }
}
