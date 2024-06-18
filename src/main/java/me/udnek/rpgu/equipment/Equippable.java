package me.udnek.rpgu.equipment;

import me.udnek.itemscoreu.customattribute.equipmentslot.CustomEquipmentSlot;
import me.udnek.rpgu.damaging.DamageEvent;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public interface Equippable {
    default void onEquipped(Player player, CustomEquipmentSlot slot, ItemStack itemStack){}
    default void onUnequipped(Player player, CustomEquipmentSlot slot, ItemStack itemStack){}
    default void tickBeingEquipped(Player player, CustomEquipmentSlot slot){}
    default void onPlayerAttacksWhenEquipped(Player player, CustomEquipmentSlot slot, DamageEvent event){}
    default void onPlayerReceivesDamageWhenEquipped(Player player, CustomEquipmentSlot slot, DamageEvent event){}
    default List<Component> getHudImages(){return null;}

}
