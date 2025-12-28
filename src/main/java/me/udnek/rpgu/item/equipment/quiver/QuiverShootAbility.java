package me.udnek.rpgu.item.equipment.quiver;

import com.destroystokyo.paper.event.player.PlayerReadyArrowEvent;
import io.papermc.paper.datacomponent.DataComponentTypes;
import io.papermc.paper.datacomponent.item.BundleContents;
import io.papermc.paper.datacomponent.item.ChargedProjectiles;
import io.papermc.paper.datacomponent.item.ItemLore;
import io.papermc.paper.event.entity.EntityLoadCrossbowEvent;
import me.udnek.coreu.custom.component.CustomComponent;
import me.udnek.coreu.custom.component.CustomComponentType;
import me.udnek.coreu.custom.equipment.slot.CustomEquipmentSlot;
import me.udnek.coreu.custom.equipment.universal.BaseUniversalSlot;
import me.udnek.coreu.custom.equipment.universal.UniversalInventorySlot;
import me.udnek.coreu.custom.item.CustomItem;
import me.udnek.coreu.rpgu.component.RPGUComponents;
import me.udnek.coreu.rpgu.component.RPGUPassiveItem;
import me.udnek.coreu.rpgu.component.ability.RPGUItemAbstractAbility;
import me.udnek.coreu.rpgu.component.ability.passive.RPGUConstructablePassiveAbility;
import me.udnek.coreu.rpgu.component.ability.property.AttributeBasedProperty;
import me.udnek.rpgu.RpgU;
import me.udnek.rpgu.component.Components;
import me.udnek.rpgu.component.ability.Abilities;
import me.udnek.rpgu.component.ability.RPGUPassiveTriggerableAbility;
import me.udnek.rpgu.component.instance.ArrowComponent;
import net.kyori.adventure.text.Component;
import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.Material;
import org.bukkit.entity.AbstractArrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class QuiverShootAbility extends RPGUConstructablePassiveAbility<EntityShootBowEvent> implements RPGUPassiveTriggerableAbility<EntityShootBowEvent> {
    public static final QuiverShootAbility DEFAULT = new QuiverShootAbility();

    private QuiverShootAbility() {
        //нужен когда 2 колчана в инвенторе, чтобы активировался только один
        getComponents().set(new AttributeBasedProperty(1, RPGUComponents.ABILITY_COOLDOWN_TIME));
    }

    @Override
    public @NotNull CustomEquipmentSlot getSlot() {
        return CustomEquipmentSlot.DUMB_INVENTORY;
    }

    @Override
    public void tick(@NotNull CustomItem customItem, @NotNull Player player, @NotNull BaseUniversalSlot baseUniversalSlot, int i) {}

    @Override
    protected @NotNull RPGUItemAbstractAbility.ActionResult action(@NotNull CustomItem customItem, @NotNull LivingEntity livingEntity, @NotNull UniversalInventorySlot slot, @NotNull EntityShootBowEvent event) {
        ItemStack item = slot.getItem(livingEntity);
        //System.out.println("Shoot item: " + CustomItem.get(slot.getItem(livingEntity)).getId());
        //System.out.println("Shoot bow: " + event.getBow().getType());
        if (item == null) throw new RuntimeException("Ебать как ты выбил это еблан!!!! Это говнокод");
        if (!item.isSimilar(event.getConsumable())) return RPGUItemAbstractAbility.ActionResult.NO_COOLDOWN;
        BundleContents data = item.getData(DataComponentTypes.BUNDLE_CONTENTS);
        if (data == null) return RPGUItemAbstractAbility.ActionResult.NO_COOLDOWN;
        List<ItemStack> contents = new ArrayList<>(data.contents());
        if (contents.isEmpty()) return RPGUItemAbstractAbility.ActionResult.NO_COOLDOWN;

        ItemStack arrowStack = contents.getFirst();
        CustomItem arrowCustom = Objects.requireNonNull(CustomItem.get(arrowStack));
        arrowCustom.getComponents().getOrDefault(Components.ARROW_ITEM).onBeingShoot(arrowCustom, arrowStack, event);

        if (Objects.requireNonNull(event.getBow()).getType() == Material.BOW) {
            arrowStack.add(-1);
            if (arrowStack.getAmount() == 0) {
                contents.removeFirst();
                item.unsetData(DataComponentTypes.BUNDLE_CONTENTS);
                slot.setItem(item.withType(Quiver.DEFAULT_MATERIAL), livingEntity);
            } else {
                contents.set(0, arrowStack);
            }
            item.setData(DataComponentTypes.BUNDLE_CONTENTS, BundleContents.bundleContents(contents));

            List<Component> lines = Quiver.generateLore(Objects.requireNonNull(customItem.getItem().lore()), contents);
            item.setData(DataComponentTypes.LORE, ItemLore.lore(lines));
        }

        AbstractArrow projectile = (AbstractArrow) event.getProjectile();
        arrowStack.setAmount(1);
        projectile.setItemStack(arrowStack);

        return RPGUItemAbstractAbility.ActionResult.FULL_COOLDOWN;
    }

    public void onChooseArrow(@NotNull CustomItem customItem, @NotNull UniversalInventorySlot slot, @NotNull PlayerReadyArrowEvent event) {
        //System.out.println("called: " + slot + " item: " + (slot.getItem(event.getPlayer()) == null ? "null" : CustomItem.get(slot.getItem(event.getPlayer())).getId()));
        Player player = event.getPlayer();
        ItemStack item = slot.getItem(player);
        if (item == null) throw new RuntimeException("Ебать как ты выбил это еблан!!!! Это говнокод");
        BundleContents data = item.getData(DataComponentTypes.BUNDLE_CONTENTS);
        if (data == null || data.contents().isEmpty()) return;
        CustomItem arrow = CustomItem.get(data.contents().getFirst());
        if (arrow == null) throw new RuntimeException("Quiver onChooseArrow arrow is null");
        if (arrow.getComponents().getOrDefault(Components.ARROW_ITEM).onChooseArrow(arrow, event) == ArrowComponent.ChoseArrowResult.DENY) {
            return;
        }
        if (!customItem.isThisItem(event.getArrow())) {
            event.setCancelled(true);
            return;
        }
        ItemStack itemClone = item.clone();
        if (slot.equals(player, new BaseUniversalSlot(40)) || slot.equals(player, new BaseUniversalSlot(player.getInventory().getHeldItemSlot()))){
            slot.setItem(itemClone, player);
            //System.out.println("using delayed");
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

        List<Component> lines = Quiver.generateLore(Objects.requireNonNull(customItem.getItem().lore()), contents);
        quiver.setData(DataComponentTypes.LORE, ItemLore.lore(lines));
        first.setAmount(1);

        event.setCancelled(true);

        first.setData(DataComponentTypes.ITEM_NAME, Objects.requireNonNull(first.getType().getDefaultData(DataComponentTypes.ITEM_NAME)));
        event.getCrossbow().setData(DataComponentTypes.CHARGED_PROJECTILES, ChargedProjectiles.chargedProjectiles(List.of(first)));
    }

    @Override
    public void onShootBow(@NotNull CustomItem customItem, @NotNull UniversalInventorySlot slot, @NotNull EntityShootBowEvent event) {
        activate(customItem, event.getEntity(), slot, event);
    }

    @Override
    public @Nullable Pair<List<String>, List<String>> getEngAndRuDescription() {
        return null;
    }

    @Override
    public @NotNull CustomComponentType<? super RPGUPassiveItem, ? extends CustomComponent<? super RPGUPassiveItem>> getType() {
        return Abilities.QUIVER_SHOOT;
    }
}
