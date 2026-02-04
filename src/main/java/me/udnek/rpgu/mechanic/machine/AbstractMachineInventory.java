package me.udnek.rpgu.mechanic.machine;

import me.udnek.coreu.custom.inventory.ConstructableCustomInventory;
import me.udnek.coreu.custom.inventory.SmartIntractableCustomInventory;
import me.udnek.coreu.custom.item.CustomItem;
import me.udnek.coreu.custom.recipe.RecipeManager;
import me.udnek.rpgu.item.Items;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BundleMeta;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

public abstract class AbstractMachineInventory extends ConstructableCustomInventory implements AbstractMachine, SmartIntractableCustomInventory {

    public static final CustomItem FILLER = Items.TECHNICAL_INVENTORY_FILLER;

    protected final int[] stuffSlots;
    protected final int[] resultSlots;
    protected final int fuelSlot;

    protected boolean shouldUpdateItems = false;
    protected float progress = 0;
    protected @Nullable AbstractMachineRecipe currentRecipe = null;
    protected final NamespacedKey serializeRecipeKey;
    protected final @NotNull AbstractMachineBlockEntity block;

    public AbstractMachineInventory(@NotNull AbstractMachineBlockEntity block, int[] stuffSlots, int[] resultSlots, int fuelSlot, @NotNull NamespacedKey recipeKey){
        this.block = block;
        this.stuffSlots = Arrays.stream(stuffSlots).sorted().toArray();
        this.resultSlots = Arrays.stream(resultSlots).sorted().toArray();
        this.fuelSlot = fuelSlot;
        this.serializeRecipeKey = recipeKey;
    }

    public abstract void updateProgressAnimation();
    public abstract void updateItems();
    public abstract List<ItemStack> getResult();


    @Override
    public boolean canPlaceItem(@Nullable ItemStack itemStack, int slot) {
        return canTakeItem(itemStack, slot) && Arrays.stream(resultSlots).anyMatch(i -> i != slot);
    }

    @Override
    public boolean canTakeItem(@Nullable ItemStack itemStack, int slot) {
        return !FILLER.isThisItem(getInventory().getItem(slot));
    }

    public void iterateTroughAllInputSlots(@NotNull Consumer<Integer> consumer){
        for (int stuffSlot : stuffSlots) {
            consumer.accept(stuffSlot);
        }
        consumer.accept(fuelSlot);
    }

    public boolean canPlaceIntoResult(@NotNull List<ItemStack> items) {
        Map<Integer, ItemStack> resultInfo = new HashMap<>();
        for (int slot : resultSlots) {
            ItemStack is = getInventory().getItem(slot);
            if (is != null && !is.isEmpty()) {
                resultInfo.put(slot, is.clone());
            }
        }

        for (ItemStack toAdd : items) {
            if (toAdd == null || toAdd.isEmpty()) continue;
            boolean isPlaced = false;
            for (int slot : resultSlots) {
                ItemStack current = resultInfo.getOrDefault(slot, null);
                if (current == null || FILLER.isThisItem(current) || current.isEmpty()) {
                    resultInfo.put(slot, toAdd.clone());
                    isPlaced = true;
                    break;
                }
                if (toAdd.isSimilar(current)) {
                    int newSpace = current.getAmount() + toAdd.getAmount();
                    if (newSpace <= current.getMaxStackSize()) {
                        current.setAmount(newSpace);
                        isPlaced = true;
                        break;
                    }
                }
            }
            if (isPlaced) continue;
            for (int slot : resultSlots) {
                if (!resultInfo.containsKey(slot)) {
                    resultInfo.put(slot, toAdd.clone());
                    isPlaced = true;
                    break;
                }
            }
            if (!isPlaced) return false;
        }
        return true;
    }

