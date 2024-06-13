package me.udnek.rpgu.equipment;

import me.udnek.itemscoreu.utils.TickingTask;
import me.udnek.rpgu.item.abstraction.EquippableItem;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PlayerWearingEquipmentTask extends TickingTask {

    public static final int DELAY = 15;

    public void run(){
        for (Player player : Bukkit.getOnlinePlayers()) {
            for (EquippableItem equippableItem : PlayerEquipmentDatabase.get(player).getFullEquipment()) {
                if (equippableItem != null) equippableItem.tickBeingEquipped(player);
            }
        }
    }

    @Override
    public int getDelay() {
        return DELAY;
    }
}
