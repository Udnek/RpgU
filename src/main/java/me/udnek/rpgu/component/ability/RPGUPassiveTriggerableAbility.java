package me.udnek.rpgu.component.ability;

import me.udnek.coreu.custom.equipmentslot.universal.UniversalInventorySlot;
import me.udnek.coreu.custom.item.CustomItem;
import me.udnek.coreu.rpgu.component.ability.passive.RPGUItemPassiveAbility;
import me.udnek.rpgu.mechanic.damaging.DamageEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.jetbrains.annotations.NotNull;

public interface RPGUPassiveTriggerableAbility<Context> extends RPGUItemPassiveAbility<Context> {

    default void onShootBow(@NotNull CustomItem customItem, @NotNull UniversalInventorySlot slot, @NotNull EntityShootBowEvent event) {}
    default void onDamageReceived(@NotNull CustomItem customItem, @NotNull UniversalInventorySlot slot, @NotNull DamageEvent event) {}
    default void onDamageDealt(@NotNull CustomItem customItem, @NotNull UniversalInventorySlot slot, @NotNull DamageEvent event) {}
}
