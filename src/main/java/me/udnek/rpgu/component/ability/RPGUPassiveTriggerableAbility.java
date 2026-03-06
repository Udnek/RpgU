package me.udnek.rpgu.component.ability;

import me.udnek.coreu.custom.equipment.universal.UniversalInventorySlot;
import me.udnek.coreu.custom.item.CustomItem;
import me.udnek.coreu.rpgu.component.ability.passive.RPGUItemPassiveAbility;
import me.udnek.rpgu.mechanic.damaging.DamageEvent;
import org.bukkit.event.entity.*;
import org.jspecify.annotations.NullMarked;

@NullMarked
public interface RPGUPassiveTriggerableAbility<Context> extends RPGUItemPassiveAbility<Context> {

    default void onShootBow(CustomItem customItem, UniversalInventorySlot slot, EntityShootBowEvent event) {}
    default void onDamageReceived(CustomItem customItem, UniversalInventorySlot slot, DamageEvent event) {}
    default void onDamageDealt(CustomItem customItem, UniversalInventorySlot slot, DamageEvent event) {}
    default void onToggleGlide(CustomItem customItem, UniversalInventorySlot slot, EntityToggleGlideEvent event) {}
    default void onPlayerDeath(CustomItem customItem, UniversalInventorySlot slot, PlayerDeathEvent event) {}
    default void onResurrect(CustomItem customItem, UniversalInventorySlot slot, boolean activatedBefore, EntityResurrectEvent event) {}
    default void onTamedEntityDeath(CustomItem customItem, UniversalInventorySlot slot, EntityDeathEvent event) {}

}