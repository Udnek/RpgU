package me.udnek.rpgu.mechanic.machine.crusher;

import com.google.common.base.Preconditions;
import me.udnek.coreu.custom.item.ItemUtils;
import me.udnek.coreu.custom.recipe.CustomRecipeType;
import me.udnek.coreu.custom.recipe.choice.CustomSingleRecipeChoice;
import me.udnek.rpgu.mechanic.machine.AbstractMachineRecipe;
import me.udnek.rpgu.mechanic.machine.RecipeTypes;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Range;
import org.jspecify.annotations.NullMarked;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Consumer;

@NullMarked
public class CrusherRecipe extends AbstractMachineRecipe {

    protected final List<ResultEntry> results;

    public CrusherRecipe(NamespacedKey key, CustomSingleRecipeChoice stuff, List<ResultEntry> results) {
        super(key, List.of(stuff), new CustomSingleRecipeChoice(Material.AIR));
        Preconditions.checkArgument(results.size() <= 9, "Alloys must be <= 8!");
        this.results = results;
    }

    public boolean test(List<ItemStack> stuffsInput) {
        return isMatchesAllRequiredInputs(stuff, stuffsInput);
    }

    public List<ItemStack> generateResult() {
        List<ItemStack> result = new ArrayList<>();
        for (ResultEntry resultEntry : results) {
            ItemStack itemStack = resultEntry.generate();
            if (itemStack == null) continue;
            result.add(itemStack);
        }
        return result;
    }

    @Override
    public void getPossibleResults(Consumer<ItemStack> consumer) {
        results.forEach(resultEntry ->  consumer.accept(resultEntry.getItem()));
    }

    @Override
    public void replaceItem(ItemStack oldItem, ItemStack newItem) {
        super.replaceItem(oldItem, newItem);
        for (ResultEntry resultEntry : results) {
            if (ItemUtils.isSameIds(resultEntry.getItem(), oldItem)) {
                resultEntry.setItem(newItem.clone());
            }
        }
    }

    @Override
    public int getCraftDuration() {
        return 10*20;
    }

    @Override
    public CustomRecipeType<?> getType() {
        return RecipeTypes.CRUSHER;
    }

    public static class ResultEntry {
        private ItemStack result;
        public final double chance;

        public ResultEntry(ItemStack result, @Range(from = 0, to = 1) double chance) {
            this.result = result;
            this.chance = chance;
        }

        public ItemStack getItem() {
            return result.clone();
        }
        
        public void setItem(ItemStack result) {
            this.result = result.clone();
        }

        public @Nullable ItemStack generate() {
            if (ThreadLocalRandom.current().nextFloat() < chance) {
                return result.clone();
            }
            return null;
        }
    }
}