    public void addItemToResult(@NotNull List<ItemStack> items){
        for (ItemStack addItem : items) {
            int remaining = addItem.getAmount();

            for (int slot : resultSlots) {
                ItemStack item = getInventory().getItem(slot);
                if (item == null || item.isEmpty() || FILLER.isThisItem(item) || !addItem.isSimilar(item)) continue;

                int spaceLeft = item.getMaxStackSize() - item.getAmount();
                if (spaceLeft <= 0) continue;

                int canAdd = Math.min(spaceLeft, remaining);
                item.setAmount(item.getAmount() + canAdd);
                remaining -= canAdd;

                if (remaining == 0) break;
            }
            if (remaining == 0) continue;

            addItem.setAmount(remaining);
            for (int slot : resultSlots) {
                ItemStack item = getInventory().getItem(slot);
                if (item == null || item.isEmpty() || !FILLER.isThisItem(item)) continue;
                getInventory().setItem(slot, addItem);
                break;
            }
        }

    }

    public void takeItem(int slot, int amount){
        ItemStack item = getInventory().getItem(slot);
        if (item == null) return;
        getInventory().setItem(slot, item.subtract(amount));
        if (Arrays.stream(resultSlots).anyMatch(i -> i == slot) && getInventory().getItem(slot) == null){
            getInventory().setItem(slot, FILLER.getItem());
        }
    }

    public void updateItemsTickLater(){
        shouldUpdateItems = true;
    }

    public void onFoundRecipe(@NotNull AbstractMachineRecipe recipe){
        currentRecipe = recipe;
        progress = 0;
        serializeItems();
    }

    public void serializeItems(){
        ItemStack saveItem = new ItemStack(Material.BUNDLE);
        BundleMeta saveMeta = (BundleMeta) saveItem.getItemMeta();

        getSlotsOrItemToSerialize(new SlotOrItemConsumer() {
            @Override
            public void accept(Integer slot) {accept(getInventory().getItem(slot));}
            @Override
            public void accept(@Nullable ItemStack itemStack) {
                itemStack = Objects.requireNonNullElseGet(itemStack, FILLER::getItem);
                if (itemStack.isEmpty()) itemStack = FILLER.getItem();
                saveMeta.addItem(itemStack);
            }
        });

        if (currentRecipe != null) {
            saveMeta.getPersistentDataContainer().set(
                    serializeRecipeKey,
                    PersistentDataType.STRING,
                    currentRecipe.getKey().asString());
            saveMeta.displayName(Component.text(currentRecipe.getKey().asString()));
        }
        saveItem.setItemMeta(saveMeta);
        block.getReal().getInventory().setItem(0, saveItem);
    }


    protected void getSlotsOrItemToSerialize(@NotNull SlotOrItemConsumer consumer) {
        iterateTroughAllInputSlots(consumer);
        for (int resultSlot : resultSlots) {
            consumer.accept(resultSlot);
        }
    }

    @Override
    public void tick(int tickDelay) {
        if (shouldUpdateItems){
            shouldUpdateItems = false;
            updateItems();
        }
        tickRecipe(tickDelay);
    }

    public void tickRecipe(int tickDelay){
        if (currentRecipe == null) return;
        updateProgressAnimation();

        if (progress >= 1){
            List<ItemStack> result = getResult();
            if (!canPlaceIntoResult(result)) return;
            progress = 0;
            onReady(result);
            addItemToResult(result);
            currentRecipe = null;
            updateProgressAnimation();
            updateItemsTickLater();
        } else {
            progress += 1f / currentRecipe.getCraftDuration() * tickDelay;
        }
    }

    protected void onReady(@NotNull List<ItemStack> item) {}

    @Override
    public void load() {
        ItemStack fuel = block.getReal().getInventory().getItem(0);
        if (fuel == null || fuel.getType() != Material.BUNDLE) return;
        List<ItemStack> items = ((BundleMeta) fuel.getItemMeta()).getItems();
        AtomicInteger bundleSlot = new AtomicInteger(0);

        getSlotsOrItemToDeserialize(items, bundleSlot);

        String recipeId = fuel.getItemMeta().getPersistentDataContainer().get(serializeRecipeKey, PersistentDataType.STRING);
        if (recipeId == null) return;
        NamespacedKey id = NamespacedKey.fromString(recipeId);
        if (id == null) return;
        currentRecipe = RecipeManager.getInstance().getCustom(RecipeTypes.ALLOYING, id);
        // TODO: 8/27/2024 SERIALIZE AND LOAD PROGRESS
        progress = 0;
    }

