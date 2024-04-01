package me.udnek.rpgu.equipment;

import me.udnek.itemscoreu.utils.TickingTask;
import me.udnek.rpgu.item.abstracts.EquippableItem;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

public class PlayerWearingEquipmentTask extends TickingTask {

    public static final int DELAY = 15;

    public void run(){
        for (Player player : Bukkit.getOnlinePlayers()) {
            for (EquippableItem equippableItem : PlayersEquipmentDatabase.get(player).getFullEquipment()) {
                if (equippableItem != null) equippableItem.onWhileBeingEquipped(player);
            }
        }
    }

    @Override
    public int getDelay() {
        return DELAY;
    }
}
