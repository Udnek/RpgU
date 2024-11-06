package me.udnek.rpgu.mechanic.alloying;

import com.destroystokyo.paper.event.block.BlockDestroyEvent;
import me.udnek.itemscoreu.util.TickingTask;
import me.udnek.rpgu.RpgU;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.BlastFurnace;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.HopperInventorySearchEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.ChunkUnloadEvent;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AlloyForgeManager extends TickingTask implements Listener {

    public static final int TICK_DELAY = 1;
    private static AlloyForgeManager instance;
    private final HashMap<String, AlloyForgeMachine> machines = new HashMap<>();
    private final List<Block> unloadTickets = new ArrayList<>();
    private AlloyForgeManager(){
        Bukkit.getPluginManager().registerEvents(this, RpgU.getInstance());
        start(RpgU.getInstance());
    }
    @Override
    public int getDelay() {return TICK_DELAY;}

    public static AlloyForgeManager getInstance() {
        if (instance == null) instance = new AlloyForgeManager();
        return instance;
    }
    private AlloyForgeMachine load(Block block){
        String serialized = serializeLocation(block);
        AlloyForgeMachine machine = machines.get(serialized);
        if (machine != null) return machine;
        machine = new AlloyForgeInventory(block);
        machines.put(serialized, machine);
        machine.load(block);
        return machine;
    }
    public void addUnloadTicket(Block block){
        unloadTickets.add(block);
    }

    private void unload(Block block){
        String serialized = serializeLocation(block);
        AlloyForgeMachine machine = machines.get(serialized);
        if (machine == null) return;
        machines.remove(serialized);
        machine.unload(block);
    }


    @Override
    public void run() {
        unloadTickets.forEach(this::unload);
        unloadTickets.clear();
        for (AlloyForgeMachine machine : machines.values()) {
            machine.tick();
        }
    }

    public String serializeLocation(Block block){
        return (block.getX()+","+
                block.getY()+","+
                block.getZ()+","+
                block.getWorld().getName()
        );
    }
    public @Nullable AlloyForgeMachine getMachine(Block block){
        return machines.get(serializeLocation(block));
    }


    @EventHandler
    public void onChuckLoad(ChunkLoadEvent event){
        for (BlockState tileEntity : event.getChunk().getTileEntities()) {
            if (!(tileEntity instanceof BlastFurnace)) return;
            load(tileEntity.getBlock());
        }
    }
    @EventHandler
    public void onChuckUnload(ChunkUnloadEvent event){
        for (BlockState tileEntity : event.getChunk().getTileEntities()) {
            if (!(tileEntity instanceof BlastFurnace)) return;
            unload(tileEntity.getBlock());
        }
    }
/*    @EventHandler
    public void onRightClick(PlayerInteractEvent event){
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        if (event.getClickedBlock().getType() != Material.BLAST_FURNACE) return;
        AlloyForgeMachine machine = getMachine(event.getClickedBlock());
        if (machine == null) machine = load(event.getClickedBlock());
        machine.onRightClick(event);
    }*/
    @EventHandler
    public void onPlayerOpensInventory(InventoryOpenEvent event){
        InventoryHolder holder = event.getInventory().getHolder();
        if (!(holder instanceof BlastFurnace blastFurnace)) return;
        AlloyForgeMachine machine = getMachine(blastFurnace.getBlock());
        if (machine == null) machine = load(blastFurnace.getBlock());
        machine.onBlastInventoryOpen(event);
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event){
        if (event.getBlock().getType() != Material.BLAST_FURNACE) return;
        load(event.getBlock());
    }
    @EventHandler
    public void onHopper(HopperInventorySearchEvent event){
        if (event.getSearchBlock().getType() != Material.BLAST_FURNACE) return;
        AlloyForgeMachine machine = getMachine(event.getSearchBlock());
        if (machine == null) machine = load(event.getSearchBlock());
        machine.onHopperSearch(event);
    }
    @EventHandler
    public void onItemTransfer(InventoryMoveItemEvent event){
        AlloyForgeInventory.acceptIfInventoryIsForge(event.getSource(), alloyForgeInventory -> alloyForgeInventory.onHopperTakesItem(event));
        AlloyForgeInventory.acceptIfInventoryIsForge(event.getDestination(), alloyForgeInventory -> alloyForgeInventory.onHopperGivesItem(event));
    }
    @EventHandler
    public void onBlockDestroy(BlockDestroyEvent event){
        if (event.getBlock().getType() != Material.BLAST_FURNACE) return;
        AlloyForgeMachine machine = getMachine(event.getBlock());
        if (machine == null) return;
        machine.onDestroy(event);
    }
    @EventHandler
    public void onPlayerBlockDestroy(BlockBreakEvent event){
        if (event.getBlock().getType() != Material.BLAST_FURNACE) return;
        AlloyForgeMachine machine = getMachine(event.getBlock());
        if (machine == null) return;
        machine.onBreak(event);
    }
}








