package me.udnek.rpgu.util;

import org.bukkit.Location;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Sounds {
    public static final PlayableSound BACKSTAB;

    static {
        BACKSTAB = new PlayableSound("rpgu:backstab_damage_sound", SoundCategory.PLAYERS, 100, 1);
    }

    public record PlayableSound(@NotNull String soundName, @NotNull SoundCategory category, float volume, float pitch) {
        public void play(@NotNull Location location){
            location.getWorld().playSound(location, soundName, category, volume, pitch);
        }

        public void play(@NotNull Player player){
            player.playSound(player, soundName, category, volume, pitch);
        }
    }


}
