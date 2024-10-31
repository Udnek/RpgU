package me.udnek.rpgu.command;

import me.udnek.rpgu.mechanic.damaging.DamageListener;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class CustomDamageSystemCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        DamageListener.CUSTOM_DAMAGE_SYSTEM = !DamageListener.CUSTOM_DAMAGE_SYSTEM;
        commandSender.sendMessage("Custom damage system is now: " + DamageListener.CUSTOM_DAMAGE_SYSTEM);
        return true;
    }
}
