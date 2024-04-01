package me.udnek.rpgu.item.abstracts;

import me.udnek.rpgu.damaging.DamageEvent;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public interface EquippableItem{

    default void onBeingEquipped(Player player, ItemStack itemStack){}
    default void onBeingUnequipped(Player player, ItemStack itemStack){}

    default void onWhileBeingEquipped(Player player){}
    default void onPlayerAttacksWhenEquipped(Player player, DamageEvent event){}

    default void onPlayerReceivesDamageWhenEquipped(Player player, DamageEvent event){}


}
