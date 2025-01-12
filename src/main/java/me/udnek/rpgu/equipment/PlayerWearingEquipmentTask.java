package me.udnek.rpgu.equipment;

import me.udnek.itemscoreu.util.TickingTask;
import me.udnek.rpgu.component.ComponentTypes;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PlayerWearingEquipmentTask extends TickingTask {

    public static final int DELAY = 1;

    private static PlayerWearingEquipmentTask instance;

    private PlayerWearingEquipmentTask(){}

    public static @NotNull PlayerWearingEquipmentTask getInstance() {
        if (instance == null) instance = new PlayerWearingEquipmentTask();
        return instance;
    }

    public void run(){
        for (Player player : Bukkit.getOnlinePlayers()) {
            PlayerEquipment.get(player).getEquipment((slot, customItem) -> {
                customItem.getComponents().getOrException(ComponentTypes.EQUIPPABLE_ITEM).tickBeingEquipped(customItem, player, slot);
            });
        }
    }

    @Override
    public int getDelay() {
        return DELAY;
    }
}
