package me.udnek.rpgu.command;

import me.udnek.itemscoreu.customequipmentslot.SingleSlot;
import me.udnek.itemscoreu.customitem.CustomItem;
import me.udnek.rpgu.equipment.PlayerEquipment;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class DebugEquipmentCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        if (!(commandSender instanceof Player player)) return false;

        player.sendMessage("");
        PlayerEquipment.EquipmentConsumer consumer = new PlayerEquipment.EquipmentConsumer() {
            @Override
            public void accept(@NotNull SingleSlot slot, @NotNull CustomItem customItem) {
                player.sendMessage(slot.getId() + ": " + customItem.getId());
            }
        };
        PlayerEquipment.get(player).getEquipment(consumer);

        return true;
    }
}
