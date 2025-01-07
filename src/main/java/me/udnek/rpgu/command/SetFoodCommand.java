package me.udnek.rpgu.command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SetFoodCommand implements CommandExecutor  {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (strings.length != 2) {
            Commands.sendError(commandSender, "The command has two arguments");
            return true;
        }
        Player player = Bukkit.getPlayerExact(strings[0]);
        if (player == null) {
            Commands.sendError(commandSender,"There is no such player");
            return true;
        }
        float food;
        try {food = Float.parseFloat(strings[1]);}
        catch (NumberFormatException e){
            Commands.sendError(commandSender, "Second argument must be a number");
            return true;
        }
        if (food > 40 || food < 0){
            Commands.sendError(commandSender,"Accepts a number from 0 to 40");
            return true;
        }

        if (food > 20){
            player.setFoodLevel(20);
            player.setSaturation(food - 20);
            return true;
        }

        player.setFoodLevel((int) food);
        return true;
    }
}