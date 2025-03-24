package me.udnek.rpgu.item.equipment;

import com.destroystokyo.paper.event.player.PlayerReadyArrowEvent;
import io.papermc.paper.datacomponent.DataComponentTypes;
import io.papermc.paper.datacomponent.item.BundleContents;
import io.papermc.paper.datacomponent.item.PotionContents;
import me.udnek.itemscoreu.customcomponent.instance.InventoryInteractableItem;
import me.udnek.itemscoreu.customcomponent.instance.RightClickableItem;
import me.udnek.itemscoreu.customequipmentslot.slot.CustomEquipmentSlot;
import me.udnek.itemscoreu.customequipmentslot.slot.SingleSlot;
import me.udnek.itemscoreu.customequipmentslot.universal.BaseUniversalSlot;
import me.udnek.itemscoreu.customequipmentslot.universal.UniversalInventorySlot;
import me.udnek.itemscoreu.customitem.ConstructableCustomItem;
import me.udnek.itemscoreu.customitem.CustomItem;
import me.udnek.itemscoreu.util.Either;
import me.udnek.rpgu.RpgU;
import me.udnek.rpgu.component.ComponentTypes;
import me.udnek.rpgu.component.ability.passive.ConstructablePassiveAbility;
import me.udnek.rpgu.component.ability.property.AttributeBasedProperty;
import org.bukkit.Tag;
import org.bukkit.entity.*;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class Quiver extends ConstructableCustomItem {
    @Override
    public @NotNull String getRawId() {
        return "quiver";
    }

    @Override
    public boolean isUpdateMaterial() {
        return false;
    }

    @Override
    public void getComponentsToUpdate(@NotNull ComponentConsumer consumer) {
        consumer.accept(DataComponentTypes.ITEM_MODEL);
    }

    @Override
    public @Nullable DataSupplier<Integer> getMaxStackSize() {
        return DataSupplier.of(1);
    }

    @Override
    public void initializeComponents() {
        super.initializeComponents();

        getComponents().set(new QuiverRightClickableItem());
        getComponents().set(new QuiverInventoryInteractable());
        getComponents().getOrCreateDefault(ComponentTypes.EQUIPPABLE_ITEM).addPassive(new QuiverEntityShootBow());
    }

    public static class QuiverEntityShootBow extends ConstructablePassiveAbility<EntityShootBowEvent> {
        QuiverEntityShootBow() {
            getComponents().set(new AttributeBasedProperty(1, ComponentTypes.ABILITY_COOLDOWN));
        }

        @Override
        public @NotNull ActionResult action(@NotNull CustomItem customItem, @NotNull LivingEntity livingEntity, @NotNull Either<UniversalInventorySlot, SingleSlot> slot, @NotNull EntityShootBowEvent event) {
            ItemStack item = slot.getLeft().getItem(livingEntity);
            if (item == null) throw new RuntimeException("Ебать как ты выбил это еблан!!!! Это говнокод");
            BundleContents data = item.getData(DataComponentTypes.BUNDLE_CONTENTS);
            if (data == null) return ActionResult.NO_COOLDOWN;
            List<ItemStack> contents = new ArrayList<>(data.contents());
            if (contents.isEmpty()) return ActionResult.NO_COOLDOWN;

            ItemStack first = contents.getFirst();
            first.add(-1);
            if (first.getAmount() == 0) contents.removeFirst();
            else contents.set(0, first);
            item.setData(DataComponentTypes.BUNDLE_CONTENTS, BundleContents.bundleContents(contents));

            AbstractArrow projectile = (AbstractArrow) event.getProjectile();
            first.setAmount(1);
            projectile.setItemStack(first);

            if (projectile.getType() == EntityType.ARROW) {
                Arrow arrow = (Arrow) projectile;
                PotionContents dataFirst = first.getData(DataComponentTypes.POTION_CONTENTS);
                if (dataFirst != null) {
                    arrow.setBasePotionType(dataFirst.potion());
                    if (dataFirst.customColor() != null) arrow.setColor(dataFirst.customColor());
                }
            }



            return ActionResult.FULL_COOLDOWN;
        }

        @Override
        public @NotNull CustomEquipmentSlot getSlot() {
            return CustomEquipmentSlot.DUMB_INVENTORY;
        }

        @Override
        public void onFire(@NotNull CustomItem customItem, @NotNull UniversalInventorySlot slot, @NotNull EntityShootBowEvent event) {
            activate(customItem, event.getEntity(), new Either<>(slot, null), event);
        }

        @Override
        public void onChooseArrow(@NotNull CustomItem customItem, @NotNull UniversalInventorySlot slot, @NotNull PlayerReadyArrowEvent event) {
            Player player = event.getPlayer();
            ItemStack item = slot.getItem(player);
            if (item == null) throw new RuntimeException("Ебать как ты выбил это еблан!!!! Это говнокод");
            BundleContents data = item.getData(DataComponentTypes.BUNDLE_CONTENTS);
            if (data == null || data.contents().isEmpty()) return;
            if (customItem.isThisItem(event.getArrow())) {
                if (slot.equals(new BaseUniversalSlot(40))) new BukkitRunnable(){
                    @Override
                    public void run() {
                        slot.setItem(event.getArrow(), player);
                    }
                }.runTaskLater(RpgU.getInstance(), 1);
                slot.addItem(1, player);
                return;
            }

            event.setCancelled(true);
        }
    }

    public static class QuiverInventoryInteractable implements InventoryInteractableItem {
        public int MAX_SIZE = 4 * 64;

        @Override
        public void onBeingClicked(@NotNull CustomItem item, @NotNull InventoryClickEvent event) {
            ItemStack quiver = event.getCurrentItem();
            if (quiver == null) throw new RuntimeException("Ебать как ты выбил это!!!! Это говнокод");
            Player player = (Player) event.getWhoClicked();
            switch (event.getClick()) {
                case LEFT -> {
                    if (event.getAction() == InventoryAction.PICKUP_ALL) return;
                    ItemStack arrow = event.getCursor();

                    if (!Tag.ITEMS_ARROWS.isTagged(arrow.getType())) {
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

                    ItemStack stack = quiver.withType(arrow.getType());
                    stack.unsetData(DataComponentTypes.POTION_CONTENTS);
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
                    if (!Tag.ITEMS_ARROWS.isTagged(arrow.getType())) {
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
                newContents.add(item);
            }
            return newContents;
        }
    }

    public static class QuiverRightClickableItem implements RightClickableItem {
        @Override
        public void onRightClick(@NotNull CustomItem customItem, @NotNull PlayerInteractEvent playerInteractEvent) {
            ItemStack item = playerInteractEvent.getItem();
            if (item == null) return;
            BundleContents data = item.getData(DataComponentTypes.BUNDLE_CONTENTS);
            if (data == null) return;
            List<ItemStack> contents = new ArrayList<>(data.contents());
            if (contents.isEmpty()) return;

            ItemStack firstItem = contents.removeFirst();
            contents.add(firstItem);
            item.setData(DataComponentTypes.BUNDLE_CONTENTS, BundleContents.bundleContents(contents));
        }
    }
}