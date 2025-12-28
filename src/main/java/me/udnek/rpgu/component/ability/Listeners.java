package me.udnek.rpgu.component.ability;

import com.destroystokyo.paper.event.player.PlayerReadyArrowEvent;
import io.papermc.paper.event.entity.EntityLoadCrossbowEvent;
import me.udnek.coreu.custom.equipment.universal.BaseUniversalSlot;
import me.udnek.coreu.custom.equipment.universal.UniversalInventorySlot;
import me.udnek.coreu.custom.item.CustomItem;
import me.udnek.coreu.rpgu.component.RPGUActiveItem;
import me.udnek.coreu.rpgu.component.RPGUComponents;
import me.udnek.coreu.rpgu.component.RPGUPassiveItem;
import me.udnek.coreu.util.SelfRegisteringListener;
import me.udnek.rpgu.mechanic.damaging.DamageEvent;
import me.udnek.rpgu.mechanic.damaging.DamageInstance;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityResurrectEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.EntityToggleGlideEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.BiConsumer;

public class Listeners extends SelfRegisteringListener implements Listener {
    public Listeners(@NotNull Plugin plugin) {
        super(plugin);
    }


    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        CustomItem.consumeIfCustom(event.getItem(), item -> {
            if (event.getAction().isRightClick()) {
                RPGUActiveItem rpguActiveItem = item.getComponents().getOrDefault(RPGUComponents.ACTIVE_ABILITY_ITEM);
                for (var trigger : rpguActiveItem.getComponents().getAllTyped(RPGUActiveTriggerableAbility.class)) {
                    trigger.onRightClick(item, event);
                }
            }
        });
    }

    @EventHandler
    public void onPlayerItemConsume(PlayerItemConsumeEvent event) {
        CustomItem.consumeIfCustom(event.getItem(), item -> {
            RPGUActiveItem rpguActiveItem = item.getComponents().getOrDefault(RPGUComponents.ACTIVE_ABILITY_ITEM);
            for (var trigger : rpguActiveItem.getComponents().getAllTyped(RPGUActiveTriggerableAbility.class)) {
                trigger.onConsume(item, event);
            }
        });
    }

    @EventHandler
    public void onEntityShootBowEvent(EntityShootBowEvent event) {
        BiConsumer<BaseUniversalSlot, ItemStack> consumer = (slot, itemStack) ->
                CustomItem.consumeIfCustom(itemStack, customItem -> {
                    RPGUPassiveItem rpguPassiveItem = customItem.getComponents().getOrDefault(RPGUComponents.PASSIVE_ABILITY_ITEM);
                    for (var trigger : rpguPassiveItem.getComponents().getAllTyped(RPGUPassiveTriggerableAbility.class)) {
                        trigger.onShootBow(customItem, slot, event);
                    }
                });

        UniversalInventorySlot.iterateThroughNotEmpty(consumer, event.getEntity());
    }

    @EventHandler
    public void onPlayerReadyArrow(PlayerReadyArrowEvent event) {
        BiConsumer<BaseUniversalSlot, ItemStack> consumer = (slot, itemStack) ->
                CustomItem.consumeIfCustom(itemStack, customItem ->
                        customItem.getComponents().getOrDefault(RPGUComponents.PASSIVE_ABILITY_ITEM).getComponents().getOrDefault(Abilities.QUIVER_SHOOT)
                                .onChooseArrow(customItem, slot, event));

        UniversalInventorySlot.iterateThroughNotEmpty(consumer, event.getPlayer());
    }

    @EventHandler
    public void onEntityLoadCrossbowEvent(EntityLoadCrossbowEvent event) {
        BiConsumer<BaseUniversalSlot, ItemStack> consumer = (slot, itemStack) ->
                CustomItem.consumeIfCustom(itemStack, customItem ->
                        customItem.getComponents().getOrDefault(RPGUComponents.PASSIVE_ABILITY_ITEM).getComponents().getOrDefault(Abilities.QUIVER_SHOOT)
                                .onLoadToCrossbow(customItem, slot, event));

        UniversalInventorySlot.iterateThroughNotEmpty(consumer, event.getEntity());
    }

    @EventHandler
    public void playerDeath(PlayerDeathEvent event){
        BiConsumer<BaseUniversalSlot, ItemStack> consumer = (slot, itemStack) ->
                CustomItem.consumeIfCustom(itemStack, customItem -> customItem.getComponents().getOrDefault(RPGUComponents.PASSIVE_ABILITY_ITEM)
                        .getComponents().getOrDefault(Abilities.TOTEM_OF_SAVING).onDeath(customItem, slot, event));

        UniversalInventorySlot.iterateThroughNotEmpty(consumer, event.getEntity());
    }

    @EventHandler
    public void entityResurrect(EntityResurrectEvent event){
        AtomicBoolean activatedBefore = new AtomicBoolean(false);
        BiConsumer<BaseUniversalSlot, ItemStack> consumer = (slot, itemStack) ->  {
            CustomItem.consumeIfCustom(itemStack, customItem ->
                     customItem.getComponents().getOrDefault(RPGUComponents.PASSIVE_ABILITY_ITEM).getComponents().getOrDefault(Abilities.DEATH_PROTECTION)
                             .onResurrect(customItem, slot, activatedBefore.get(), event));

            if (!(event.isCancelled())) activatedBefore.set(true);
        };

        UniversalInventorySlot.iterateThroughNotEmpty(consumer, event.getEntity());
    }

    @EventHandler
    public void onDamage(DamageEvent event){
        DamageInstance damageInstance = event.getDamageInstance();

        if (damageInstance.getDamager() instanceof LivingEntity damager) {
            BiConsumer<BaseUniversalSlot, ItemStack> consumer = (slot, itemStack) ->
                    CustomItem.consumeIfCustom(itemStack, customItem -> {
                        RPGUPassiveItem rpguActiveItem = customItem.getComponents().getOrDefault(RPGUComponents.PASSIVE_ABILITY_ITEM);
                        for (var trigger : rpguActiveItem.getComponents().getAllTyped(RPGUPassiveTriggerableAbility.class)) {
                            trigger.onDamageDealt(customItem, slot, event);
                        }});

            UniversalInventorySlot.iterateThroughNotEmpty(consumer, damager);
        }

        if (damageInstance.getVictim() instanceof LivingEntity victim) {
            BiConsumer<BaseUniversalSlot, ItemStack> consumer = (slot, itemStack) ->
                    CustomItem.consumeIfCustom(itemStack, customItem -> {
                        RPGUPassiveItem rpguActiveItem = customItem.getComponents().getOrDefault(RPGUComponents.PASSIVE_ABILITY_ITEM);
                        for (var trigger : rpguActiveItem.getComponents().getAllTyped(RPGUPassiveTriggerableAbility.class)) {
                            trigger.onDamageReceived(customItem, slot, event);
                        }});

            UniversalInventorySlot.iterateThroughNotEmpty(consumer, victim);
        }
    }

    @EventHandler
    public void onEntityToggleGlide(EntityToggleGlideEvent event){
        if (!(event.getEntity() instanceof LivingEntity livingEntity)) return;
        BiConsumer<BaseUniversalSlot, ItemStack> consumer = (slot, itemStack) ->
                CustomItem.consumeIfCustom(itemStack, customItem ->
                    customItem.getComponents().getOrDefault(RPGUComponents.PASSIVE_ABILITY_ITEM).getComponents().getOrDefault(Abilities.GLIDER)
                            .onToggleGlide(customItem, slot, event));

        UniversalInventorySlot.iterateThroughNotEmpty(consumer, livingEntity);
    }
}
