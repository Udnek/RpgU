package me.udnek.rpgu.item.equipment.quiver;

import io.papermc.paper.datacomponent.DataComponentTypes;
import io.papermc.paper.datacomponent.item.BundleContents;
import io.papermc.paper.datacomponent.item.ItemLore;
import me.udnek.coreu.custom.component.instance.InventoryInteractableItem;
import me.udnek.coreu.custom.item.CustomItem;
import me.udnek.rpgu.component.Components;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class QuiverLoadUnloadComponent implements InventoryInteractableItem {
    public static int MAX_SIZE = 4 * 64;

    @Override
    public void onBeingClicked(@NotNull CustomItem item, @NotNull InventoryClickEvent event) {
        ItemStack quiver = event.getCurrentItem();
        assert quiver != null;
        Player player = (Player) event.getWhoClicked();
        switch (event.getClick()) {
            case LEFT -> {
                if (event.getAction() == InventoryAction.PICKUP_ALL) return;
                ItemStack arrow = event.getCursor();
                CustomItem customItem = CustomItem.get(arrow);

                if (customItem == null || !(customItem.getComponents().has(Components.ARROW_ITEM))) {
                    event.setCancelled(true);
                    return;
                }

                BundleContents bundleData = quiver.getData(DataComponentTypes.BUNDLE_CONTENTS);
                if (bundleData == null) bundleData = BundleContents.bundleContents().build();

                List<ItemStack> contents = new ArrayList<>(bundleData.contents());
                int freeAmount = MAX_SIZE - contents.stream().mapToInt(ItemStack::getAmount).sum();

                ItemStack toQuiver = arrow.clone();
                if (freeAmount != 0) {
                    toQuiver.setAmount(Math.min(freeAmount, arrow.getAmount()));
                    quiver.setData(DataComponentTypes.BUNDLE_CONTENTS, BundleContents.bundleContents(stackOrAppend(contents, toQuiver)));
                } else toQuiver.setAmount(0);

                int remainder = arrow.getAmount() - toQuiver.getAmount();
                if (remainder != 0) {
                    ItemStack toPlayer = arrow.clone();
                    toPlayer.setAmount(remainder);
                    player.setItemOnCursor(toPlayer);
                } else player.setItemOnCursor(null);

                List<Component> lines = Quiver.generateLore(Objects.requireNonNull(item.getItem().lore()),
                        new ArrayList<>(Objects.requireNonNull(quiver.getData(DataComponentTypes.BUNDLE_CONTENTS)).contents()));
                quiver.setData(DataComponentTypes.LORE, ItemLore.lore(lines));

                ItemStack stack = quiver.withType(arrow.getType());
                stack.unsetData(DataComponentTypes.POTION_CONTENTS);
                stack.unsetData(DataComponentTypes.FIREWORKS);
                event.setCurrentItem(stack);
                event.setCancelled(true);
            }
            case RIGHT -> {
                BundleContents bundleData = quiver.getData(DataComponentTypes.BUNDLE_CONTENTS);
                if (bundleData == null) return;
                if (!event.getCursor().isEmpty()) return;


                ArrayList<ItemStack> contents = new ArrayList<>(bundleData.contents());
                if (contents.isEmpty()) return;
                ItemStack arrow = contents.removeFirst();
                quiver.setData(DataComponentTypes.BUNDLE_CONTENTS, BundleContents.bundleContents(contents));

                List<Component> lines = Quiver.generateLore(Objects.requireNonNull(item.getItem().lore()), contents);
                quiver.setData(DataComponentTypes.LORE, ItemLore.lore(lines));

                if (contents.isEmpty()) {
                    quiver.unsetData(DataComponentTypes.BUNDLE_CONTENTS);
                    event.setCurrentItem(quiver.withType(Quiver.DEFAULT_MATERIAL));
                } else event.setCurrentItem(quiver);

                player.setItemOnCursor(arrow);
                event.setCancelled(true);
            }
        }
    }

    @Override
    public void onClickWith(@NotNull CustomItem item, @NotNull InventoryClickEvent event) {
        ItemStack quiver = event.getCursor();
        Player player = (Player) event.getWhoClicked();
        switch (event.getClick()) {
            case LEFT -> {
                ItemStack arrow = event.getCurrentItem();
                if (arrow == null || arrow.isEmpty()) return;
                CustomItem customItem = CustomItem.get(arrow);

                if (customItem == null || !(customItem.getComponents().has(Components.ARROW_ITEM))) {
                    event.setCancelled(true);
                    return;
                }

                BundleContents bundleData = quiver.getData(DataComponentTypes.BUNDLE_CONTENTS);
                if (bundleData == null) bundleData = BundleContents.bundleContents().build();

                List<ItemStack> contents = new ArrayList<>(bundleData.contents());
                int freeAmount = MAX_SIZE - contents.stream().mapToInt(ItemStack::getAmount).sum();

                ItemStack toQuiver = arrow.clone();
                if (freeAmount != 0) {
                    toQuiver.setAmount(Math.min(freeAmount, arrow.getAmount()));
                    quiver.setData(DataComponentTypes.BUNDLE_CONTENTS, BundleContents.bundleContents(stackOrAppend(contents, toQuiver)));
                } else toQuiver.setAmount(0);

                int remainder = arrow.getAmount() - toQuiver.getAmount();
                if (remainder != 0) {
                    ItemStack toPlayer = arrow.clone();
                    toPlayer.setAmount(remainder);
                    event.setCurrentItem(toPlayer);
                } else event.setCurrentItem(null);

                List<Component> lines = Quiver.generateLore(Objects.requireNonNull(item.getItem().lore()),
                        new ArrayList<>(Objects.requireNonNull(quiver.getData(DataComponentTypes.BUNDLE_CONTENTS)).contents()));
                quiver.setData(DataComponentTypes.LORE, ItemLore.lore(lines));

                ItemStack stack = quiver.withType(arrow.getType());
                stack.unsetData(DataComponentTypes.POTION_CONTENTS);
                player.setItemOnCursor(stack);
                event.setCancelled(true);
            }
            case RIGHT -> {
                BundleContents bundleData = quiver.getData(DataComponentTypes.BUNDLE_CONTENTS);
                if (bundleData == null) return;
                ItemStack currentItem = event.getCurrentItem();
                if (currentItem == null || !currentItem.getType().isAir()) return;

                ArrayList<ItemStack> contents = new ArrayList<>(bundleData.contents());
                if (contents.isEmpty()) return;
                ItemStack arrow = contents.removeFirst();
                quiver.setData(DataComponentTypes.BUNDLE_CONTENTS, BundleContents.bundleContents(contents));

                List<Component> lines = Quiver.generateLore(Objects.requireNonNull(item.getItem().lore()), contents);
                quiver.setData(DataComponentTypes.LORE, ItemLore.lore(lines));

                if (contents.isEmpty()) {
                    quiver.unsetData(DataComponentTypes.BUNDLE_CONTENTS);
                    player.setItemOnCursor(quiver.withType(Quiver.DEFAULT_MATERIAL));
                } else player.setItemOnCursor(quiver);

                event.setCurrentItem(arrow);
                event.setCancelled(true);
            }
        }
    }

    public @NotNull List<ItemStack> stackOrAppend(@NotNull List<ItemStack> contents, @NotNull ItemStack itemStack) {
        List<ItemStack> newContents = new ArrayList<>();
        ItemStack item = itemStack.clone();
        int itemAmount = item.getAmount();
        for (ItemStack stack : contents) {
            if (stack.isSimilar(item) && itemAmount > 0) {
                int stackAmount = stack.getAmount();
                int addAmount = Math.min(itemAmount, stack.getMaxStackSize() - stackAmount);
                itemAmount -= addAmount;
                stack.setAmount(stackAmount + addAmount);
            }
            newContents.add(stack);
        }
        if (itemAmount != 0) {
            item.setAmount(itemAmount);
            newContents.addFirst(item);
        }
        return newContents;
    }
}