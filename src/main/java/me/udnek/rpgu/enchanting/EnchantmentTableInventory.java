package me.udnek.rpgu.enchanting;

import me.udnek.itemscoreu.custominventory.CustomInventory;
import me.udnek.itemscoreu.utils.CustomItemUtils;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class EnchantmentTableInventory extends CustomInventory {

    private final int LAPIS_SLOT = 9*3;
    private final int BOOK_SLOT = 9*3+1;

    private final int[] PASSION_ITEMS_SLOTS = new int[]{
            0,1,
            9,10
    };

    private final int[] PLACEABLE_SLOTS = new int[]{
            0,1,
            9,10,
            LAPIS_SLOT, BOOK_SLOT
    };

    private final int[] ENCHANTED_BOOKS_SLOTS = new int[]{
            3,  4,  5,  6,  7,  8,
            12, 13, 14, 15, 16, 17,
            21, 22, 23, 24, 25, 26,
            30, 31, 32, 33, 34, 35,
            39, 40, 41, 42, 43, 44,
    };

    public int getNextEmptySlot(){
        for (int slot : PLACEABLE_SLOTS) {
            if (inventory.getItem(slot) == null){
                return slot;
            }
        }
        return -1;
    }

    public boolean hasBook(){
        ItemStack itemStack = inventory.getItem(BOOK_SLOT);
        if (itemStack == null) return false;
        if (itemStack.getType() != Material.BOOK) return false;
        if (CustomItemUtils.isCustomItem(itemStack)) return false;
        return true;
    }
    public boolean hasLapis(){
        ItemStack itemStack = inventory.getItem(LAPIS_SLOT);
        if (itemStack == null) return false;
        if (itemStack.getType() != Material.LAPIS_LAZULI) return false;
        if (CustomItemUtils.isCustomItem(itemStack)) return false;
        return true;
    }

    public List<ItemStack> getPassionItems(){
        List<ItemStack> passionItems = new ArrayList<>();
        for (int slot : PASSION_ITEMS_SLOTS) {
            ItemStack itemStack = inventory.getItem(slot);
            if (itemStack == null) continue;
            passionItems.add(itemStack);
        }
        return passionItems;
    }

    public void showEnchantedBooks(EnchantmentsContainer enchantmentsContainer){
        List<Enchantment> all = enchantmentsContainer.getAll();
        for (int i = 0; i < ENCHANTED_BOOKS_SLOTS.length; i++) {
            if (i > all.size()-1){
                inventory.setItem(ENCHANTED_BOOKS_SLOTS[i], null);
                continue;
            }
            Enchantment enchantment = all.get(i);

            ItemStack itemStack = new ItemStack(Material.ENCHANTED_BOOK);
            EnchantmentStorageMeta itemMeta = (EnchantmentStorageMeta) itemStack.getItemMeta();
            itemMeta.addStoredEnchant(enchantment, 1, true);
            itemStack.setItemMeta(itemMeta);

            inventory.setItem(ENCHANTED_BOOKS_SLOTS[i], itemStack);

        }
    }

    public void recalculate(){
        Bukkit.getLogger().info("CALLED RECALCULATE");
        List<ItemStack> passionItems = getPassionItems();
        EnchantmentsContainer enchantments = EnchantingPassion.mix(passionItems);
        showEnchantedBooks(enchantments);
    }


    @Override
    public void afterPlayerClicksItem(InventoryClickEvent event) {
        if (event.getAction() == InventoryAction.MOVE_TO_OTHER_INVENTORY){
            recalculate();
            return;
        }
        if (event.getCurrentItem() == null) return;
        recalculate();
    }

    @Override
    public void onPlayerClicksItem(InventoryClickEvent event) {
        if (event.getAction() == InventoryAction.MOVE_TO_OTHER_INVENTORY) {
            if (!(event.getClickedInventory() instanceof PlayerInventory)){
                if (!IntStream.of(PLACEABLE_SLOTS).anyMatch(x -> x == event.getSlot())){
                    event.setCancelled(true);
                }
                return;
            }
            int nextEmptySlot = getNextEmptySlot();
            if (nextEmptySlot == -1){
                event.setCancelled(true);
                return;
            }

            event.setCancelled(true);
            ItemStack itemStack = event.getCurrentItem();
            event.getClickedInventory().setItem(event.getSlot(), null);
            event.getInventory().setItem(nextEmptySlot, itemStack);

        }
        if (event.getClickedInventory() instanceof PlayerInventory) return;
        Bukkit.getLogger().info("CHECKING FOR SLOT");
        if (IntStream.of(PLACEABLE_SLOTS).anyMatch(x -> x == event.getSlot())){
            return;
        }
        event.setCancelled(true);
    }

    @Override
    public void onPlayerDragsItem(InventoryDragEvent event) {
        if (event.getInventory() instanceof PlayerInventory) return;
        event.setCancelled(true);
    }

    @Override
    public void onPlayerClosesInventory(InventoryCloseEvent event) {
        PlayerInventory playerInventory = event.getPlayer().getInventory();
        Inventory eventInventory = event.getInventory();

        for (int placeableSlot : PLACEABLE_SLOTS) {
            ItemStack itemStack = eventInventory.getItem(placeableSlot);
            if (itemStack != null){
                playerInventory.addItem(itemStack);
            }
        }
    }

    @Override
    public int getInventorySize() {
        return 9*6;
    }

    @Override
    public String getRawDisplayName() {
        return "";
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("space.-8").append(Component.text("0").font(Key.key("rpgu:enchanting")).color(TextColor.color(1f,1f,1f)))
                .append(Component.translatable("space.-170")).append(Component.translatable("container.enchant").color(TextColor.color(1f, 1f, 1f)));
    }
}

















