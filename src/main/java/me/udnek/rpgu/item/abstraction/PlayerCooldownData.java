package me.udnek.rpgu.item.abstraction;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class PlayerCooldownData {

    private final Map<Player, Integer> cooldownExpireTime = new HashMap<>();
    private int standardCooldown;
    public PlayerCooldownData(int cooldown){
        standardCooldown = cooldown;
    }

    public void setStandardCooldown(int standardCooldown) {this.standardCooldown = standardCooldown;}
    public int getStandardCooldown() {return standardCooldown;}

    public void set(Player player){
        set(player, standardCooldown);
    }
    public void set(Player player, int cooldown){
        cooldownExpireTime.put(player, Bukkit.getCurrentTick()+cooldown);
    }

    public int get(Player player){
        Integer expire = cooldownExpireTime.get(player);
        if (expire == null) return 0;
        int cooldown = expire - Bukkit.getCurrentTick();
        if (cooldown <= 0) {
            cooldownExpireTime.remove(player);
            return 0;
        }
        return cooldown;
    }
    public boolean has(Player player){
        return get(player) > 0;
    }

    public float getProgress(Player player){
        int cooldown = get(player);
        return Math.min(1.0f, cooldown/(float)standardCooldown);
    }

}
