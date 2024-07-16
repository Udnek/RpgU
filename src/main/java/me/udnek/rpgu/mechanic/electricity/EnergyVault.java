package me.udnek.rpgu.mechanic.electricity;

import me.udnek.rpgu.entity.Entities;
import me.udnek.rpgu.multiblockstructure.Structures;
import org.bukkit.Material;
import org.bukkit.event.block.BlockPlaceEvent;

public class EnergyVault {

    public static final Material CORE_BLOCK = Material.BLAST_FURNACE;

    public static void playerPlacesCore(BlockPlaceEvent event){
        if (!Structures.energyVault.isStandingHere(event.getBlock().getLocation())) return;
        Entities.energyVault.spawn(event.getBlock().getLocation());
    }

}
