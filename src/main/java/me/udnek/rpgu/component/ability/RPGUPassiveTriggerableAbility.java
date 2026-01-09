package me.udnek.rpgu.component.ability;

import me.udnek.coreu.custom.equipment.universal.UniversalInventorySlot;
import me.udnek.coreu.custom.item.CustomItem;
import me.udnek.coreu.rpgu.component.ability.passive.RPGUItemPassiveAbility;
import me.udnek.rpgu.mechanic.damaging.DamageEvent;
import org.bukkit.event.entity.EntityResurrectEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.EntityToggleGlideEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.jetbrains.annotations.NotNull;

public interface RPGUPassiveTriggerableAbility<Context> extends RPGUItemPassiveAbility<Context> {

    default void onShootBow(@NotNull CustomItem customItem, @NotNull UniversalInventorySlot slot, @NotNull EntityShootBowEvent event) {}
    default void onDamageReceived(@NotNull CustomItem customItem, @NotNull UniversalInventorySlot slot, @NotNull DamageEvent event) {}
    default void onDamageDealt(@NotNull CustomItem customItem, @NotNull UniversalInventorySlot slot, @NotNull DamageEvent event) {}
    default void onToggleGlide(@NotNull CustomItem customItem, @NotNull UniversalInventorySlot slot, @NotNull EntityToggleGlideEvent event) {}
    default void onDeath(@NotNull CustomItem customItem, @NotNull UniversalInventorySlot slot, @NotNull PlayerDeathEvent event) {}
    default void onResurrect(@NotNull CustomItem customItem, @NotNull UniversalInventorySlot slot, boolean activatedBefore, @NotNull EntityResurrectEvent event) {}
}
