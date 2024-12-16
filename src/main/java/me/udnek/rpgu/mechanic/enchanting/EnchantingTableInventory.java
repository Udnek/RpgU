package me.udnek.rpgu.mechanic.enchanting;

import io.papermc.paper.datacomponent.DataComponentTypes;
import io.papermc.paper.datacomponent.item.ItemEnchantments;
import me.udnek.itemscoreu.custominventory.ConstructableCustomInventory;
import me.udnek.itemscoreu.custominventory.SmartIntractableCustomInventory;
import me.udnek.itemscoreu.customitem.CustomItem;
import me.udnek.itemscoreu.util.ComponentU;
import me.udnek.itemscoreu.util.ItemUtils;
import me.udnek.rpgu.item.Items;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class EnchantingTableInventory extends ConstructableCustomInventory implements SmartIntractableCustomInventory {

    public static final CustomItem FILLER = Items.TECHNICAL_INVENTORY_FILLER;

    private static final int LAPIS_SLOT = 9*3;
    private static final int BOOK_SLOT = 9*3+1;

    private static final int[] PASSION_SLOTS = new int[]{
            0,1,
            9,10
    };

    private static final int[] INPUT_SLOTS = new int[]{
            0,1,
            9,10,
            LAPIS_SLOT, BOOK_SLOT
    };

    private static final int[] ENCHANTED_BOOKS_SLOTS = new int[]{
            3,  4,  5,  6,  7,  8,
            12, 13, 14, 15, 16, 17,
            21, 22, 23, 24, 25, 26,
            30, 31, 32, 33, 34, 35,
            39, 40, 41, 42, 43, 44,
    };

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
            if (itemStack == null) continue;
            passionItems.add(itemStack);
        }
        return passionItems;
    }

    public void clearEnchantedBooks(){
        for (int slot : ENCHANTED_BOOKS_SLOTS) {
            inventory.setItem(slot, FILLER.getItem());
        }
    }
    
    public void showEnchantedBooks(@NotNull Map<Enchantment, Integer> map, int maxLevel){
        int index = -1;
        for (Map.Entry<Enchantment, Integer> entry : map.entrySet()) {
            if (entry.getValue() > maxLevel) continue;

            index ++;
            if (index > map.size()-1) return;

            ItemStack book = new ItemStack(Material.ENCHANTED_BOOK);
            book.setData(DataComponentTypes.STORED_ENCHANTMENTS, ItemEnchantments.itemEnchantments().add(
                    entry.getKey(),
                    Math.clamp(entry.getValue(), 1, maxLevel))
                    .build());

            inventory.setItem(ENCHANTED_BOOKS_SLOTS[index], book);
        }
    }

    public void recalculate(){
        Bukkit.getLogger().info("CALLED RECALCULATE");
        if (!(hasLapis() && hasBook())){
            clearEnchantedBooks();
            return;
        }
        List<EnchantmentsContainer> containers = new ArrayList<>();
        for (@NotNull ItemStack passionItem : getPassionItems()) {
            containers.add(EnchantingPassion.instance().get(passionItem));
        }
        clearEnchantedBooks();
        showEnchantedBooks(EnchantmentsContainer.mix(containers), inventory.getItem(LAPIS_SLOT).getAmount());
    }

    public void iterateTroughAllInputSlots(@NotNull Consumer<Integer> consumer){
        Arrays.stream(INPUT_SLOTS).forEach(consumer::accept);
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

        ItemStack book = inventory.getItem(event.getSlot());
        if (book != null && event.getClickedInventory() == event.getInventory()){
            if (ItemUtils.isVanillaMaterial(book, Material.ENCHANTED_BOOK)) {
                if (Arrays.stream(ENCHANTED_BOOKS_SLOTS).anyMatch(slot -> slot == event.getSlot())) {
                    takeItem(LAPIS_SLOT, new ArrayList<>(book.getData(DataComponentTypes.STORED_ENCHANTMENTS).enchantments().values()).getFirst());
                    takeItem(BOOK_SLOT, 1);
                }
            }
        }
    }

    @Override
    public boolean canPlaceItem(@Nullable ItemStack itemStack, int i) {
        return Arrays.stream(INPUT_SLOTS).anyMatch(slot -> i == slot);
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

















