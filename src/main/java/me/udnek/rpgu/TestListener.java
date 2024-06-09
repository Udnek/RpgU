package me.udnek.rpgu;

import me.udnek.itemscoreu.utils.LogUtils;
import me.udnek.itemscoreu.utils.SelfRegisteringListener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class TestListener extends SelfRegisteringListener {
    public TestListener(JavaPlugin plugin) {
        super(plugin);
    }


    @EventHandler
    public void onSlot(InventoryClickEvent event){
        LogUtils.log("slot: " + event.getSlot());
        LogUtils.log("rawSlot: " + event.getRawSlot());
    }

}
