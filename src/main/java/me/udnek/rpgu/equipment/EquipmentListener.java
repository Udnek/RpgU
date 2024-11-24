package me.udnek.rpgu.equipment;

import io.papermc.paper.event.player.PlayerInventorySlotChangeEvent;
import me.udnek.itemscoreu.customequipmentslot.CustomEquipmentSlot;
import me.udnek.itemscoreu.customequipmentslot.SingleSlot;
import me.udnek.itemscoreu.customitem.CustomItem;
import me.udnek.itemscoreu.util.SelfRegisteringListener;
import me.udnek.rpgu.component.ComponentTypes;
import me.udnek.rpgu.component.EquippableItemComponent;
import me.udnek.rpgu.equipment.slot.EquipmentSlots;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class EquipmentListener extends SelfRegisteringListener {
    public EquipmentListener(JavaPlugin plugin) {
        super(plugin);
    }

    private static boolean isEquippableAtSlot(@NotNull CustomItem customItem, @NotNull CustomEquipmentSlot slot){
        EquippableItemComponent component = customItem.getComponents().get(ComponentTypes.EQUIPPABLE_ITEM);
        if (component == null) return false;
        return component.isAppropriateSlot(slot);
    }

    private static @Nullable CustomItem getIfEquippable(@Nullable ItemStack itemStack, @NotNull CustomEquipmentSlot slot){
        CustomItem customItem = CustomItem.get(itemStack);
        if (customItem == null) return null;
        if (isEquippableAtSlot(customItem, slot)) return customItem;
        return null;
    }
    
    @EventHandler
    public void onHotbarScroll(PlayerItemHeldEvent event){
        Player player = event.getPlayer();
        ItemStack itemStack = player.getInventory().getItem(event.getNewSlot());
        // todo fire equipped and unequipped
        PlayerEquipment.get(player).setItem(getIfEquippable(itemStack, CustomEquipmentSlot.MAIN_HAND), CustomEquipmentSlot.MAIN_HAND);
    }

    @EventHandler
    public void onPlayerEquipsItem(PlayerInventorySlotChangeEvent event){
        SingleSlot equipmentSlot = switch (event.getSlot()){
            case 40 -> CustomEquipmentSlot.OFF_HAND;
            case 9 -> EquipmentSlots.FIRST_ARTIFACT;
            case 10 -> EquipmentSlots.SECOND_ARTIFACT;
            case 11 -> EquipmentSlots.THIRD_ARTIFACT;
            case 39 -> CustomEquipmentSlot.HEAD;
            case 38 -> CustomEquipmentSlot.CHEST;
            case 37 -> CustomEquipmentSlot.LEGS;
            case 36 -> CustomEquipmentSlot.FEET;
            default -> null;
        };
        if (equipmentSlot == null) return;

        if (CustomItem.isSameIds(event.getOldItemStack(), event.getNewItemStack())) return;

        Player player = event.getPlayer();
        ItemStack itemStack;
        CustomItem customItem;

        itemStack = event.getOldItemStack();
        customItem = CustomItem.get(itemStack);
        if (customItem != null){
            PlayerEquipment.get(player).setItem(null, equipmentSlot);
            customItem.getComponents().getOrDefault(ComponentTypes.EQUIPPABLE_ITEM).onUnequipped(customItem, player, equipmentSlot, itemStack);
        }


        itemStack = event.getNewItemStack();
        customItem = CustomItem.get(itemStack);
        if (customItem != null){
            if (isEquippableAtSlot(customItem, equipmentSlot)) PlayerEquipment.get(player).setItem(customItem, equipmentSlot);
            customItem.getComponents().getOrDefault(ComponentTypes.EQUIPPABLE_ITEM).onEquipped(customItem, player, equipmentSlot, itemStack);
        }
    }
}