    protected void getSlotsOrItemToDeserialize(@NotNull List<ItemStack> items, @NotNull AtomicInteger bundleSlot) {
        iterateTroughAllInputSlots(slot -> tryConsumeNextItem(itemStack -> getInventory().setItem(slot, itemStack), items, bundleSlot));
        for (int resultSlot : resultSlots) {
            tryConsumeNextItem(itemStack -> getInventory().setItem(resultSlot, itemStack), items, bundleSlot);
        }
    }

    protected void tryConsumeNextItem(@NotNull Consumer<ItemStack> consumer, @NotNull List<ItemStack> items, @NotNull AtomicInteger bundleSlot){
        ItemStack item = items.get(bundleSlot.get());
        if (!FILLER.isThisItem(item)){
            consumer.accept(item);
        }
        bundleSlot.incrementAndGet();
    }

    @Override
    public void destroy(){
        World world = block.getReal().getWorld();
        Location location = block.getReal().getLocation().toCenterLocation();
        iterateTroughAllInputSlots(slot -> {
            ItemStack item = getInventory().getItem(slot);
            if (item == null) return;
            world.dropItemNaturally(location, item);
        });
        for (int slot : resultSlots) {
            ItemStack item = getInventory().getItem(slot);
            if (item == null || item.isEmpty() || FILLER.isThisItem(item)) continue;
            world.dropItemNaturally(location, item.clone());
        }
    }

    @Override
    public void openInventory(@NotNull Player player) {
        open(player);
    }

    @Override
    public void onHopperMoveFrom(@NotNull InventoryMoveItemEvent event) {
        super.onHopperMoveFrom(event);
        event.setCancelled(true);

        for (int slot : resultSlots) {
            ItemStack result = getInventory().getItem(slot);
            if (result != null && !result.isEmpty() && !FILLER.isThisItem(result)) {
                event.getDestination().addItem(result.asQuantity(1));
                takeItem(slot, 1);
                updateItemsTickLater();
                return;
            }
        }
    }

    @Override
    public void onHopperMoveInto(@NotNull InventoryMoveItemEvent event) {
        super.onHopperMoveInto(event);
        updateItemsTickLater();
    }

    @Override
    public void onHopperSearch(@NotNull HopperInventorySearchEvent event) {
        event.setInventory(getInventory());
    }

    @Override
    public void onPlayerDragsItem(@NotNull InventoryDragEvent event) {
        SmartIntractableCustomInventory.super.onPlayerDragsItem(event);
        updateItemsTickLater();
    }

    @Override
    public @NotNull Inventory generateInventory(int size, @Nullable Component title) {
        Inventory inventory = super.generateInventory(size, title);
        for (int i = 0; i < size; i++) {
            inventory.setItem(i, FILLER.getItem());
        }
        iterateTroughAllInputSlots(i -> inventory.setItem(i, null));
        return inventory;
    }

    @Override
    public void unload() {}

    @Override
    public void onPlayerClicksItem(@NotNull InventoryClickEvent event) {

        SmartIntractableCustomInventory.super.onPlayerClicksItem(event);

        // TODO: 8/26/2024 REMOVE WHEN FIXED IN COREU
        if (event.getClickedInventory() != this.getInventory() && event.getAction() == InventoryAction.MOVE_TO_OTHER_INVENTORY){
            event.setCancelled(false);
        }

        if (event.getInventory() == event.getClickedInventory()){
            for (int slot : resultSlots) {
                if (getInventory().getItem(slot) == null){
                    getInventory().setItem(slot, FILLER.getItem());
                }
            }
        }
        updateItemsTickLater();
    }

    public interface SlotOrItemConsumer extends Consumer<Integer>{
        @Override
        void accept(Integer slot);
        void accept(@Nullable ItemStack itemStack);
    }
}
