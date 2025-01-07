package me.udnek.rpgu.util;

import com.google.common.collect.Maps;
import org.bukkit.Location;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class Sounds {
    public static Map<String, PlayableSound> values = Maps.newHashMap();
    public static final PlayableSound BACKSTAB;

    static {
        BACKSTAB = register(new PlayableSound("rpgu:backstab", SoundCategory.PLAYERS, 1, 1));
    }

    public static @Nullable PlayableSound getSound(@NotNull String name)  {
        return values.get(name);
    }

    public record PlayableSound(@NotNull String soundName, @NotNull SoundCategory category, float volume, float pitch) {
        public void play(@NotNull Location location){
            location.getWorld().playSound(location, soundName, category, volume, pitch);
        }

        public void play(@NotNull Player player){
            player.playSound(player, soundName, category, volume, pitch);
        }

        public void play(@NotNull Player player, @NotNull Location location){
            player.playSound(location, soundName, category, volume, pitch);
        }
    }

    private static PlayableSound register(PlayableSound playableSound){
        String soundName = playableSound.soundName;
        values.put(soundName.substring(soundName.indexOf(":") + 1), playableSound);
        return playableSound;
    }
}