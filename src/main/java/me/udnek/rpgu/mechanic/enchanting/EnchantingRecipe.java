package me.udnek.rpgu.mechanic.enchanting;

import com.google.common.base.Preconditions;
import io.papermc.paper.datacomponent.DataComponentTypes;
import io.papermc.paper.datacomponent.item.ItemEnchantments;
import me.udnek.coreu.custom.item.ItemUtils;
import me.udnek.coreu.custom.recipe.CustomRecipe;
import me.udnek.coreu.custom.recipe.choice.CustomSingleRecipeChoice;
import me.udnek.jeiu.visualizer.abstraction.Visualizable;
import me.udnek.jeiu.visualizer.abstraction.Visualizer;
import me.udnek.rpgu.mechanic.enchanting.upgrade.EnchantingTableUpgrade;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class EnchantingRecipe implements CustomRecipe<EnchantingRecipeType>, Visualizable {

    protected final @NotNull NamespacedKey key;
    protected final @NotNull Enchantment enchantment;
    protected final @NotNull List<CustomSingleRecipeChoice> passion;
    protected final @NotNull Set<EnchantingTableUpgrade> upgrades;

    @Override
    public @NotNull Collection<ItemStack> getResults() {
        return Collections.singleton(getResult());
    }

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
    public boolean isResult(@NotNull ItemStack itemStack) {
        ItemEnchantments data = itemStack.getData(DataComponentTypes.STORED_ENCHANTMENTS);
        if (data == null) return false;
        return data.enchantments().containsKey(enchantment);
    }

    @Override
    public boolean isIngredient(@NotNull ItemStack itemStack) {
        for (CustomSingleRecipeChoice choice : passion) if (choice.test(itemStack)) return true;
        return ItemUtils.isVanillaMaterial(itemStack, Material.LAPIS_LAZULI) || ItemUtils.isVanillaMaterial(itemStack, Material.BOOK);
    }
    

    @Override
    public void replaceItem(@NotNull ItemStack old, @NotNull ItemStack newI) {
        for (CustomSingleRecipeChoice choice : passion) {choice.replaceItem(old, newI);}
        
    }

    @Override
    public @NotNull ItemStack getResult() {
        ItemStack itemStack = new ItemStack(Material.ENCHANTED_BOOK);
        itemStack.setData(DataComponentTypes.STORED_ENCHANTMENTS, ItemEnchantments.itemEnchantments(Map.of(enchantment, 1), true));
        return itemStack;
    }

    @Override
    public @NotNull NamespacedKey getKey() {
        return key;
    }

    @Override
    public @NotNull EnchantingRecipeType getType() {
        return EnchantingRecipeType.INSTANCE;
    }

    @Override
    public @NotNull Visualizer getVisualizer() {
        return new EnchantingRecipeVisualizer(this);
    }
}
