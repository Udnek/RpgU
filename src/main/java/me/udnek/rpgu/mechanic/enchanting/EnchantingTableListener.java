package me.udnek.rpgu.mechanic.enchanting;

import me.udnek.itemscoreu.util.SelfRegisteringListener;
import org.bukkit.Material;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class EnchantingTableListener extends SelfRegisteringListener {
    public EnchantingTableListener(JavaPlugin plugin) {
        super(plugin);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerClicksEnchantmentTable(PlayerInteractEvent event){
        if (event.useInteractedBlock() == Event.Result.DENY) return;
        if (event.getAction() == Action.LEFT_CLICK_BLOCK) return;
        if (event.getClickedBlock() == null) return;
        if (event.getPlayer().isSneaking()) return;
        if (event.getClickedBlock().getType() != Material.ENCHANTING_TABLE) return;
        event.setCancelled(true);
        new EnchantingTableInventory().open(event.getPlayer());
    }

}
