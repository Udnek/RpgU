package me.udnek.rpgu.mechanic.alloying;

import com.destroystokyo.paper.event.block.BlockDestroyEvent;
import org.bukkit.block.Block;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.HopperInventorySearchEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.jetbrains.annotations.NotNull;

public interface AlloyForgeMachine {
    void unload(@NotNull Block block);
    void load(@NotNull Block block);
    void tick();
    void onBlastInventoryOpen(InventoryOpenEvent event);
    void onHopperSearch(HopperInventorySearchEvent event);
    void onHopperTakesItem(InventoryMoveItemEvent event);
    void onHopperGivesItem(InventoryMoveItemEvent event);
    void onDestroy(BlockDestroyEvent event);
    void onBreak(BlockBreakEvent event);
}
