package me.udnek.rpgu.command;

import me.udnek.rpgu.RpgU;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class Commands {
    public static void declareCommands() {
        RpgU instance = RpgU.getInstance();
        instance.getCommand("debugequipment").setExecutor(new DebugEquipmentCommand());
        instance.getCommand("customdamagesystem").setExecutor(new CustomDamageSystemCommand());
        instance.getCommand("reset_cooldown").setExecutor(new ResetCooldownCommand());
        instance.getCommand("set_food").setExecutor(new SetFoodCommand());
        instance.getCommand("play_sound").setExecutor(new PlaySoundCommand());
    }

    public static void sendError(@NotNull CommandSender commandSender, @NotNull String ...strings){
        StringBuilder string = new StringBuilder();
        for (String world : strings) string.append(world).append("\n");
        commandSender.sendMessage(Component.text(string.substring(0, string.length() - 1)).color(NamedTextColor.RED).decorate(TextDecoration.BOLD));
    }
}