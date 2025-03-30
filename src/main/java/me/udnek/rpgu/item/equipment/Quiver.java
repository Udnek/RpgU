package me.udnek.rpgu.item.equipment;

import com.destroystokyo.paper.event.player.PlayerReadyArrowEvent;
import io.papermc.paper.datacomponent.DataComponentTypes;
import io.papermc.paper.datacomponent.item.BundleContents;
import io.papermc.paper.datacomponent.item.ChargedProjectiles;
import io.papermc.paper.datacomponent.item.ItemLore;
import io.papermc.paper.event.entity.EntityLoadCrossbowEvent;
import me.udnek.itemscoreu.customcomponent.instance.AutoGeneratingFilesItem;
import me.udnek.itemscoreu.customcomponent.instance.DispensableItem;
import me.udnek.itemscoreu.customcomponent.instance.InventoryInteractableItem;
import me.udnek.itemscoreu.customequipmentslot.slot.CustomEquipmentSlot;
import me.udnek.itemscoreu.customequipmentslot.slot.SingleSlot;
import me.udnek.itemscoreu.customequipmentslot.universal.BaseUniversalSlot;
import me.udnek.itemscoreu.customequipmentslot.universal.UniversalInventorySlot;
import me.udnek.itemscoreu.customitem.ConstructableCustomItem;
import me.udnek.itemscoreu.customitem.CustomItem;
import me.udnek.itemscoreu.util.Either;
import me.udnek.rpgu.RpgU;
import me.udnek.rpgu.component.ComponentTypes;
import me.udnek.rpgu.component.ability.active.ConstructableActiveAbilityComponent;
import me.udnek.rpgu.component.ability.passive.ConstructablePassiveAbility;
import me.udnek.rpgu.component.ability.property.AttributeBasedProperty;
import me.udnek.rpgu.component.instance.ArrowComponent;
import me.udnek.rpgu.item.Items;
import me.udnek.rpgu.lore.ability.PassiveAbilityLorePart;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.entity.AbstractArrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
        getComponents().set(DispensableItem.ALWAYS_DROP);
        getComponents().getOrCreateDefault(ComponentTypes.EQUIPPABLE_ITEM).addPassive(new QuiverEntityShootBow());
        getComponents().set(AutoGeneratingFilesItem.GENERATED_20X20);
    }

    public static @NotNull List<Component> generateLore(@NotNull List<Component> lore, @NotNull List<ItemStack> content){
        int sum = content.stream().mapToInt(ItemStack::getAmount).sum();
        TextColor color = sum >= new QuiverInventoryInteractable().MAX_SIZE ? NamedTextColor.RED : NamedTextColor.WHITE;
        lore.addFirst(Component.text(sum).append(Component.text(" / ")).append(Component.text(new QuiverInventoryInteractable().MAX_SIZE))
                .decoration(TextDecoration.ITALIC, false).color(NamedTextColor.WHITE).color(color));

        for (ItemStack itemStack : content.reversed()) {
            CustomItem customItem = CustomItem.get(itemStack);
            Component icon = Objects.requireNonNull(customItem).getComponents().getOrDefault(ComponentTypes.ARROW_ITEM).getIcon(customItem, itemStack);
            lore.addFirst(icon.append(Component.text(" x "  ).append(Component.text(itemStack.getAmount())).
                    decoration(TextDecoration.ITALIC, false).color(NamedTextColor.WHITE).font(Key.key("default"))));
        }
        return lore;
    }

    public static class QuiverEntityShootBow extends ConstructablePassiveAbility<EntityShootBowEvent> {
        public QuiverEntityShootBow() {
            getComponents().set(new AttributeBasedProperty(1, ComponentTypes.ABILITY_COOLDOWN));
        }

        @Override
        public void addLoreLines(@NotNull PassiveAbilityLorePart componentable) {
            componentable.addFullAbilityDescription(Items.QUIVER, 1);
            super.addLoreLines(componentable);
        }

        @Override
        public @NotNull CustomEquipmentSlot getSlot() {
            return CustomEquipmentSlot.DUMB_INVENTORY;
        }

        @Override
        public @NotNull ActionResult action(@NotNull CustomItem customItem, @NotNull LivingEntity livingEntity, @NotNull Either<UniversalInventorySlot, SingleSlot> slot, @NotNull EntityShootBowEvent event) {
            ItemStack item = slot.getLeft().getItem(livingEntity);
            System.out.println("Shoot item: " + CustomItem.get(slot.getLeft().getItem(livingEntity)).getId());
            System.out.println("Shoot bow: " + event.getBow().getType());
            if (item == null) throw new RuntimeException("Ебать как ты выбил это еблан!!!! Это говнокод");
            if (!item.isSimilar(event.getConsumable())) return ActionResult.NO_COOLDOWN;
            BundleContents data = item.getData(DataComponentTypes.BUNDLE_CONTENTS);
            if (data == null) return ActionResult.NO_COOLDOWN;
            List<ItemStack> contents = new ArrayList<>(data.contents());
            if (contents.isEmpty()) return ActionResult.NO_COOLDOWN;

            ItemStack arrowStack = contents.getFirst();
            CustomItem arrowCustom = Objects.requireNonNull(CustomItem.get(arrowStack));
            arrowCustom.getComponents().getOrDefault(ComponentTypes.ARROW_ITEM).onBeingShoot(arrowCustom, arrowStack, event);

            if (Objects.requireNonNull(event.getBow()).getType() == Material.BOW) {
                arrowStack.add(-1);
                if (arrowStack.getAmount() == 0) {
                    contents.removeFirst();
                    item.unsetData(DataComponentTypes.BUNDLE_CONTENTS);
                    slot.getLeft().setItem(item.withType(Quiver.DEFAULT_MATERIAL), livingEntity);
                } else {
                    contents.set(0, arrowStack);
                }
                item.setData(DataComponentTypes.BUNDLE_CONTENTS, BundleContents.bundleContents(contents));

                List<Component> lines = generateLore(Objects.requireNonNull(customItem.getItem().lore()), contents);
                item.setData(DataComponentTypes.LORE, ItemLore.lore(lines));
            }



            AbstractArrow projectile = (AbstractArrow) event.getProjectile();
            arrowStack.setAmount(1);
            projectile.setItemStack(arrowStack);

            return ActionResult.FULL_COOLDOWN;
        }

        @Override
        public void onFire(@NotNull CustomItem customItem, @NotNull UniversalInventorySlot slot, @NotNull EntityShootBowEvent event) {
            activate(customItem, event.getEntity(), new Either<>(slot, null), event);
        }

        @Override
        public void onChooseArrow(@NotNull CustomItem customItem, @NotNull UniversalInventorySlot slot, @NotNull PlayerReadyArrowEvent event) {
            System.out.println("called: " + slot + " item: " + (slot.getItem(event.getPlayer()) == null ? "null" : CustomItem.get(slot.getItem(event.getPlayer())).getId()));
            Player player = event.getPlayer();
            ItemStack item = slot.getItem(player);
            if (item == null) throw new RuntimeException("Ебать как ты выбил это еблан!!!! Это говнокод");
            BundleContents data = item.getData(DataComponentTypes.BUNDLE_CONTENTS);
            if (data == null || data.contents().isEmpty()) return;
            CustomItem arrow = CustomItem.get(data.contents().getFirst());
            if (arrow == null) throw new RuntimeException("Quiver onChooseArrow arrow is null");
            if (arrow.getComponents().getOrDefault(ComponentTypes.ARROW_ITEM).onChooseArrow(arrow, event) == ArrowComponent.ChoseArrowResult.DENY) {
                 return;
            }
            if (!customItem.isThisItem(event.getArrow())) {
                event.setCancelled(true);
                return;
            }
            ItemStack itemClone = item.clone();
            if (slot.equals(new BaseUniversalSlot(40)) || slot.equals(new BaseUniversalSlot(player.getInventory().getHeldItemSlot()))){
                slot.setItem(itemClone, player);
                System.out.println("using delayed");
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        slot.setItem(itemClone, player);
                    }
                }.runTaskLater(RpgU.getInstance(), 10);
            } else {
                slot.setItem(itemClone, player);
            }
        }

        @Override
        public void onLoadToCrossbow(@NotNull CustomItem customItem, @NotNull UniversalInventorySlot slot, @NotNull EntityLoadCrossbowEvent event) {
            ItemStack quiver = slot.getItem(event.getEntity());
            if (quiver == null) throw new RuntimeException("Ебать как ты выбил это еблан!!!! Это говнокод");
            BundleContents data = quiver.getData(DataComponentTypes.BUNDLE_CONTENTS);
            if (data == null || data.contents().isEmpty()) return;
            List<ItemStack> contents = new ArrayList<>(data.contents());
            ItemStack first = contents.getFirst().clone();

            first.add(-1);
            if (first.getAmount() == 0) {
                contents.removeFirst();
                quiver.unsetData(DataComponentTypes.BUNDLE_CONTENTS);
                slot.setItem(quiver.withType(Quiver.DEFAULT_MATERIAL), event.getEntity());
            } else {
                contents.set(0, first);
            }
            quiver.setData(DataComponentTypes.BUNDLE_CONTENTS, BundleContents.bundleContents(contents));

            List<Component> lines = generateLore(Objects.requireNonNull(customItem.getItem().lore()), contents);
            quiver.setData(DataComponentTypes.LORE, ItemLore.lore(lines));
            first.setAmount(1);

            event.setCancelled(true);

            first.setData(DataComponentTypes.ITEM_NAME, Objects.requireNonNull(first.getType().getDefaultData(DataComponentTypes.ITEM_NAME)));
            event.getCrossbow().setData(DataComponentTypes.CHARGED_PROJECTILES, ChargedProjectiles.chargedProjectiles(List.of(first)));
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
                    CustomItem customItem = CustomItem.get(arrow);

                    if (customItem == null || !(customItem.getComponents().has(ComponentTypes.ARROW_ITEM))) {
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

                    List<Component> lines = generateLore(Objects.requireNonNull(item.getItem().lore()),
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

                    List<Component> lines = generateLore(Objects.requireNonNull(item.getItem().lore()), contents);
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

                    if (customItem == null || !(customItem.getComponents().has(ComponentTypes.ARROW_ITEM))) {
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

                    List<Component> lines = generateLore(Objects.requireNonNull(item.getItem().lore()),
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

                    List<Component> lines = generateLore(Objects.requireNonNull(item.getItem().lore()), contents);
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

    public static class QuiverRightClickableItem extends ConstructableActiveAbilityComponent<PlayerInteractEvent> {
        @Override
        public void onRightClick(@NotNull CustomItem customItem, @NotNull PlayerInteractEvent event) {
            event.setCancelled(true);
            activate(customItem, event.getPlayer(),
                    new Either<>(new BaseUniversalSlot(Objects.requireNonNull(event.getHand())), null), event);
        }

        @Override
        public @NotNull ActionResult action(@NotNull CustomItem customItem, @NotNull LivingEntity livingEntity, @NotNull Either<UniversalInventorySlot, SingleSlot> slot, @NotNull PlayerInteractEvent event) {
            ItemStack item = event.getItem();
            if (item == null) return ActionResult.NO_COOLDOWN;
            BundleContents data = item.getData(DataComponentTypes.BUNDLE_CONTENTS);
            if (data == null) return ActionResult.NO_COOLDOWN;
            List<ItemStack> contents = new ArrayList<>(data.contents());
            if (contents.isEmpty()) return ActionResult.NO_COOLDOWN;

            ItemStack firstItem = contents.removeFirst();
            contents.add(firstItem);
            item.setData(DataComponentTypes.BUNDLE_CONTENTS, BundleContents.bundleContents(contents));

            List<Component> lines = generateLore(Objects.requireNonNull(customItem.getItem().lore()),contents);
            item.setData(DataComponentTypes.LORE, ItemLore.lore(lines));

            ItemStack stack = item.withType(contents.getFirst().getType());
            stack.unsetData(DataComponentTypes.POTION_CONTENTS);
            stack.unsetData(DataComponentTypes.FIREWORKS);
            new BaseUniversalSlot(Objects.requireNonNull(event.getHand())).setItem(stack, event.getPlayer());

            return ActionResult.FULL_COOLDOWN;
        }
    }
}