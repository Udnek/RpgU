package me.udnek.rpgu.mechanic.alloying;

import com.destroystokyo.paper.event.block.BlockDestroyEvent;
import io.papermc.paper.datacomponent.DataComponentTypes;
import me.udnek.itemscoreu.custominventory.ConstructableCustomInventory;
import me.udnek.itemscoreu.custominventory.SmartIntractableCustomInventory;
import me.udnek.itemscoreu.customitem.CustomItem;
import me.udnek.itemscoreu.customrecipe.RecipeManager;
import me.udnek.itemscoreu.util.ComponentU;
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
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BundleMeta;
import org.bukkit.inventory.meta.ColorableArmorMeta;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

public class AlloyForgeInventory extends ConstructableCustomInventory implements AlloyForgeMachine, SmartIntractableCustomInventory {

    public static final TextColor COLOR = TextColor.color(91, 100, 118);
    public static final int CRAFT_DURATION = 20*20 / AlloyForgeManager.TICK_DELAY;
    public static final NamespacedKey SERIALIZE_RECIPE_KEY = new NamespacedKey(RpgU.getInstance(), "alloy_forge_recipe");
    public static final CustomItem FILLER = Items.TECHNICAL_INVENTORY_FILLER;

    public static final int[] ALLOYS_SLOTS = new int[]{
            9*0+0, 9*0+1, 9*0+2,
            9*1+0, 9*1+1, 9*1+2
            };
    public static final int FUEL_SLOT = 9*3+1;
    public static final int ADDITION_SLOT = 9*2+4;
    public static final int RESULT_SLOT = 9*2+7;
    public static final int PROGRESS_SLOT = 9*2-1;

