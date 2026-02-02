package me.udnek.rpgu.mechanic.machine.alloying;

import com.google.common.base.Preconditions;
import me.udnek.coreu.custom.item.ItemUtils;
import me.udnek.coreu.custom.recipe.CustomRecipeType;
import me.udnek.coreu.custom.recipe.choice.CustomRecipeChoice;
import me.udnek.coreu.custom.recipe.choice.CustomSingleRecipeChoice;
import me.udnek.rpgu.mechanic.machine.AbstractMachineRecipe;
import me.udnek.rpgu.mechanic.machine.RecipeTypes;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class AlloyingRecipe extends AbstractMachineRecipe {

    protected final List<CustomSingleRecipeChoice> alloys;
    protected ItemStack result;
    protected final CustomRecipeChoice addition;
    protected final boolean keepEnchantments;

    public AlloyingRecipe(@NotNull NamespacedKey key, @NotNull List<CustomSingleRecipeChoice> stuffs, @NotNull CustomRecipeChoice fuel, @NotNull List<CustomSingleRecipeChoice> alloys,  @NotNull CustomRecipeChoice addition, @NotNull ItemStack result, boolean keepEnchantments){
        super(key, stuffs, fuel);
        Preconditions.checkArgument(stuffs.size() <= 8, "Stuffs must be <= 8!");
        for (CustomRecipeChoice stuffsInput : stuffs) {
            Preconditions.checkArgument(stuffsInput != null, "StuffsInput can not be null!");
        }
        Preconditions.checkArgument(alloys.size() <= 8, "Alloys must be <= 8!");
        for (CustomRecipeChoice stuffsInput : alloys) {
            Preconditions.checkArgument(stuffsInput != null, "AlloysInput can not be null!");
        }
        this.result = result.clone();
        this.alloys = new ArrayList<>(alloys);
        this.addition = addition;
        this.keepEnchantments = keepEnchantments;
    }
    public AlloyingRecipe(@NotNull NamespacedKey key, @NotNull List<CustomSingleRecipeChoice> stuffs, @NotNull CustomRecipeChoice fuel, @NotNull List<CustomSingleRecipeChoice> alloys, @NotNull CustomRecipeChoice addition, @NotNull ItemStack result){
        this(key, stuffs, fuel, alloys, addition, result, true);
    }

    public @NotNull List<CustomSingleRecipeChoice> getAlloys() {return new ArrayList<>(alloys);}
    public @NotNull CustomRecipeChoice getAddition() {return addition;}
    public boolean isKeepEnchantments() {return keepEnchantments;}

    public boolean test(@NotNull List<ItemStack> stuffsInput, @NotNull List<ItemStack> alloysInput, @NotNull ItemStack fuelInput, @NotNull ItemStack additionInput) {
        if (!fuel.test(fuelInput)) return false;
        if (!addition.test(additionInput)) return false;
        if (!isMatchesAllRequiredInputs(stuff, stuffsInput)) return false;
        return isMatchesAllRequiredInputs(alloys, alloysInput);
    }

    @Override
    public void replaceItem(@NonNull ItemStack oldItem, @NonNull ItemStack newItem) {
        super.replaceItem(oldItem, newItem);
        if (ItemUtils.isSameIds(result, oldItem)){
            result = newItem.clone();
        }
        for (CustomSingleRecipeChoice alloy : alloys) {
            alloy.replaceItem(oldItem, newItem);
        }
        addition.replaceItem(oldItem, newItem);
    }

    @Override
    public void getPossibleResults(@NotNull Consumer<ItemStack> consumer) {
        consumer.accept(result.clone());
    }

    @Override
    public void getPossibleIngredients(@NotNull Consumer<CustomRecipeChoice> consumer) {
        super.getPossibleIngredients(consumer);
        for (CustomRecipeChoice alloy : alloys) {
            consumer.accept(alloy);
        }
    }

    public @NotNull ItemStack getNewResult() {
        return result;
    }

    @Override
    public @NotNull CustomRecipeType<?> getType() {
        return RecipeTypes.ALLOYING;
    }
}
