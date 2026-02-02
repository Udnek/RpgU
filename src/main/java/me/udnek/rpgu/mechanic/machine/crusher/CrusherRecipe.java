package me.udnek.rpgu.mechanic.machine.crusher;

import com.google.common.base.Preconditions;
import io.papermc.paper.datacomponent.DataComponentTypes;
import io.papermc.paper.datacomponent.item.ItemLore;
import me.udnek.coreu.custom.item.ItemUtils;
import me.udnek.coreu.custom.recipe.CustomRecipeType;
import me.udnek.coreu.custom.recipe.choice.CustomSingleRecipeChoice;
import me.udnek.coreu.util.Utils;
import me.udnek.rpgu.mechanic.machine.AbstractMachineRecipe;
import me.udnek.rpgu.mechanic.machine.RecipeTypes;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Range;
import org.jspecify.annotations.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Consumer;

public class CrusherRecipe extends AbstractMachineRecipe {

    protected final List<ResultEntry> results;

    public CrusherRecipe(@NonNull NamespacedKey key, @NonNull CustomSingleRecipeChoice stuff, @NonNull List<ResultEntry> results) {
        super(key, List.of(stuff), new CustomSingleRecipeChoice(Material.AIR));
        Preconditions.checkArgument(results.size() <= 9, "Alloys must be <= 8!");
        for (ResultEntry resultEntry : results) {
            Preconditions.checkArgument(resultEntry != null, "AlloysInput can not be null!");
        }
        this.results = results;
    }

    public boolean test(@NotNull List<ItemStack> stuffsInput) {
        return isMatchesAllRequiredInputs(stuff, stuffsInput);
    }

    public @NotNull List<ItemStack> generateResult() {
        List<ItemStack> result = new ArrayList<>();
        for (ResultEntry resultEntry : results) {
            ItemStack itemStack = resultEntry.generateResult();
            if (itemStack == null) continue;
            result.add(itemStack);
        }
        return result;
    }

    public List<ItemStack> getResultsWithChance() {
        List<ItemStack> newResults = new ArrayList<>();
        for (ResultEntry resultEntry : results) {
            ItemStack result = resultEntry.getResult().clone();
            List<Component> lore = new ArrayList<>(result.getDataOrDefault(DataComponentTypes.LORE, ItemLore.lore().build()).lines());
            lore.add(Component.translatable("crusher.rpgu.probability",
                    List.of(Component.text(Utils.roundToTwoDigits(resultEntry.chance * 100)))).color(NamedTextColor.YELLOW));
            result.setData(DataComponentTypes.LORE, ItemLore.lore(lore));
            newResults.add(result);
        }
        return newResults;
    }

    @Override
    public void getPossibleResults(@NotNull Consumer<ItemStack> consumer) {
        results.forEach(resultEntry ->  consumer.accept(resultEntry.getResult()));
    }

    @Override
    public void replaceItem(@NonNull ItemStack oldItem, @NonNull ItemStack newItem) {
        super.replaceItem(oldItem, newItem);
        for (ResultEntry resultEntry : results) {
            if (ItemUtils.isSameIds(resultEntry.getResult(), oldItem)) {
                resultEntry.setResult(newItem.clone());
            }
        }

    }

    @Override
    public @NotNull CustomRecipeType<?> getType() {
        return RecipeTypes.CRUSHER;
    }

    public static class ResultEntry {
        private ItemStack result;
        private final double chance;

        public ResultEntry(@NonNull ItemStack result, @Range(from = 0, to = 1) double chance) {
            this.result = result;
            this.chance = chance;
        }

        public ItemStack getResult() {
            return result.clone();
        }
        
        public void setResult(@NotNull ItemStack result) {
            this.result = result.clone();
        }

        public @Nullable ItemStack generateResult() {
            if (ThreadLocalRandom.current().nextFloat() < chance) {
                return result.clone();
            }
            return null;
        }
    }
}
