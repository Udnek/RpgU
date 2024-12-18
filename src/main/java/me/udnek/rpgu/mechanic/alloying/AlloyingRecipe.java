package me.udnek.rpgu.mechanic.alloying;

import com.google.common.base.Preconditions;
import me.udnek.itemscoreu.customrecipe.CustomRecipe;
import me.udnek.itemscoreu.customrecipe.choice.CustomRecipeChoice;
import me.udnek.itemscoreu.customrecipe.choice.CustomSingleRecipeChoice;
import me.udnek.itemscoreu.util.ItemUtils;
import me.udnek.jeiu.visualizer.abstraction.Visualizable;
import me.udnek.jeiu.visualizer.abstraction.Visualizer;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class AlloyingRecipe implements CustomRecipe<AlloyingRecipeType>, Visualizable {

    protected final List<CustomSingleRecipeChoice> alloys;
    protected final CustomRecipeChoice addition;
    protected final CustomRecipeChoice fuel;
    protected ItemStack result;
    protected final NamespacedKey id;
    protected final boolean keepEnchantments;
    public AlloyingRecipe(@NotNull NamespacedKey key, @NotNull List<CustomSingleRecipeChoice> alloys, @NotNull CustomRecipeChoice fuel, @NotNull CustomRecipeChoice addition, @NotNull ItemStack result, boolean keepEnchantments){
        Preconditions.checkArgument(key != null, "Key can not be null!");
        Preconditions.checkArgument(fuel != null, "Fuel can not be null!");
        Preconditions.checkArgument(addition != null, "TargetInput can not be null!");
        Preconditions.checkArgument(result != null, "Result can not be null!");
        Preconditions.checkArgument(alloys.size() <= 6, "Alloys must be <= 6!");
        for (CustomRecipeChoice alloyInput : alloys) {
            Preconditions.checkArgument(alloyInput != null, "AlloyInput can not be null!");
        }
        this.alloys = new ArrayList<>(alloys);
        this.fuel = fuel;
        this.addition = addition;
        this.result = result.clone();
        this.id = key;
        this.keepEnchantments = keepEnchantments;
    }
    public AlloyingRecipe(@NotNull NamespacedKey key, @NotNull List<CustomSingleRecipeChoice> alloys, @NotNull CustomRecipeChoice fuel, @NotNull CustomRecipeChoice addition, @NotNull ItemStack result){
        this(key, alloys, fuel, addition, result, true);
    }

    public @NotNull CustomRecipeChoice getFuel() {return fuel;}
    public @NotNull CustomRecipeChoice getAddition() {return addition;}
    public @NotNull List<CustomSingleRecipeChoice> getAlloys() {return new ArrayList<>(alloys);}
    public boolean isKeepEnchantments() {return keepEnchantments;}

    @Override
    public @NotNull Visualizer getVisualizer() {
        return new AlloyingRecipeVisualizer(this);
    }
    public boolean test(@NotNull List<ItemStack> alloysInput, @NotNull ItemStack fuelInput, @NotNull ItemStack additionInput) {
        if (!fuel.test(fuelInput)) return false;
        if (!addition.test(additionInput)) return false;
        List<CustomSingleRecipeChoice> alloysLeft = new ArrayList<>(alloys);
        CustomSingleRecipeChoice toRemove = null;
        for (ItemStack itemStack : alloysInput) {
            if (alloysLeft.isEmpty()) return false;
            for (CustomSingleRecipeChoice choice : alloysLeft) {
                if (choice.test(itemStack)) {
                    toRemove = choice;
                    break;
                }
            }
            if (toRemove == null) return false;
            alloysLeft.remove(toRemove);
            toRemove = null;
        }
        return alloysLeft.isEmpty();

    }
    @Override
    public void replaceItem(@NotNull ItemStack oldItem, @NotNull ItemStack newItem) {
        for (CustomSingleRecipeChoice alloy : alloys) {
            alloy.replaceItem(oldItem, newItem);
        }
        fuel.replaceItem(oldItem, newItem);
        addition.replaceItem(oldItem, newItem);
        if (isResult(oldItem)){
            result = newItem.clone();
        }
    }
    @Override
    public @NotNull Collection<ItemStack> getResults() {
        return Collections.singleton(result);
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
        return addition.test(itemStack) || fuel.test(itemStack);
    }
    @Override
    public @NotNull AlloyingRecipeType getType() {
        return AlloyingRecipeType.getInstance();
    }
    @Override
    public @NotNull ItemStack getResult() {
        return result;
    }
    @Override
    public @NotNull NamespacedKey getKey() {
        return id;
    }
}
