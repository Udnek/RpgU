package me.udnek.rpgu.command;

import me.udnek.rpgu.util.Sounds;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class PlaySoundCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!(commandSender instanceof Player player)) {
            Commands.sendError(commandSender, "Only the player can execute this command");
            return true;
        }
        SoundReason soundReason;
        Player playerTo;
        switch (strings.length){
            case 1 -> soundReason = SoundReason.LOCATION;
            case 2 -> soundReason = SoundReason.TROLL;
            default -> {
                Commands.sendError(commandSender, "Incorrect number of arguments");
                return true;
            }
        }

        Sounds.PlayableSound playableSound = Sounds.getSound(strings[0]);
        if (playableSound == null) {
            Commands.sendError(commandSender, "There is no such sound");
            return true;
        }

        switch (soundReason){
            case SoundReason.LOCATION -> {
                playableSound.play(player.getEyeLocation());
                return true;
            }
            case SoundReason.TROLL -> {
                playerTo = Bukkit.getPlayerExact(strings[1]);
                if (playerTo == null) {
                    Commands.sendError(commandSender,"There is no such player");
                    return true;
                }

                playableSound.play(Objects.requireNonNull(playerTo), player.getEyeLocation());
                return true;
            }
            default -> {
                Commands.sendError(commandSender, "There is no such reason");
                return true;
            }
        }
    }
    public enum SoundReason{
        LOCATION,
        TROLL
    }
}
