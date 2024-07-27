package me.udnek.rpgu.util;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public abstract class PerPlayerData<T> {

    protected final Map<Player, T> data = new HashMap<>();
    public void set(Player player, T value){data.put(player, value);}
    public T get(Player player){return data.get(player);}
    public boolean has(Player player){return get(player) != null;}
    public void remove(Player player){data.remove(player);}
}
