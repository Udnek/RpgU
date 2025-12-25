package me.udnek.rpgu.command;

import me.udnek.rpgu.equipment.PlayerEquipment;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class DebugEquipmentCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] strings) {

        if (!(commandSender instanceof Player player)) return false;

        player.sendMessage("");
        PlayerEquipment.EquipmentConsumer consumer = (slot, custom.Item) -> player.sendMessage(slot.getId()
                + ": " + custom.Item.getId());
        PlayerEquipment.get(player).getEquipment(consumer);

        return true;
    }
}
