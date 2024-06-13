package me.udnek.rpgu.equipment;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class PlayerEquipmentDatabase {

    private static final Map<Player, PlayerEquipment> playersData = new HashMap<>();

    public static @NotNull PlayerEquipment get(Player player){
        if (playersData.containsKey(player)){
            return playersData.get(player);
        }
        PlayerEquipment playerEquipment = new PlayerEquipment();
        playersData.put(player, playerEquipment);
        return playerEquipment;
    }
}
