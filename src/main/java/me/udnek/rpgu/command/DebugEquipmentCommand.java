package me.udnek.rpgu.command;

import me.udnek.itemscoreu.customattribute.equipmentslot.CustomEquipmentSlot;
import me.udnek.rpgu.equipment.Equippable;
import me.udnek.rpgu.equipment.PlayerEquipment;
import me.udnek.rpgu.equipment.PlayerEquipmentDatabase;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Set;

public class DebugEquipmentCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        if (!(commandSender instanceof Player player)) return false;

        player.sendMessage("");
        Set<Map.Entry<PlayerEquipment.Slot, Equippable>> entries = PlayerEquipmentDatabase.get(player).getFullEquipment().entrySet();
        if (entries.isEmpty()){
            player.sendMessage("equipment is empty!");
            return true;
        }
        for (Map.Entry<PlayerEquipment.Slot, Equippable> entry : entries) {
            CustomEquipmentSlot equipmentSlot = entry.getKey().equipmentSlot;
            Equippable equippable = entry.getValue();
            player.sendMessage(equipmentSlot.getClass().getSimpleName() + ": " + equippable.getClass().getSimpleName());
        }





        return true;
    }
}
