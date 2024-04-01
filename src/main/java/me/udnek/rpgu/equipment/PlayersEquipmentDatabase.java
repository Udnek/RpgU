package me.udnek.rpgu.equipment;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class PlayersEquipmentDatabase {

    private static final HashMap<String, PlayerEquipment> playersData = new HashMap<>();

    public static @NotNull PlayerEquipment get(Player player){
        String uuid = player.getUniqueId().toString();
        if (playersData.containsKey(uuid)){
            return playersData.get(uuid);
        }
        PlayerEquipment playerEquipment = new PlayerEquipment();
        playersData.put(uuid, playerEquipment);
        return playerEquipment;
    }
}
