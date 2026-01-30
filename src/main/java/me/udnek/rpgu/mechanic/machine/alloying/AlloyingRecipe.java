package me.udnek.rpgu.mechanic.machine.alloying;

import com.google.common.base.Preconditions;
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

public class AlloyingRecipe extends AbstractMachineRecipe {

    protected final List<CustomSingleRecipeChoice> alloys;
    protected final CustomRecipeChoice addition;
    protected final boolean keepEnchantments;

    public AlloyingRecipe(@NotNull NamespacedKey key, @NotNull List<CustomSingleRecipeChoice> stuffs, @NotNull CustomRecipeChoice fuel, @NotNull CustomRecipeChoice addition, @NotNull ItemStack result, boolean keepEnchantments){
        super(key, stuffs, fuel, result);
        Preconditions.checkArgument(this.stuffs.size() <= 6, "Stuffs must be <= 6!");
        for (CustomRecipeChoice stuffsInput : this.stuffs) {
            Preconditions.checkArgument(stuffsInput != null, "StuffsInput can not be null!");
        }
        this.alloys = new ArrayList<>(stuffs);//TODO поменять на норм
        this.addition = addition;
        this.keepEnchantments = keepEnchantments;
    }
    public AlloyingRecipe(@NotNull NamespacedKey key, @NotNull List<CustomSingleRecipeChoice> stuffs, @NotNull CustomRecipeChoice fuel, @NotNull CustomRecipeChoice addition, @NotNull ItemStack result){
        this(key, stuffs, fuel, addition, result, true);
    }

    public @NotNull List<CustomSingleRecipeChoice> getAlloys() {return new ArrayList<>(alloys);}
    public @NotNull CustomRecipeChoice getAddition() {return addition;}
    public boolean isKeepEnchantments() {return keepEnchantments;}

    public boolean test(@NotNull List<ItemStack> stuffsInput, @NotNull List<ItemStack> alloysInput, @NotNull ItemStack fuelInput, @NotNull ItemStack additionInput) {
        if (!fuel.test(fuelInput)) return false;
        if (!addition.test(additionInput)) return false;
        if (!isMatchesAllRequiredInputs(stuffs, stuffsInput)) return false;
        return isMatchesAllRequiredInputs(alloys, alloysInput);
    }


    @Override
    public void replaceItem(@NonNull ItemStack oldItem, @NonNull ItemStack newItem) {
        super.replaceItem(oldItem, newItem);
        for (CustomSingleRecipeChoice alloy : alloys) {
            alloy.replaceItem(oldItem, newItem);
        }
        addition.replaceItem(oldItem, newItem);
    }

    @Override
    public boolean isIngredient(@NonNull ItemStack itemStack) {
        for (CustomRecipeChoice alloy : alloys) {
            if (alloy.test(itemStack)) return true;
        }
        return super.isIngredient(itemStack) || addition.test(itemStack);
    }

    @Override
    public @NotNull CustomRecipeType<?> getType() {
        return RecipeTypes.ALLOYING;
    }
}
