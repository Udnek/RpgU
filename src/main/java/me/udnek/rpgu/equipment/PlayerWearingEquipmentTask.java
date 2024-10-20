package me.udnek.rpgu.equipment;

import me.udnek.itemscoreu.util.TickingTask;
import me.udnek.rpgu.component.ComponentTypes;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PlayerWearingEquipmentTask extends TickingTask {

    public static final int DELAY = 10;

    public void run(){
        for (Player player : Bukkit.getOnlinePlayers()) {
            PlayerEquipment.get(player).getEquipment((slot, customItem) -> {
                customItem.getComponent(ComponentTypes.EQUIPPABLE_ITEM).tickBeingEquipped(customItem, player, slot);
            });
        }
    }

    @Override
    public int getDelay() {
        return DELAY;
    }
}
