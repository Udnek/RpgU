package me.udnek.rpgu.mechanic.machine.crusher;

import io.papermc.paper.datacomponent.DataComponentTypes;
import me.udnek.coreu.custom.recipe.RecipeManager;
import me.udnek.coreu.util.ComponentU;
import me.udnek.rpgu.RpgU;
import me.udnek.rpgu.block.CrusherBlockEntity;
import me.udnek.rpgu.mechanic.machine.AbstractMachineInventory;
import me.udnek.rpgu.mechanic.machine.AbstractMachineRecipe;
import me.udnek.rpgu.mechanic.machine.RecipeTypes;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Color;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ItemType;
import org.bukkit.inventory.meta.ColorableArmorMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class CrusherInventory extends AbstractMachineInventory {

    public static final TextColor COLOR = NamedTextColor.WHITE;
    public static final NamespacedKey SERIALIZE_RECIPE_KEY = new NamespacedKey(RpgU.getInstance(), "alloy_forge_recipe");
    public static final int STUFF_SLOT = 5;
    public static final int[] RESULT_SLOTS = IntStream.concat(IntStream.concat(IntStream.range(22, 25), IntStream.range(31, 34)), IntStream.range(40, 43)).toArray();
    public static final int FUEL_SLOT = 18;
    public static final int PROGRESS_SLOT = 43;

    protected final @NotNull CrusherBlockEntity crusherBlock;
    protected int litTime = 0;
    protected List<ItemStack> currentResult;

    public CrusherInventory(@NonNull CrusherBlockEntity block) {
        super(block, new int[]{STUFF_SLOT}, RESULT_SLOTS, FUEL_SLOT, SERIALIZE_RECIPE_KEY);
        this.crusherBlock = block;
    }

    protected boolean isLit(){
        return litTime > 0;
    }

    @Override
    public void tick(int tickDelay) {
        super.tick(tickDelay);
        if (isLit()){
            if (currentResult == null) updateItemsTickLater();
            litTime -= tickDelay;
        } else {
            crusherBlock.setLit(false);
        }
    }

    @Override
    public void updateProgressAnimation() {
        ItemStack icon = FILLER.getItem();
        icon.editMeta(ColorableArmorMeta.class, itemMeta -> itemMeta.setColor(Color.fromRGB(COLOR.value())));

        String model = "gui/crusher/progress/";
        if (progress == 0) model += "empty";
        else model += (int) (progress*15);
        icon.setData(DataComponentTypes.ITEM_MODEL, new NamespacedKey(RpgU.getInstance(), model));
        getInventory().setItem(PROGRESS_SLOT, icon);
    }

    @Override
    public void updateItems() {
        serializeItems();
        if (currentRecipe != null) return;

        List<ItemStack> stuffs = new ArrayList<>();
        for (int stuffSlot : stuffSlots) {
            ItemStack item = getInventory().getItem(stuffSlot);
            if (item != null) stuffs.add(item);
        }

        List<CrusherRecipe> recipes = RecipeManager.getInstance().getByType(RecipeTypes.CRUSHER);
        for (CrusherRecipe recipe : recipes) {
            boolean matches = recipe.test(stuffs);
            if (!matches) continue;
            List<ItemStack> result = recipe.generateResult();
            if (!canPlaceIntoResult(result)) return;

            if (!isLit()) {
                ItemStack fuel = getInventory().getItem(fuelSlot);
                if (fuel == null) return;
                ItemType itemType = fuel.getType().asItemType();
                if (itemType == null) return;
                int burnDuration = itemType.getBurnDuration();
                if (burnDuration == 0) return;
                onBeginLit(burnDuration);
            }

            currentResult = result;
            onFoundRecipe(recipe);
            return;
        }
    }

    protected void onBeginLit(int burnDuration) {
        litTime = burnDuration;
        takeItem(fuelSlot, 1);
        crusherBlock.setLit(true);
    }

    @Override
    public List<ItemStack> getResult() {
        return currentResult;
    }

    @Override
    public void onFoundRecipe(@NonNull AbstractMachineRecipe recipe) {
        super.onFoundRecipe(recipe);
        takeItem(STUFF_SLOT, 1);
        crusherBlock.setWorking(true);
    }

    @Override
    protected void onReady(@NonNull List<ItemStack> item) {
        crusherBlock.setWorking(false);
        currentResult = null;
    }

    @Override
    public @Nullable Component getTitle() {
        return ComponentU.textWithNoSpace(
                        -8,
                        Component.text(0).color(COLOR).font(Key.key("rpgu", "crusher")),
                        176)
                .append(Component.translatable("gui.rpgu.crusher").font(NamespacedKey.minecraft("default")).color(NamedTextColor.BLACK));
    }

    @Override
    public int getInventorySize() {
        return 9*6;
    }
}
