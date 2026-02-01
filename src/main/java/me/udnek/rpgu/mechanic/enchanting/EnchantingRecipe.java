package me.udnek.rpgu.mechanic.enchanting;

import com.google.common.base.Preconditions;
import io.papermc.paper.datacomponent.DataComponentTypes;
import io.papermc.paper.datacomponent.item.ItemEnchantments;
import me.udnek.coreu.custom.item.ItemUtils;
import me.udnek.coreu.custom.recipe.CustomRecipe;
import me.udnek.coreu.custom.recipe.CustomRecipeType;
import me.udnek.coreu.custom.recipe.choice.CustomRecipeChoice;
import me.udnek.coreu.custom.recipe.choice.CustomSingleRecipeChoice;
import me.udnek.rpgu.mechanic.enchanting.upgrade.EnchantingTableUpgrade;
import me.udnek.rpgu.mechanic.machine.RecipeTypes;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.Consumer;

public class EnchantingRecipe implements CustomRecipe {

    protected static final CustomRecipeChoice LAPIS_CHOICE = new CustomSingleRecipeChoice(Material.LAPIS_LAZULI);
    protected static final CustomRecipeChoice BOOK_CHOICE = new CustomSingleRecipeChoice(Material.BOOK);

    protected final @NotNull NamespacedKey key;
    protected final @NotNull Enchantment enchantment;
    protected final @NotNull List<CustomSingleRecipeChoice> passion;
    protected final @NotNull Set<EnchantingTableUpgrade> upgrades;

    public EnchantingRecipe(@NotNull NamespacedKey key, @NotNull Enchantment enchantment, @NotNull List<CustomSingleRecipeChoice> passions, @NotNull Set<EnchantingTableUpgrade> upgrades){
        Preconditions.checkArgument(passions.size() <= EnchantingTableInventory.PASSION_SLOTS_AMOUNT,
                "Input must be <= " + EnchantingTableInventory.PASSION_SLOTS_AMOUNT);
        this.key = key;
        this.enchantment = enchantment;
        this.passion = passions;
        this.upgrades = upgrades;
    }

    public EnchantingRecipe(@NotNull NamespacedKey key, @NotNull Enchantment enchantment, @NotNull List<CustomSingleRecipeChoice> passion){
        this(key, enchantment, passion, Set.of());
    }

    public @NotNull List<CustomSingleRecipeChoice> getPassions() {
        return new ArrayList<>(passion);
    }

    public @NotNull Set<EnchantingTableUpgrade> getUpgrades() {
        return new HashSet<>(upgrades);
    }

    public @NotNull Enchantment getEnchantment() {return enchantment;}

    public boolean test(@NotNull List<ItemStack> itemStacks, @NotNull Set<EnchantingTableUpgrade> ups){
        if (!ups.containsAll(upgrades)) return false;
        List<CustomSingleRecipeChoice> left = new ArrayList<>(passion);
        CustomSingleRecipeChoice toRemove = null;
        for (ItemStack itemStack : itemStacks) {
            if (left.isEmpty()) return false;
            for (CustomSingleRecipeChoice choice : left) {
                if (choice.test(itemStack)) {
                    toRemove = choice;
                    break;
                }
            }
            if (toRemove == null) return false;
            left.remove(toRemove);
            toRemove = null;
        }
        return left.isEmpty();
    }

    @Override
    public void getPossibleResults(@NotNull Consumer<ItemStack> consumer) {
        consumer.accept(getResult());
    }

    @Override
    public void getPossibleIngredients(@NotNull Consumer<CustomRecipeChoice> consumer) {
        for (CustomSingleRecipeChoice choice : passion) {
            consumer.accept(choice);
        }
        consumer.accept(LAPIS_CHOICE);
        consumer.accept(BOOK_CHOICE);
    }

    @Override
    public void replaceItem(@NotNull ItemStack old, @NotNull ItemStack newI) {
        for (CustomSingleRecipeChoice choice : passion) {choice.replaceItem(old, newI);}
    }

    @NotNull
    @Override
    public ItemStack getResult() {
        ItemStack book = new ItemStack(Material.ENCHANTED_BOOK);
        book.setData(DataComponentTypes.STORED_ENCHANTMENTS, ItemEnchantments.itemEnchantments(Map.of(enchantment, 1)));
        return book;
    }

    @Override
    public @NotNull NamespacedKey getKey() {
        return key;
    }


    @Override
    public @NotNull CustomRecipeType<?> getType() {
        return RecipeTypes.ENCHANTING;
    }
}
