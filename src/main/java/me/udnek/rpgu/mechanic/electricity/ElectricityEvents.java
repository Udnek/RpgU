package me.udnek.rpgu.mechanic.electricity;

import me.udnek.itemscoreu.util.SelfRegisteringListener;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.Directional;
import org.bukkit.event.EventHandler;
import org.bukkit.event.weather.LightningStrikeEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class ElectricityEvents extends SelfRegisteringListener {

    public ElectricityEvents(JavaPlugin plugin) {super(plugin);}

    //public static boolean isStruckToLightningRod(LightningStrike lightningStrike){
    //    return lightningStrike.getLocation().getBlock().getType() == Material.LIGHTNING_ROD;
    //}
    public static Location locationUnderLightingRod(Location location){
        Block block = location.getBlock();
        Bukkit.getLogger().info(block.getBlockData().toString());
        if (block.getType() != Material.LIGHTNING_ROD) return null;

        return location.clone().add(((Directional) block.getBlockData()).getFacing().getOppositeFace().getDirection());
    }

    @EventHandler
    public void onLightningStrikes(LightningStrikeEvent event){
        Location location = event.getLightning().getLocation().add(0, -0.2, 0);
        if (location.getBlock().getType() != Material.LIGHTNING_ROD) return;
        //Bukkit.getLogger().info("LESGO");

        // TODO: 2/27/2024 REMOVE
/*        Bukkit.getLogger().info("");
        Bukkit.getLogger().info("");
        Bukkit.getLogger().info(String.valueOf(Bukkit.getScheduler().getPendingTasks().size()));

        for (BukkitTask pendingTask : Bukkit.getScheduler().getPendingTasks()) {
            Bukkit.getLogger().info(pendingTask.getOwner() + " " + pendingTask.getTaskId());
        }*/


        ElectricCharge.createChargeAtStruckLocation(location);
    }

    // TODO: 7/26/2024 FIX
/*    @EventHandler
    public void playerPlacesCore(BlockPlaceEvent event){
        if (event.getBlock().getType() != EnergyVault.CORE_BLOCK) return;
        EnergyVault.playerPlacesCore(event);

    }*/

}















