package me.udnek.rpgu.equipment;

import me.udnek.itemscoreu.customattribute.equipmentslot.CustomEquipmentSlot;
import me.udnek.itemscoreu.utils.TickingTask;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Map;

public class PlayerWearingEquipmentTask extends TickingTask {

    public static final int DELAY = 10;

    public void run(){
        for (Player player : Bukkit.getOnlinePlayers()) {
            for (Map.Entry<PlayerEquipment.Slot, Equippable> entry : PlayerEquipmentDatabase.get(player).getFullEquipment().entrySet()) {
                Equippable equippable = entry.getValue();
                CustomEquipmentSlot equipmentSlot = entry.getKey().equipmentSlot;
                equippable.tickBeingEquipped(player, equipmentSlot);
            }
        }
    }

    @Override
    public int getDelay() {
        return DELAY;
    }
}
