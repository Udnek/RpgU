package me.udnek.rpgu.mechanic.alloying;

import com.destroystokyo.paper.event.block.BlockDestroyEvent;
import me.udnek.itemscoreu.custominventory.ConstructableCustomInventory;
import me.udnek.itemscoreu.custominventory.SmartIntractableCustomInventory;
import me.udnek.itemscoreu.customitem.CustomItem;
import me.udnek.itemscoreu.customrecipe.RecipeManager;
import me.udnek.itemscoreu.util.ComponentU;
import me.udnek.itemscoreu.util.LogUtils;
import me.udnek.rpgu.RpgU;
import me.udnek.rpgu.item.Items;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.*;
import org.bukkit.block.BlastFurnace;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.Furnace;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockExpEvent;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BundleMeta;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

public class AlloyForgeInventory extends ConstructableCustomInventory implements AlloyForgeMachine, SmartIntractableCustomInventory {

    public static final int CRAFT_DURATION = 40;

    public static final NamespacedKey SERIALIZE_RECIPE_KEY = new NamespacedKey(RpgU.getInstance(), "alloy_forge_recipe");

    protected static final CustomItem FILLER = Items.TECHNICAL_INVENTORY_FILLER;

    protected static final int[] ALLOYS_SLOTS = new int[]
            {
            9*0+0, 9*0+1, 9*0+2,
            9*1+0, 9*1+1, 9*1+2
            };
    protected static final int FUEL_SLOT = 9*3+1;
    protected static final int ADDITION_SLOT = 9*2+4;
    protected static final int RESULT_SLOT = 9*2+7;

    protected boolean shouldUpdateItems = false;
    protected float progress = 0;
    protected AlloyingRecipe currentRecipe = null;

    protected final Block block;

    public AlloyForgeInventory(Block block){
        this.block = block;
    }

    public static void acceptIfInventoryIsForge(Inventory inventory, Consumer<AlloyForgeInventory> consumer){
        if (inventory.getHolder() instanceof AlloyForgeInventory alloyForgeInventory){
            consumer.accept(alloyForgeInventory);
        }
    }
    @Override
    public boolean canPlaceItem(@Nullable ItemStack itemStack, int slot) {
        return canTakeItem(itemStack, slot) && slot != RESULT_SLOT;
        //return slot == ADDITION_SLOT || slot == FUEL_SLOT || (Arrays.stream(ALLOYS_SLOTS).anyMatch(i -> i == slot));
    }
    @Override
    public boolean canTakeItem(@Nullable ItemStack itemStack, int slot) {
        return !FILLER.isThisItem(inventory.getItem(slot));
        //return canPlaceItem(itemStack, slot) || slot == RESULT_SLOT;
    }
    @Override
    public int getInventorySize() {return 9*5;}
    public void iterateTroughAllInputSlots(Consumer<Integer> consumer){
        for (int alloysSlot : ALLOYS_SLOTS) {
            consumer.accept(alloysSlot);
        }
        consumer.accept(FUEL_SLOT);
        consumer.accept(ADDITION_SLOT);
    }
    @Override
    public void tick() {
        if (block.getType() != Material.BLAST_FURNACE){
            AlloyForgeManager.getInstance().addUnloadTicket(block);
            return;
        }
        if (shouldUpdateItems){
            shouldUpdateItems = false;
            updateItems();
        }
        tickRecipe();
    }

    public void tickRecipe(){
        if (currentRecipe == null) return;

        if (progress >= 1){
            if (!canFit(RESULT_SLOT, currentRecipe.getResult())) return;
            progress = 0;
            addItem(RESULT_SLOT, currentRecipe.getResult());
            currentRecipe = null;
            setLit(false);
            updateItems();
        } else {
            inventory.setItem(8, inventory.getItem(8).asQuantity((int) (Math.max(progress*64f, 1))));
            progress += 1f/CRAFT_DURATION;
        }
    }

    public void setLit(boolean b){
        // TODO: 10/7/2024 MAKE WORKING
        Furnace blockData = (Furnace) block.getBlockData();
        blockData.setLit(b);
    }

    public void foundRecipe(AlloyingRecipe recipe){
        if (currentRecipe != null) return;
        if (!canFit(RESULT_SLOT, recipe.getResult())) return;

        iterateTroughAllInputSlots(integer -> takeItem(integer, 1));
        serializeItems();

        currentRecipe = recipe;
        progress = 0;
        setLit(true);
    }

