package me.udnek.rpgu.equipment;

import io.papermc.paper.event.player.PlayerInventorySlotChangeEvent;
import me.udnek.itemscoreu.customattribute.equipmentslot.CustomEquipmentSlot;
import me.udnek.itemscoreu.customattribute.equipmentslot.CustomEquipmentSlots;
import me.udnek.itemscoreu.customitem.CustomItem;
import me.udnek.itemscoreu.utils.SelfRegisteringListener;
import me.udnek.rpgu.attribute.equipmentslot.EquipmentSlots;
import me.udnek.rpgu.item.abstraction.ArmorItem;
import me.udnek.rpgu.item.abstraction.ArtifactItem;
import me.udnek.rpgu.item.abstraction.EquippableItem;
import me.udnek.rpgu.item.abstraction.MainHandItem;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class EquipmentListener extends SelfRegisteringListener {
    public EquipmentListener(JavaPlugin plugin) {
        super(plugin);
    }

    private static EquippableItem getEquippableItem(ItemStack itemStack){
        if (CustomItem.get(itemStack) instanceof EquippableItem equippableItem) return equippableItem;
        return null;
    }

    @EventHandler
    public void onHotbarScroll(PlayerItemHeldEvent event){
        Player player = event.getPlayer();
        ItemStack itemStack = player.getInventory().getItem(event.getNewSlot());
        EquippableItem equippableItem = getEquippableItem(itemStack);
        if (equippableItem instanceof MainHandItem mainHandItem){
            PlayerEquipmentDatabase.get(player).setMainHand(mainHandItem);
        } else {
            PlayerEquipmentDatabase.get(player).setMainHand(null);
        }
    }

    @EventHandler
    public void onPlayerEquipsItem(PlayerInventorySlotChangeEvent event){
        int slot = event.getSlot();
        CustomEquipmentSlot equipmentSlot;

        switch (slot){
            case 40:
                equipmentSlot = CustomEquipmentSlots.OFF_HAND;
                break;

            case 9:
            case 10:
            case 11:
                equipmentSlot = EquipmentSlots.ARTIFACT;
                break;

            case 39:
                equipmentSlot = CustomEquipmentSlots.HEAD;
                break;
            case 38:
                equipmentSlot = CustomEquipmentSlots.CHEST;
                break;
            case 37:
                equipmentSlot = CustomEquipmentSlots.LEGS;
                break;
            case 36:
                equipmentSlot = CustomEquipmentSlots.FEET;
                break;
            default:
                return;
        }

        if (CustomItem.isSameIds(event.getOldItemStack(), event.getNewItemStack())) return;

        ItemStack itemStack;
        EquippableItem equippableItem;
        Player player = event.getPlayer();

        itemStack = event.getOldItemStack();
        equippableItem = getEquippableItem(itemStack);
        if (equippableItem != null){
            PlayerEquipmentDatabase.get(player).setEquippableItem(null, equipmentSlot, slot);
            equippableItem.onUnequipped(player, equipmentSlot, itemStack);
        }


        itemStack = event.getNewItemStack();
        equippableItem = getEquippableItem(itemStack);
        if (equippableItem != null){
            equippableItem.onEquipped(player, equipmentSlot, itemStack);
            PlayerEquipmentDatabase.get(player).setEquippableItem(equippableItem, equipmentSlot, slot);
        }
    }
}
