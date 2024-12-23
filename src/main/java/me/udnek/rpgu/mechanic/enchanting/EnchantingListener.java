package me.udnek.rpgu.mechanic.enchanting;

import io.papermc.paper.datacomponent.DataComponentTypes;
import me.udnek.itemscoreu.util.SelfRegisteringListener;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.BlockInventoryHolder;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class EnchantingListener extends SelfRegisteringListener {
    public EnchantingListener(JavaPlugin plugin) {
        super(plugin);
    }

    @EventHandler
    public void onPlayerOpensInventory(InventoryOpenEvent event){
        if (event.getInventory().getType() != InventoryType.ENCHANTING) return;
        InventoryHolder holder = event.getInventory().getHolder();
        if (holder instanceof BlockInventoryHolder blockInventoryHolder && blockInventoryHolder.getBlock().getType() == Material.ENCHANTING_TABLE){
            event.setCancelled(true);
            new EnchantingTableInventory(blockInventoryHolder.getBlock().getLocation()).open((Player) event.getPlayer());
        }
    }

    @EventHandler
    public void onAnvil(PrepareAnvilEvent event){
        ItemStack result = event.getResult();
        if (result == null) return;
        result.resetData(DataComponentTypes.REPAIR_COST);
        event.setResult(result);
    }
}
