package me.udnek.rpgu.mechanic.machine;

import me.udnek.coreu.custom.inventory.ConstructableCustomInventory;
import me.udnek.coreu.custom.inventory.SmartIntractableCustomInventory;
import me.udnek.coreu.custom.item.CustomItem;
import me.udnek.coreu.custom.recipe.RecipeManager;
import me.udnek.rpgu.item.Items;
import me.udnek.rpgu.mechanic.machine.alloying.AlloyingRecipe;
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

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

public abstract class AbstractMachineInventory extends ConstructableCustomInventory implements AbstractMachine, SmartIntractableCustomInventory {

    public static final CustomItem FILLER = Items.FERRUDAM_BOOTS;

    protected final int[] alloySlots;
    protected final int[] resulSlots;
    protected final int fuelSlot;

    protected boolean shouldUpdateItems = false;
    protected float progress = 0;
    protected @Nullable AbstractMachineRecipe currentRecipe = null;
    protected final NamespacedKey serializeRecipeKey;
    protected final @NotNull AbstractMachineBlockEntity block;

    public AbstractMachineInventory(@NotNull AbstractMachineBlockEntity block, int[] alloySlots, int[] resulSlots, int fuelSlot, @NotNull NamespacedKey recipeKey){
        this.block = block;
        this.alloySlots = Arrays.stream(alloySlots).sorted().toArray();
        this.resulSlots = Arrays.stream(resulSlots).sorted().toArray();
        this.fuelSlot = fuelSlot;
        this.serializeRecipeKey = recipeKey;
    }

    public abstract void updateProgressAnimation();
    public abstract void updateItems();


    @Override
    public boolean canPlaceItem(@Nullable ItemStack itemStack, int slot) {
        return canTakeItem(itemStack, slot) && Arrays.stream(resulSlots).anyMatch(i -> i != slot);
    }

    @Override
    public boolean canTakeItem(@Nullable ItemStack itemStack, int slot) {
        return !FILLER.isThisItem(getInventory().getItem(slot));
    }

    public void iterateTroughAllInputSlots(@NotNull Consumer<Integer> consumer){
        for (int alloysSlot : alloySlots) {
            consumer.accept(alloysSlot);
        }
        consumer.accept(fuelSlot);
    }

    public boolean canPlaceIntoResult(@NotNull ItemStack addItem){
        int toPlace = addItem.getAmount();
        for (int slot : resulSlots) {
            ItemStack item = getInventory().getItem(slot);
            if (item == null || FILLER.isThisItem(item) || item.getType().isAir()) return true;
            if (!addItem.isSimilar(item)) continue;

            int spaceLeft = item.getMaxStackSize() - item.getAmount();
            if (spaceLeft > 0) {
                toPlace -= spaceLeft;
                if (toPlace <= 0) return true;
            }
        }
        return toPlace <= 0;
    }

    public void addItem(int slot, @NotNull ItemStack addItem){
        ItemStack item = getInventory().getItem(slot);
        if (item == null || (Arrays.stream(resulSlots).anyMatch(i -> i == slot) && FILLER.isThisItem(item))) {
            getInventory().setItem(slot, addItem);
            return;
        }
        if (addItem.isSimilar(item)){
            getInventory().setItem(slot, item.add(addItem.getAmount()));
        }
    }

    public void takeItem(int slot, int amount){
        ItemStack item = getInventory().getItem(slot);
        if (item == null) return;
        getInventory().setItem(slot, item.subtract(amount));
        if (Arrays.stream(resulSlots).anyMatch(i -> i == slot) && getInventory().getItem(slot) == null){
            getInventory().setItem(slot, FILLER.getItem());
        }
    }

    public void updateItemsTickLater(){
        shouldUpdateItems = true;
    }

    public void onFoundRecipe(@NotNull AlloyingRecipe recipe){
        iterateTroughAllInputSlots(integer -> takeItem(integer, 1));
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
        for (int resulSlot : resulSlots) {
            consumer.accept(resulSlot);
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
            ItemStack result = currentRecipe.getResult();
            if (!canPlaceIntoResult(result)) return;
            progress = 0;
            onReady(result);
            addItem(resulSlots[0], result);//TODO
            currentRecipe = null;
            updateProgressAnimation();
            updateItemsTickLater();
        } else {
            progress += 1f / currentRecipe.getCraftDuration() * tickDelay;
        }
    }

    protected void onReady(@NotNull ItemStack item) {}

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
        iterateTroughAllInputSlots(slot -> {
            ItemStack item = items.get(bundleSlot.get());
            if (!FILLER.isThisItem(item)){
                getInventory().setItem(slot, item);
            }
            bundleSlot.incrementAndGet();
        });
        for (int resulSlot : resulSlots) {
            ItemStack result = items.get(bundleSlot.get());
            if (!FILLER.isThisItem(result)){
                getInventory().setItem(resulSlot, result);
            }
        }
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
        for (int slot : resulSlots) {
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

        for (int slot : resulSlots) {
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
            for (int slot : resulSlots) {
                if (getInventory().getItem(slot) == null){
                    getInventory().setItem(slot, FILLER.getItem());
                }
            }
        }
        updateItemsTickLater();
    }



    @Override
    public int getInventorySize() {return 9*5;}

    public interface SlotOrItemConsumer extends Consumer<Integer>{
        @Override
        void accept(Integer slot);
        void accept(@Nullable ItemStack itemStack);
    }
}
