package me.udnek.rpgu.item.abstraction;

import me.udnek.itemscoreu.customattribute.equipmentslot.CustomEquipmentSlot;
import oshi.util.tuples.Pair;

import java.util.HashMap;
import java.util.Map;

public interface ExtraDescribed {
    Map<CustomEquipmentSlot, Pair<Integer, Integer>> getExtraDescription();

    static Map<CustomEquipmentSlot, Pair<Integer, Integer>> getSimple(CustomEquipmentSlot slot, int amount){
        HashMap<CustomEquipmentSlot, Pair<Integer, Integer>> map = new HashMap<>();
        map.put(slot, new Pair<>(0, amount-1));
        return map;
    }
}