    protected boolean shouldUpdateItems = false;
    protected float progress = 0;
    protected AlloyingRecipe currentRecipe = null;
    protected ItemStack currentAddition = null;

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
    }
    @Override
    public boolean canTakeItem(@Nullable ItemStack itemStack, int slot) {
        return !FILLER.isThisItem(inventory.getItem(slot));
    }
    public void iterateTroughAllInputSlots(@NotNull Consumer<Integer> consumer){
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

    public void updateProgressAnimation(){
        ItemStack icon = Items.TECHNICAL_INVENTORY_FILLER.getItem();
        icon.editMeta(ColorableArmorMeta.class, itemMeta -> itemMeta.setColor(Color.fromRGB(COLOR.value())));

        String model = "gui/alloying/progress/";
        if (progress == 0) model += "empty";
        else model += (int) (progress*29);
        icon.setData(DataComponentTypes.ITEM_MODEL, new NamespacedKey(RpgU.getInstance(), model));
        inventory.setItem(PROGRESS_SLOT, icon);
    }

    public void tickRecipe(){
        if (currentRecipe == null) return;
        updateProgressAnimation();

        if (progress >= 1){
            ItemStack result = currentRecipe.getResult();
            if (!canFit(RESULT_SLOT, result)) return;
            progress = 0;
            if (currentRecipe.isKeepEnchantments() && currentAddition != null){
                result.addEnchantments(currentAddition.getEnchantments());
            }
            addItem(RESULT_SLOT, result);
            currentRecipe = null;
            setLit(false);
            updateProgressAnimation();
            updateItemsTickLater();
        } else {
            progress += 1f/CRAFT_DURATION;
        }
    }

    public void setLit(boolean lit){
        Furnace blockData = (Furnace) block.getBlockData();
        blockData.setLit(lit);
        block.setBlockData(blockData, true);
    }

    public void foundRecipe(@NotNull AlloyingRecipe recipe){
        if (currentRecipe != null) return;
        if (!canFit(RESULT_SLOT, recipe.getResult())) return;

        currentAddition = inventory.getItem(ADDITION_SLOT);
        if (currentAddition != null) currentAddition = currentAddition.clone();

        iterateTroughAllInputSlots(integer -> takeItem(integer, 1));

        currentRecipe = recipe;
        progress = 0;

        serializeItems();
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

        if (currentRecipe != null) return;
        List<AlloyingRecipe> recipes = RecipeManager.getInstance().getByType(AlloyingRecipeType.getInstance());
        for (AlloyingRecipe recipe : recipes) {
            boolean matches = recipe.test(alloys, fuel, addition);
            if (!matches) continue;
            foundRecipe(recipe);
            return;
        }
    }

    public boolean canFit(int slot, @NotNull ItemStack addItem){
        ItemStack item = inventory.getItem(slot);
        if (item == null) return true;
        if (slot == RESULT_SLOT && FILLER.isThisItem(item)) return true;
        if (!addItem.isSimilar(item)) return false;
        return item.getAmount() + addItem.getAmount() <= item.getMaxStackSize();
    }

    public void addItem(int slot, @NotNull ItemStack addItem){
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
    public void unload(@NotNull Block block) {}

    @Override
    public void load(@NotNull Block block) {
        BlastFurnace state = (BlastFurnace) block.getState();
        ItemStack fuel = state.getInventory().getFuel();
        if (fuel == null || fuel.getType() != Material.BUNDLE) return;
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
        ItemStack addition = items.get(bundleSlot.addAndGet(1));
        if (!FILLER.isThisItem(addition)){
            currentAddition = addition;
        }

        String recipeId = fuel.getItemMeta().getPersistentDataContainer().get(SERIALIZE_RECIPE_KEY, PersistentDataType.STRING);
        if (recipeId == null) return;
        NamespacedKey id = NamespacedKey.fromString(recipeId);
        if (id == null) return;
        currentRecipe = RecipeManager.getInstance().getCustom(AlloyingRecipeType.getInstance(), id);
        // TODO: 8/27/2024 SERIALIZE AND LOAD PROGRESS
        progress = 0;
    }

    public void serializeItems(){
        ItemStack saveItem = new ItemStack(Material.BUNDLE);
        BundleMeta saveMeta = (BundleMeta) saveItem.getItemMeta();

        SlotOrItemConsumer consumer = new SlotOrItemConsumer() {
            @Override
            public void accept(Integer slot) {accept(inventory.getItem(slot));}
            @Override
            public void accept(@Nullable ItemStack itemStack) {
                saveMeta.addItem(Objects.requireNonNullElseGet(itemStack, FILLER::getItem));
            }

        };
        iterateTroughAllInputSlots(consumer);
        consumer.accept(RESULT_SLOT);
        consumer.accept(currentAddition);

        if (currentRecipe != null) {
            saveMeta.getPersistentDataContainer().set(
                    SERIALIZE_RECIPE_KEY,
                    PersistentDataType.STRING,
                    currentRecipe.getKey().asString());
            saveMeta.displayName(Component.text(currentRecipe.getKey().asString()));
        }
        saveItem.setItemMeta(saveMeta);
        BlastFurnace state = (BlastFurnace) block.getState();
        state.getInventory().setFuel(saveItem);
    }

    @Override
    public void onBlastInventoryOpen(InventoryOpenEvent event) {
        if (event.getPlayer().getGameMode() == GameMode.SPECTATOR) return;
        event.setCancelled(true);
        open((Player) event.getPlayer());
    }

    @Override
    public void onHopperSearch(HopperInventorySearchEvent event) {
        event.setInventory(inventory);
    }
    @Override
    public void onHopperGivesItem(InventoryMoveItemEvent event) {
        updateItemsTickLater();
    }
    @Override
    public void onHopperTakesItem(InventoryMoveItemEvent event) {
        ItemStack result = inventory.getItem(RESULT_SLOT);
        event.setCancelled(true);
        if (!FILLER.isThisItem(result)) {
            event.getDestination().addItem(result.asQuantity(1));
            takeItem(RESULT_SLOT, 1);
        }
        updateItemsTickLater();
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
    public void onPlayerDragsItem(@NotNull InventoryDragEvent event) {
        SmartIntractableCustomInventory.super.onPlayerDragsItem(event);
        updateItemsTickLater();
    }

    @Override
    public @Nullable Component getTitle() {
        return ComponentU.textWithNoSpace(
                -8,
                Component.text(0).color(COLOR).font(Key.key("rpgu", "alloying")),
                176)
                .append(Component.translatable("gui.rpgu.alloy_forge").font(NamespacedKey.minecraft("default")).color(NamedTextColor.BLACK));
    }
    @Override
    public int getInventorySize() {return 9*5;}


    public interface SlotOrItemConsumer extends Consumer<Integer>{
        @Override
        void accept(Integer slot);
        void accept(@Nullable ItemStack itemStack);
    }
}
