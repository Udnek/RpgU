package me.udnek.rpgu.util;

import org.bukkit.Location;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Sounds {
    public static final SoundStats BACKSTAB;

    static {
        BACKSTAB = new SoundStats("rpgu:backstab_damage_sound", SoundCategory.PLAYERS, 100, 1);
    }

    public record SoundStats(@NotNull String soundName, @NotNull SoundCategory category, float volume, float pitch) {}

    public static void playSound(@NotNull SoundStats sound, @NotNull Location location){
        location.getWorld().playSound(location, sound.soundName(), sound.category(), sound.volume(), sound.pitch());
    }

    public static void playSoundToPlayer(@NotNull SoundStats sound, @NotNull Player player){
        player.playSound(player, sound.soundName(), sound.category(), sound.volume(), sound.pitch());
    }
}
