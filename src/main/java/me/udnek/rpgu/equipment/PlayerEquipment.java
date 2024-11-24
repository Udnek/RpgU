package me.udnek.rpgu.equipment;

import me.udnek.itemscoreu.customequipmentslot.CustomEquipmentSlot;
import me.udnek.itemscoreu.customequipmentslot.GroupSlot;
import me.udnek.itemscoreu.customequipmentslot.SingleSlot;
import me.udnek.itemscoreu.customitem.CustomItem;
import me.udnek.rpgu.component.ComponentTypes;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayerEquipment {

    private static final Map<Player, PlayerEquipment> playersData = new HashMap<>();
    private final HashMap<SingleSlot, CustomItem> equipment = new HashMap<>();


    public static @NotNull PlayerEquipment get(Player player){
        if (playersData.containsKey(player)){
            return playersData.get(player);
        }
        PlayerEquipment playerEquipment = new PlayerEquipment();
        playersData.put(player, playerEquipment);
        return playerEquipment;
    }

    public void getEquipment(@NotNull EquipmentConsumer consumer) {
        equipment.forEach(consumer::accept);
    }

    public boolean isEmpty() {return equipment.isEmpty();}
    private void put(@NotNull SingleSlot slot, @Nullable CustomItem customItem){
        if (customItem == null){
            equipment.remove(slot);
            return;
        }
        equipment.put(slot, customItem);
    }
    public void setItem(@Nullable CustomItem customItem, @NotNull SingleSlot equipmentSlot){
        if (
                customItem != null &&
                customItem.getComponents().getOrDefault(ComponentTypes.EQUIPPABLE_ITEM).isAppropriateSlot(equipmentSlot)
        ) {
            put(equipmentSlot, customItem);
            return;
        }

        put(equipmentSlot, null);
    }
    public boolean isEquipped(@NotNull CustomEquipmentSlot slot, @NotNull CustomItem customItem){
        if (!(slot instanceof GroupSlot groupSlot)){
            return equipment.get(slot) == customItem;
        } else {
            List<SingleSlot> slots = new ArrayList<>();
            groupSlot.getAllSubSlots(slots::add);
            return slots.stream().anyMatch(equipmentSlot -> equipment.get(equipmentSlot) == customItem);
        }
    }

    public interface EquipmentConsumer{
        void accept(@NotNull SingleSlot slot, @NotNull CustomItem customItem);
    }
}
