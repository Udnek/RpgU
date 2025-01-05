package me.udnek.rpgu.util;

import org.bukkit.Location;
import org.bukkit.SoundCategory;
import org.jetbrains.annotations.NotNull;

public class Sounds {
    public static final SoundStats BACKSTAB_DAMAGE_ATTRIBUTE;

    static {
        BACKSTAB_DAMAGE_ATTRIBUTE = new SoundStats("rpgu:backstab_damage_sound", SoundCategory.PLAYERS, 100, 1, 1);
    }

    public record SoundStats(@NotNull String soundName, @NotNull SoundCategory category, float volume, float pitch, long seed) {}

    public static void playSound(@NotNull Location location, @NotNull SoundStats sound){
        location.getWorld().playSound(location, sound.soundName(), sound.category(), sound.volume(), sound.pitch(), sound.seed());
    }
}
