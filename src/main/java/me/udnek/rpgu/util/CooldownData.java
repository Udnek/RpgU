package me.udnek.rpgu.util;

import me.udnek.itemscoreu.utils.ComponentU;
import me.udnek.rpgu.lore.TranslationKeys;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class CooldownData extends PerPlayerData<Integer>{

    private final int standardCooldown;
    public CooldownData(int cooldown){
        standardCooldown = cooldown;
    }

    public void set(Player player){ set(player, standardCooldown);}

    @Override
    public void set(Player player, Integer cooldown){
        data.put(player, Bukkit.getCurrentTick()+cooldown);
    }

    @Override
    public Integer get(Player player){
        Integer expire = data.get(player);
        if (expire == null) return 0;
        int cooldown = expire - Bukkit.getCurrentTick();
        if (cooldown <= 0) {
            data.remove(player);
            return 0;
        }
        return cooldown;
    }
    @Override
    public boolean has(Player player){
        return get(player) > 0;
    }

    public float getProgress(Player player){
        int cooldown = get(player);
        return Math.min(1.0f, cooldown/(float)standardCooldown);
    }

    public Component getImage(Player player){
        return ComponentU.textWithNoSpace(Component.translatable(TranslationKeys.artifactCooldown + Math.round(getProgress(player) * 16)), 16);

    }
}
