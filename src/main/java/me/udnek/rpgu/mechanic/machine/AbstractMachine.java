package me.udnek.rpgu.mechanic.machine;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.HopperInventorySearchEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.jetbrains.annotations.NotNull;

public interface AbstractMachine {
    void unload();
    void load();
    void tick(int tickDelay);
    void onHopperSearch(@NotNull HopperInventorySearchEvent event);
    void destroy();
    void openInventory(@NotNull Player player);
}
