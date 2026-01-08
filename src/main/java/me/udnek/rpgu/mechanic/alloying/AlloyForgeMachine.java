package me.udnek.rpgu.mechanic.alloying;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.HopperInventorySearchEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.jetbrains.annotations.NotNull;

public interface AlloyForgeMachine {
    void unload();
    void load();
    void tick();
    void onHopperSearch(@NotNull HopperInventorySearchEvent event);
    void onHopperTakesItem(@NotNull InventoryMoveItemEvent event);
    void onHopperGivesItem(@NotNull InventoryMoveItemEvent event);
    void destroy();
    void openInventory(@NotNull Player player);
}
