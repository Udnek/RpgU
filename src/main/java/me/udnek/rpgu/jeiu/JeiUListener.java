package me.udnek.rpgu.jeiu;

import me.udnek.coreu.util.SelfRegisteringListener;
import me.udnek.jeiu.event.UnknownLootTableIconEvent;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class JeiUListener extends SelfRegisteringListener {

    public JeiUListener(Plugin plugin) {
        super(plugin);
    }

    @EventHandler
    void onIconGenerated(UnknownLootTableIconEvent event){
        String id = event.lootTable.key().value();
        if (id.startsWith("archaelogy")){ // MISSPELLING
            event.setIcon(new ItemStack(Material.BRUSH));
        }
        else if (id.startsWith("keys")){
            event.setIcon(new ItemStack(Material.TRIAL_KEY));
        }
    }
}
