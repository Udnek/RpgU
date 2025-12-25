package me.udnek.rpgu.util;

import me.udnek.coreu.custom.component.instance.TranslatableThing;
import me.udnek.coreu.custom.registry.CustomRegistries;
import me.udnek.coreu.custom.sound.ConstructableCustomSound;
import me.udnek.coreu.custom.sound.CustomSound;
import me.udnek.rpgu.RpgU;
import org.bukkit.SoundCategory;
import org.jetbrains.annotations.NotNull;

public class Sounds {

    public static final CustomSound BACKSTAB = register(new ConstructableCustomSound(
            "backstab", SoundCategory.PLAYERS, TranslatableThing.ofEng("Backstab")));

    private static @NotNull CustomSound register(@NotNull CustomSound sound){
        return CustomRegistries.SOUND.register(RpgU.getInstance(), sound);
    }
}