    public void updateItems(){
        serializeItems();

        if (currentRecipe != null) return;

        List<ItemStack> alloys = new ArrayList<>();
        for (int alloySlot : ALLOYS_SLOTS) {
            ItemStack item = inventory.getItem(alloySlot);
            if (item != null) alloys.add(item);
        }
        ItemStack fuel = inventory.getItem(FUEL_SLOT);
        if (fuel == null) return;
        ItemStack addition = inventory.getItem(ADDITION_SLOT);
        if (addition == null) return;

        List<AlloyingRecipe> recipes = RecipeManager.getInstance().getByType(AlloyingRecipeType.getInstance());
        for (AlloyingRecipe recipe : recipes) {
            boolean matches = recipe.matches(alloys, fuel, addition);
            if (!matches) continue;
            foundRecipe(recipe);
            return;
        }
    }

    public boolean canFit(int slot, ItemStack addItem){
        ItemStack item = inventory.getItem(slot);
        if (item == null) return true;
        if (slot == RESULT_SLOT && FILLER.isThisItem(item)) return true;
        if (!addItem.isSimilar(item)) return false;
        return item.getAmount() + addItem.getAmount() <= item.getMaxStackSize();
    }

    public void addItem(int slot, ItemStack addItem){
        ItemStack item = inventory.getItem(slot);
        if (item == null || (slot == RESULT_SLOT && FILLER.isThisItem(item))) {
            inventory.setItem(slot, addItem);
            return;
        }
        if (addItem.isSimilar(item)){
            inventory.setItem(slot, item.add(addItem.getAmount()));
        }

    }
    public void takeItem(int slot, int amount){
        ItemStack item = inventory.getItem(slot);
        if (item == null) return;
        inventory.setItem(slot, item.subtract(amount));
        if (slot == RESULT_SLOT && inventory.getItem(slot) == null){
            inventory.setItem(slot, FILLER.getItem());
        }
    }


    public void updateItemsTickLater(){
        shouldUpdateItems = true;
    }

    @Override
    public void generateInventory(int size, Component title) {
        this.inventory = Bukkit.createInventory(this, size, title);
        for (int i = 0; i < size; i++) {
            inventory.setItem(i, FILLER.getItem());
        }
        iterateTroughAllInputSlots(integer -> inventory.setItem(integer, null));
    }

    @Override
    public void unload(@NotNull Block block) {
        LogUtils.log("TERMINATE " + block);
        if (block.getType() != Material.BLAST_FURNACE) return;

        BlastFurnace state = (BlastFurnace) block.getState();

        if (currentRecipe != null){
            state.getPersistentDataContainer().set(
                    SERIALIZE_RECIPE_KEY,
                    PersistentDataType.STRING,
                    currentRecipe.getKey().asString());
        } else {
            state.getPersistentDataContainer().remove(SERIALIZE_RECIPE_KEY);
        }

        state.update(true, false);

        LogUtils.log("SAVE_ITEM_AFTER: " + ((BlastFurnace) block.getState()).getInventory().getFuel());
        LogUtils.log("SAVED_RECIPE: " + ((BlastFurnace) block.getState()).getPersistentDataContainer().get(SERIALIZE_RECIPE_KEY, PersistentDataType.STRING));
    }
    @Override
    public void load(@NotNull Block block) {
        LogUtils.log("SETUP " + block);
        BlastFurnace state = (BlastFurnace) block.getState();
        ItemStack fuel = state.getInventory().getFuel();
        if (fuel != null && fuel.getType() == Material.BUNDLE){
            BundleMeta saveMeta = (BundleMeta) fuel.getItemMeta();
            List<ItemStack> items = saveMeta.getItems();
            AtomicInteger bundleSlot = new AtomicInteger(0);
            iterateTroughAllInputSlots(slot -> {
                ItemStack item = items.get(bundleSlot.get());
                if (!FILLER.isThisItem(item)){
                    inventory.setItem(slot, item);
                }
                bundleSlot.incrementAndGet();
            });
            ItemStack result = items.get(bundleSlot.get());
            if (!FILLER.isThisItem(result)){
                inventory.setItem(RESULT_SLOT, result);
            }
        }
        String recipeId = state.getPersistentDataContainer().get(SERIALIZE_RECIPE_KEY, PersistentDataType.STRING);
        if (recipeId == null) return;
        NamespacedKey id = NamespacedKey.fromString(recipeId);
        if (id == null) return;
        currentRecipe = RecipeManager.getInstance().getCustom(AlloyingRecipeType.getInstance(), id);
        // TODO: 8/27/2024 SERIALIZE AND LOAD PROGRESS
        progress = 0;
    }
    public void serializeItems(){
        LogUtils.log("CALLED SERIALIZATION");
        ItemStack saveItem = new ItemStack(Material.BUNDLE);
        BundleMeta saveMeta = (BundleMeta) saveItem.getItemMeta();

        AtomicBoolean atLeastOneItem = new AtomicBoolean(false);
        Consumer<Integer> consumer = new Consumer<>() {
            @Override
            public void accept(Integer slot) {
                ItemStack item = inventory.getItem(slot);
                if (item == null) {
                    saveMeta.addItem(FILLER.getItem());
                } else {
                    atLeastOneItem.set(true);
                    saveMeta.addItem(item);
                }
            }
        };
        iterateTroughAllInputSlots(consumer);
        consumer.accept(RESULT_SLOT);

        if (!atLeastOneItem.get()) return;

        BlastFurnace state = (BlastFurnace) block.getState();
        saveItem.setItemMeta(saveMeta);
        state.getInventory().setFuel(saveItem);
        LogUtils.log("SAVE_ITEM: " + saveItem);

    }
    @Override
    public void onRightClick(PlayerInteractEvent event) {
        if (event.getPlayer().isSneaking()) return;
        event.setCancelled(true);
        open(event.getPlayer());
    }

