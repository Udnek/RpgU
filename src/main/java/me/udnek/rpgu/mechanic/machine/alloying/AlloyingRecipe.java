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

    protected final CustomRecipeChoice addition;
    protected final boolean keepEnchantments;

    public AlloyingRecipe(@NotNull NamespacedKey key, @NotNull List<CustomSingleRecipeChoice> alloys, @NotNull CustomRecipeChoice fuel, @NotNull CustomRecipeChoice addition, @NotNull ItemStack result, boolean keepEnchantments){
        super(key, alloys, fuel, result);
        Preconditions.checkArgument(alloys.size() <= 6, "Alloys must be <= 6!");
        for (CustomRecipeChoice alloyInput : alloys) {
            Preconditions.checkArgument(alloyInput != null, "AlloyInput can not be null!");
        }
        this.addition = addition;
        this.keepEnchantments = keepEnchantments;
    }
    public AlloyingRecipe(@NotNull NamespacedKey key, @NotNull List<CustomSingleRecipeChoice> alloys, @NotNull CustomRecipeChoice fuel, @NotNull CustomRecipeChoice addition, @NotNull ItemStack result){
        this(key, alloys, fuel, addition, result, true);
    }

    public @NotNull CustomRecipeChoice getAddition() {return addition;}
    public boolean isKeepEnchantments() {return keepEnchantments;}

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
    public void replaceItem(@NonNull ItemStack oldItem, @NonNull ItemStack newItem) {
        super.replaceItem(oldItem, newItem);
        addition.replaceItem(oldItem, newItem);
    }

    @Override
    public boolean isIngredient(@NonNull ItemStack itemStack) {
        return super.isIngredient(itemStack) || addition.test(itemStack);
    }

    @Override
    public @NotNull CustomRecipeType<?> getType() {
        return RecipeTypes.ALLOYING;
    }
}
