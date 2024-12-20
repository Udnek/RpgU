package me.udnek.rpgu.mechanic.enchanting;

import io.papermc.paper.datacomponent.DataComponentTypes;
import io.papermc.paper.datacomponent.item.ItemEnchantments;
import me.udnek.itemscoreu.custominventory.ConstructableCustomInventory;
import me.udnek.itemscoreu.custominventory.SmartIntractableCustomInventory;
import me.udnek.itemscoreu.customitem.CustomItem;
import me.udnek.itemscoreu.customrecipe.RecipeManager;
import me.udnek.itemscoreu.util.ComponentU;
import me.udnek.itemscoreu.util.ItemUtils;
import me.udnek.rpgu.item.Items;
import me.udnek.rpgu.mechanic.enchanting.upgrade.EnchantingTableUpgrade;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.apache.commons.lang3.ArrayUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class EnchantingTableInventory extends ConstructableCustomInventory implements SmartIntractableCustomInventory {

    public static final int UPGRADE_RADIUS_XZ = 2;
    public static final int UPGRADE_RADIUS_Y = 3;

    public static final CustomItem FILLER = Items.TECHNICAL_INVENTORY_FILLER;

    private static final int LAPIS_SLOT = 0;
    private static final int BOOK_SLOT = 1;

    public static final int[] PASSION_SLOTS = new int[]{
            2,   3,   4,
            9+2, 9+3, 9+4
    };

    public static final int[] ALL_INPUT_SLOTS = ArrayUtils.addAll(PASSION_SLOTS, LAPIS_SLOT, BOOK_SLOT);

    private static final int[] ENCHANTED_BOOKS_SLOTS = new int[]{
            9*3+0, 9*3+1, 9*3+2, 9*3+3, 9*3+4,
            9*4+0, 9*4+1, 9*4+2, 9*4+3, 9*4+4,
    };

    public static final int[] UPGRADE_SLOTS = new int[]{
            9*0+6, 9*0+7, 9*0+8,
            9*1+6, 9*1+7, 9*1+8,
            9*2+6, 9*2+7, 9*2+8,
            9*3+6, 9*3+7, 9*3+8,
            9*4+6, 9*4+7, 9*4+8
    };

    public static final int PASSION_SLOTS_AMOUNT = PASSION_SLOTS.length;


    protected final @NotNull Location tableLocation;
    protected Set<EnchantingTableUpgrade> upgrades;

    public EnchantingTableInventory(@NotNull Location tableLocation){
        this.tableLocation = tableLocation;
        recalculateUpgrades();
    }

    public boolean hasBook(){
        ItemStack item = inventory.getItem(BOOK_SLOT);
        if (item == null) return false;
        if (CustomItem.isCustom(item)) return false;
        return item.getType() == Material.BOOK;
    }
    public boolean hasLapis(){
        ItemStack item = inventory.getItem(LAPIS_SLOT);
        if (item == null) return false;
        if (CustomItem.isCustom(item)) return false;
        return item.getType() == Material.LAPIS_LAZULI;
    }
    public @NotNull List<@NotNull ItemStack> getPassionItems(){
        List<ItemStack> passionItems = new ArrayList<>();
        for (int slot : PASSION_SLOTS) {
            ItemStack itemStack = inventory.getItem(slot);
            if (itemStack != null) passionItems.add(itemStack);
        }
        return passionItems;
    }

    public void clearEnchantedBooks(){
        for (int slot : ENCHANTED_BOOKS_SLOTS) {
            inventory.setItem(slot, FILLER.getItem());
        }
    }

    public void recalculateUpgrades(){
        this.upgrades = EnchantingTableUpgrade.REGISTRY.getAll().stream().filter(upgrade -> upgrade.test(tableLocation)).collect(Collectors.toSet());
        ArrayList<EnchantingTableUpgrade> upgradeList = new ArrayList<>(upgrades);
        for (int i = 0; i < UPGRADE_SLOTS.length; i++) {
            int slot = UPGRADE_SLOTS[i];
            if (i >= this.upgrades.size()) {
                inventory.setItem(slot, FILLER.getItem());
            } else {
                inventory.setItem(slot, upgradeList.get(i).getIcon());
            }
        }
    }


    public void recalculate(){
        Bukkit.getLogger().info("CALLED RECALCULATE");
        if (!(hasLapis() && hasBook())){
            clearEnchantedBooks();
            return;
        }
        List<EnchantingRecipe> recipes = RecipeManager.getInstance().getByType(EnchantingRecipeType.INSTANCE);
        EnchantingRecipe enchantingRecipe = null;
        for (EnchantingRecipe recipe : recipes) {
            if (recipe.test(getPassionItems(), upgrades)) {
                enchantingRecipe = recipe;
                break;
            }
        }
        if (enchantingRecipe == null) clearEnchantedBooks();
        else proceedRecipe(enchantingRecipe);
    }

    public void proceedRecipe(@NotNull EnchantingRecipe recipe){
        Enchantment enchantment = recipe.getEnchantment();
        int startLevel =  Math.max(enchantment.getMaxLevel() - ENCHANTED_BOOKS_SLOTS.length, 0)+1;
        for (int i = 0; i < Math.min(ENCHANTED_BOOKS_SLOTS.length, enchantment.getMaxLevel()); i++) {
            ItemStack book = new ItemStack(Material.ENCHANTED_BOOK);
            book.setData(DataComponentTypes.STORED_ENCHANTMENTS, ItemEnchantments.itemEnchantments(
                    Map.of(enchantment, startLevel+i), true)
            );
            inventory.setItem(ENCHANTED_BOOKS_SLOTS[i], book);
        }
    }

    // BASES

    public void iterateTroughAllInputSlots(@NotNull Consumer<Integer> consumer){
        Arrays.stream(ALL_INPUT_SLOTS).forEach(consumer::accept);
    }

    @Override
    public void afterPlayerClicksItem(InventoryClickEvent event) {
        recalculate();
    }

    @Override
    public void onPlayerClicksItem(InventoryClickEvent event) {
        SmartIntractableCustomInventory.super.onPlayerClicksItem(event);

        // TODO: 8/26/2024 REMOVE WHEN FIXED IN ITEMSCOREU
        if (event.getClickedInventory() != this.getInventory() && event.getAction() == InventoryAction.MOVE_TO_OTHER_INVENTORY){
            event.setCancelled(false);
        }

        if (event.isCancelled()) return;

        if (event.getSlot() < 0) return;
        ItemStack book = inventory.getItem(event.getSlot());
        if (book != null && event.getClickedInventory() == event.getInventory()){
            if (ItemUtils.isVanillaMaterial(book, Material.ENCHANTED_BOOK)) {
                if (Arrays.stream(ENCHANTED_BOOKS_SLOTS).anyMatch(slot -> slot == event.getSlot())) {
                    takeItem(LAPIS_SLOT, new ArrayList<>(book.getData(DataComponentTypes.STORED_ENCHANTMENTS).enchantments().values()).getFirst());
                    takeItem(BOOK_SLOT, 1);
                    for (int slot : ALL_INPUT_SLOTS) {
                        takeItem(slot, 1);
                    }
                }
            }
        }
    }

    @Override
    public boolean canPlaceItem(@Nullable ItemStack itemStack, int i) {
        return Arrays.stream(ALL_INPUT_SLOTS).anyMatch(slot -> i == slot);
    }

    @Override
    public boolean canTakeItem(@Nullable ItemStack itemStack, int i) {
        if (FILLER.isThisItem(itemStack)) return false;
        return canPlaceItem(itemStack, i) || Arrays.stream(ENCHANTED_BOOKS_SLOTS).anyMatch(slot -> i == slot);
    }

    public void generateInventory(int size, @NotNull Component title) {
        inventory = Bukkit.createInventory(this, size, title);
        for (int i = 0; i < size; i++) {
            inventory.setItem(i, FILLER.getItem());
        }
        iterateTroughAllInputSlots(integer -> inventory.setItem(integer, null));

    }

    @Override
    public void onPlayerClosesInventory(InventoryCloseEvent event) {
        PlayerInventory playerInventory = event.getPlayer().getInventory();
        Inventory eventInventory = event.getInventory();

        iterateTroughAllInputSlots(slot -> {
            ItemStack itemStack = eventInventory.getItem(slot);
            if (itemStack != null){playerInventory.addItem(itemStack);}
        });
    }

    @Override
    public int getInventorySize() {return 9*6;}

    @Override
    public Component getDisplayName() {
        return ComponentU.textWithNoSpace(
                -8,
                Component.text("0").font(Key.key("rpgu:enchanting")).color(NamedTextColor.WHITE),
                170)
                .append(Component.translatable("container.enchant").color(NamedTextColor.BLACK));
    }
}

















