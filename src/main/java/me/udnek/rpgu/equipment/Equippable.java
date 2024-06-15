package me.udnek.rpgu.equipment;

import me.udnek.rpgu.damaging.DamageEvent;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public interface Equippable {
    default void onEquipped(Player player, ItemStack itemStack){}
    default void onUnequipped(Player player, ItemStack itemStack){}
    default void tickBeingEquipped(Player player){}
    default void onPlayerAttacksWhenEquipped(Player player, DamageEvent event){}
    default void onPlayerReceivesDamageWhenEquipped(Player player, DamageEvent event){}

}
