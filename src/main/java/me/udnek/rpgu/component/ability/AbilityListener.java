package me.udnek.rpgu.component.ability;

import com.destroystokyo.paper.event.player.PlayerLaunchProjectileEvent;
import com.destroystokyo.paper.event.player.PlayerReadyArrowEvent;
import io.papermc.paper.event.entity.EntityAttemptSmashAttackEvent;
import io.papermc.paper.event.entity.EntityLoadCrossbowEvent;
import io.papermc.paper.event.player.PlayerShieldDisableEvent;
import me.udnek.coreu.custom.component.CustomComponent;
import me.udnek.coreu.custom.component.CustomComponentType;
import me.udnek.coreu.custom.equipment.PlayerEquipmentManager;
import me.udnek.coreu.custom.equipment.universal.BaseUniversalSlot;
import me.udnek.coreu.custom.equipment.universal.UniversalInventorySlot;
import me.udnek.coreu.custom.item.CustomItem;
import me.udnek.coreu.rpgu.component.RPGUActiveItem;
import me.udnek.coreu.rpgu.component.RPGUComponents;
import me.udnek.coreu.util.SelfRegisteringListener;
import me.udnek.rpgu.item.equipment.quiver.QuiverShootAbility;
import me.udnek.rpgu.mechanic.damaging.DamageEvent;
import me.udnek.rpgu.mechanic.damaging.DamageInstance;
import org.apache.logging.log4j.util.TriConsumer;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Trident;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityResurrectEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.EntityToggleGlideEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.BiConsumer;

public class AbilityListener extends SelfRegisteringListener implements Listener {
    public AbilityListener(@NotNull Plugin plugin) {
        super(plugin);
    }

