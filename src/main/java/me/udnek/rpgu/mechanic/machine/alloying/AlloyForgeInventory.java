package me.udnek.rpgu.mechanic.machine.alloying;

import io.papermc.paper.datacomponent.DataComponentTypes;
import me.udnek.coreu.custom.recipe.RecipeManager;
import me.udnek.coreu.util.ComponentU;
import me.udnek.rpgu.RpgU;
import me.udnek.rpgu.block.AlloyForgeBlockEntity;
import me.udnek.rpgu.mechanic.machine.AbstractMachineInventory;
import me.udnek.rpgu.mechanic.machine.RecipeTypes;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Color;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ColorableArmorMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.stream.IntStream;

public class AlloyForgeInventory extends AbstractMachineInventory {

    public static final TextColor COLOR = TextColor.color(91, 100, 118);
    public static final NamespacedKey SERIALIZE_RECIPE_KEY = new NamespacedKey(RpgU.getInstance(), "alloy_forge_recipe");
    public static final int[] STUFF_SLOTS = IntStream.concat(IntStream.range(3, 7), IntStream.range(12, 16)).toArray();
    public static final int FUEL_SLOT = 46;
    public static final int[] ALLOYS_SLOTS = new int[]{ 0, 1, 9, 10, 18, 19, 27, 28 };
    public static final int ADDITION_SLOT = 39;
    public static final int RESULT_SLOT = 42;
    public static final int PROGRESS_SLOT = 9*2-1;

    protected @Nullable ItemStack currentAddition = null;
    protected final @NotNull AlloyForgeBlockEntity alloyBlock;

    public AlloyForgeInventory(@NotNull AlloyForgeBlockEntity block){
        super(block, STUFF_SLOTS, new int[]{RESULT_SLOT}, FUEL_SLOT, SERIALIZE_RECIPE_KEY);
        this.alloyBlock = block;
    }

    public void updateItems(){
        serializeItems();

        if (currentRecipe != null) return;

        List<ItemStack> stuffs = new ArrayList<>();
        for (int stuffSlot : stuffSlots) {
            ItemStack item = getInventory().getItem(stuffSlot);
            if (item != null) stuffs.add(item);
        }
        List<ItemStack> alloys = new ArrayList<>();
        for (int alloySlot : ALLOYS_SLOTS) {
            ItemStack item = getInventory().getItem(alloySlot);
            if (item != null) alloys.add(item);
        }
        ItemStack fuel = getInventory().getItem(fuelSlot);
        if (fuel == null) return;
        ItemStack addition = getInventory().getItem(ADDITION_SLOT);
        if (addition == null) return;

        if (currentRecipe != null) return;
        List<AlloyingRecipe> recipes = RecipeManager.getInstance().getByType(RecipeTypes.ALLOYING);
        for (AlloyingRecipe recipe : recipes) {
            boolean matches = recipe.test(stuffs, alloys, fuel, addition);
            if (!matches) continue;
            if (!canPlaceIntoResult(recipe.getResult())) return;
            onFoundRecipe(recipe);
            return;
        }
    }

    @Override
    public void iterateTroughAllInputSlots(@NonNull Consumer<Integer> consumer) {
        super.iterateTroughAllInputSlots(consumer);
        for (int alloysSlot : ALLOYS_SLOTS) {
            consumer.accept(alloysSlot);
        }
        consumer.accept(ADDITION_SLOT);
    }

    @Override
    public void updateProgressAnimation(){
        ItemStack icon = FILLER.getItem();
        icon.editMeta(ColorableArmorMeta.class, itemMeta -> itemMeta.setColor(Color.fromRGB(COLOR.value())));

        String model = "gui/alloying/progress/";
        if (progress == 0) model += "empty";
        else model += (int) (progress*29);
        icon.setData(DataComponentTypes.ITEM_MODEL, new NamespacedKey(RpgU.getInstance(), model));
        getInventory().setItem(PROGRESS_SLOT, icon);
    }

    @Override
    protected void onReady(@NonNull ItemStack result) {
        super.onReady(result);
        alloyBlock.setLit(false);
        if (Objects.requireNonNull(((AlloyingRecipe) currentRecipe)).isKeepEnchantments() && currentAddition != null){
            result.addEnchantments(currentAddition.getEnchantments());
        }
    }

    @Override
    public void onFoundRecipe(@NonNull AlloyingRecipe recipe) {
        currentAddition = getInventory().getItem(ADDITION_SLOT);
        alloyBlock.setLit(true);
        super.onFoundRecipe(recipe);
    }

    @Override
    protected void getSlotsOrItemToSerialize(@NotNull SlotOrItemConsumer consumer) {
        super.getSlotsOrItemToSerialize(consumer);
        consumer.accept(currentAddition);
    }

    @Override
    protected void getSlotsOrItemToDeserialize(@NonNull List<ItemStack> items, @NonNull AtomicInteger bundleSlot) {
        super.getSlotsOrItemToDeserialize(items, bundleSlot);
        ItemStack addition = items.get(bundleSlot.addAndGet(1));
        if (!FILLER.isThisItem(addition)){
            currentAddition = addition;
        }
    }

    @Override
    public int getInventorySize() {return 9*6;}

    @Override
    public @Nullable Component getTitle() {
        return ComponentU.textWithNoSpace(
                -8,
                Component.text(0).color(COLOR).font(Key.key("rpgu", "alloying")),
                176)
                .append(Component.translatable("gui.rpgu.alloy_forge").font(NamespacedKey.minecraft("default")).color(NamedTextColor.BLACK));
    }
}
