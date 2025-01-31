package me.udnek.rpgu.command;

import me.udnek.rpgu.RpgU;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Commands {
    public static void declareCommands() {
        RpgU instance = RpgU.getInstance();
        instance.getCommand("debug_equipment").setExecutor(new DebugEquipmentCommand());
        instance.getCommand("custom_damage_system").setExecutor(new CustomDamageSystemCommand());
        instance.getCommand("reset_cooldown").setExecutor(new ResetCooldownCommand());
        instance.getCommand("set_food").setExecutor(new SetFoodCommand());
    }

    public static void sendError(@NotNull CommandSender commandSender, @NotNull String ...strings){
        sendError(commandSender, null,  strings);
    }
    public static void sendError(@NotNull CommandSender commandSender, @Nullable TextColor textColor,  @NotNull String ...strings){
        StringBuilder string = new StringBuilder();
        if (textColor == null) textColor = NamedTextColor.RED;
        for (String world : strings) string.append(world).append("\n");
        commandSender.sendMessage(Component.text(string.substring(0, string.length() - 1)).color(textColor).decorate(TextDecoration.BOLD));
    }
}