    private <T extends CustomComponent<CustomItem>> void joinConsumer(@NotNull CustomComponentType<CustomItem, T> type, @NotNull LivingEntity livingEntity,
                                                                      @NotNull TriConsumer<T, CustomItem, UniversalInventorySlot> cons) {
        if (livingEntity instanceof Player player) {
            PlayerEquipmentManager.getInstance().getData(player).getEquipment(
                    (slot, customItem) ->
                            cons.accept(customItem.getComponents().getOrDefault(type), customItem, slot)
                    );
        } else {
            BiConsumer<BaseUniversalSlot, ItemStack> consumer = (slot, itemStack) ->
                    CustomItem.consumeIfCustom(itemStack, customItem ->
                            cons.accept(customItem.getComponents().getOrDefault(type), customItem, slot));

            UniversalInventorySlot.iterateThroughNotEmpty(consumer, livingEntity);
        }
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
    public void onPlayerLaunchProjectile(PlayerLaunchProjectileEvent event) {
        CustomItem.consumeIfCustom(event.getItemStack(), item -> {
            RPGUActiveItem rpguActiveItem = item.getComponents().getOrDefault(RPGUComponents.ACTIVE_ABILITY_ITEM);
            for (var trigger : rpguActiveItem.getComponents().getAllTyped(RPGUActiveTriggerableAbility.class)) {
                if (event.getProjectile() instanceof Trident) trigger.onLaunchTrident(item, event);
            }
        });
    }

    @EventHandler
    public void onEntityAttemptSmashAttack(EntityAttemptSmashAttackEvent event) {
        if (!(event.getEntity() instanceof LivingEntity)) return;
        CustomItem.consumeIfCustom(event.getWeapon(), item -> {
            RPGUActiveItem rpguActiveItem = item.getComponents().getOrDefault(RPGUComponents.ACTIVE_ABILITY_ITEM);
            for (var trigger : rpguActiveItem.getComponents().getAllTyped(RPGUActiveTriggerableAbility.class)) {
                trigger.onSmashAttack(item, event);
            }
        });
    }

    @EventHandler
    public void onEntityShootBowEvent(EntityShootBowEvent event) {
        LivingEntity entity = event.getEntity();
        joinConsumer(RPGUComponents.PASSIVE_ABILITY_ITEM, entity,
                (passiveItem, customItem, slot) -> {
                    for (var trigger : passiveItem.getComponents().getAllTyped(RPGUPassiveTriggerableAbility.class)) {
                        trigger.onShootBow(customItem, slot, event);
                    }
                });

        CustomItem.consumeIfCustom(event.getBow(), item -> {
            RPGUActiveItem rpguActiveItem = item.getComponents().getOrDefault(RPGUComponents.ACTIVE_ABILITY_ITEM);
            for (var trigger : rpguActiveItem.getComponents().getAllTyped(RPGUActiveTriggerableAbility.class)) {
                trigger.onShoot(item, event);
            }
        });
    }

    @EventHandler
    public void onPlayerItemConsume(PlayerShieldDisableEvent event) {
        if (!(event.getDamager() instanceof LivingEntity livingEntity) || livingEntity.getEquipment() != null) return;
        CustomItem.consumeIfCustom(livingEntity.getEquipment().getItem(EquipmentSlot.HAND), item -> {
            RPGUActiveItem rpguActiveItem = item.getComponents().getOrDefault(RPGUComponents.ACTIVE_ABILITY_ITEM);
            for (var trigger : rpguActiveItem.getComponents().getAllTyped(RPGUActiveTriggerableAbility.class)) {
                trigger.onShieldBreak(item, event);
            }
        });
    }

    @EventHandler
    public void onPlayerReadyArrow(PlayerReadyArrowEvent event) {
        Player player = event.getPlayer();
        joinConsumer(RPGUComponents.PASSIVE_ABILITY_ITEM, player,
                (passiveItem, customItem, slot) -> {
                    QuiverShootAbility quiverShootAbility = passiveItem.getComponents().get(Abilities.QUIVER_SHOOT);
                    if (quiverShootAbility == null) return;
                    quiverShootAbility.onChooseArrow(customItem, slot, event);
                });
    }

    @EventHandler
    public void onEntityLoadCrossbowEvent(EntityLoadCrossbowEvent event) {
        LivingEntity entity = event.getEntity();
        joinConsumer(RPGUComponents.PASSIVE_ABILITY_ITEM, entity,
                (passiveItem, customItem, slot) -> {
                    QuiverShootAbility quiverShootAbility = passiveItem.getComponents().get(Abilities.QUIVER_SHOOT);
                    if (quiverShootAbility == null) return;
                    quiverShootAbility.onLoadToCrossbow(customItem, slot, event);
                });
    }

    @EventHandler
    public void playerDeath(PlayerDeathEvent event){
        Player entity = event.getEntity();
        joinConsumer(RPGUComponents.PASSIVE_ABILITY_ITEM, entity,
                (passiveItem, customItem, slot) -> {
                    for (var trigger : passiveItem.getComponents().getAllTyped(RPGUPassiveTriggerableAbility.class)) {
                        trigger.onDeath(customItem, slot, event);
                    }
                });
    }

    @EventHandler
    public void entityResurrect(EntityResurrectEvent event){
        AtomicBoolean activatedBefore = new AtomicBoolean(false);
        LivingEntity entity = event.getEntity();
        joinConsumer(RPGUComponents.PASSIVE_ABILITY_ITEM, entity,
                (passive, customItem,  slot) -> {
                    if (activatedBefore.get()) return;
                    for (var trigger : passive.getComponents().getAllTyped(RPGUPassiveTriggerableAbility.class)) {
                        trigger.onResurrect(customItem, slot, activatedBefore.get(), event);
                    }
                    if (!(event.isCancelled())) activatedBefore.set(true);
                });
        if (!activatedBefore.get()) event.setCancelled(true);
    }

    @EventHandler
    public void onDamage(DamageEvent event){
        DamageInstance damageInstance = event.getDamageInstance();

        if (damageInstance.getDamager() instanceof LivingEntity damager){
            joinConsumer(RPGUComponents.PASSIVE_ABILITY_ITEM, damager,
                    (passive, customItem, slot) -> {
                        for (var trigger : passive.getComponents().getAllTyped(RPGUPassiveTriggerableAbility.class)) {
                            trigger.onDamageDealt(customItem, slot, event);
                        }
                    });
        }

         if (damageInstance.getVictim() instanceof LivingEntity victim) {
             joinConsumer(RPGUComponents.PASSIVE_ABILITY_ITEM, victim,
                     (passive, customItem, slot) -> {
                         for (var trigger : passive.getComponents().getAllTyped(RPGUPassiveTriggerableAbility.class)) {
                             trigger.onDamageReceived(customItem, slot, event);
                         }
                     });
        }
    }

    @EventHandler
    public void onEntityToggleGlide(EntityToggleGlideEvent event){
        if (!(event.getEntity() instanceof LivingEntity livingEntity)) return;
        joinConsumer(RPGUComponents.PASSIVE_ABILITY_ITEM, livingEntity,
                (passive, customItem, slot) -> {
                    for (var trigger : passive.getComponents().getAllTyped(RPGUPassiveTriggerableAbility.class)) {
                        trigger.onToggleGlide(customItem, slot, event);
                    }
                });
    }
}