    @Override
    public void onBlastInventoryOpen(InventoryOpenEvent event) {
        event.setCancelled(true);
        open((Player) event.getPlayer());
    }

    @Override
    public void onHopperSearch(HopperInventorySearchEvent event) {
        event.setInventory(inventory);
    }
    @Override
    public void onHopperGivesItem(InventoryMoveItemEvent event) {}
    @Override
    public void onHopperTakesItem(InventoryMoveItemEvent event) {
        ItemStack result = inventory.getItem(RESULT_SLOT);
        event.setCancelled(true);
        if (!FILLER.isThisItem(result)) {
            event.getDestination().addItem(result.asQuantity(1));
            takeItem(RESULT_SLOT, 1);
        }
    }
    @Override
    public void onDestroy(BlockDestroyEvent event) {destroy(event);}
    @Override
    public void onBreak(BlockBreakEvent event) {destroy(event);}
    protected void destroy(BlockExpEvent event){
        BlastFurnace state = (BlastFurnace) block.getState();
        state.getInventory().setFuel(null);

        AlloyForgeManager.getInstance().addUnloadTicket(block);
        World world = event.getBlock().getWorld();
        Location location = block.getLocation().toCenterLocation();
        iterateTroughAllInputSlots(slot -> {
            ItemStack item = inventory.getItem(slot);
            if (item == null) return;
            world.dropItemNaturally(location, item);
        });
        ItemStack item = inventory.getItem(RESULT_SLOT);
        if (!FILLER.isThisItem(item)) world.dropItemNaturally(location, item);
    }

    @Override
    public void onPlayerClicksItem(InventoryClickEvent event) {

        SmartIntractableCustomInventory.super.onPlayerClicksItem(event);

        // TODO: 8/26/2024 REMOVE WHEN FIXED IN ITEMSCOREU
        if (event.getClickedInventory() != this.getInventory() && event.getAction() == InventoryAction.MOVE_TO_OTHER_INVENTORY){
            event.setCancelled(false);
        }

        if (event.getInventory() == event.getClickedInventory()){
            if (inventory.getItem(RESULT_SLOT) == null){
                inventory.setItem(RESULT_SLOT, FILLER.getItem());
            }
        }
        updateItemsTickLater();
    }
    @Override
    public void onPlayerDragsItem(InventoryDragEvent event) {
        SmartIntractableCustomInventory.super.onPlayerDragsItem(event);
        updateItemsTickLater();
    }

    @Override
    public Component getDisplayName() {
        return ComponentU.textWithNoSpace(
                -8,
                Component.text(0).color(TextColor.color(91, 100, 118)).font(Key.key("rpgu", "alloying")),
                176)
                .append(Component.translatable("gui.rpgu.alloy_forge").font(NamespacedKey.minecraft("default")).color(NamedTextColor.BLACK));
/*        return ComponentU.space(-8).append(ComponentU.textWithNoSpace(Component.text(0).color(NamedTextColor.WHITE).font(Key.key("rpgu", "alloying")), 176))
                .append(Component.translatable("gui.rpgu.alloy_forge").font(NamespacedKey.minecraft("default")).color(NamedTextColor.BLACK));*/
    }
}
