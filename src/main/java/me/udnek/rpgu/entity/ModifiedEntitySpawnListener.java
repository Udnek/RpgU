package me.udnek.rpgu.entity;

import me.udnek.itemscoreu.util.SelfRegisteringListener;
import me.udnek.rpgu.item.Items;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.PiglinBrute;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class ModifiedEntitySpawnListener extends SelfRegisteringListener {
    public ModifiedEntitySpawnListener(JavaPlugin plugin) {
        super(plugin);
    }

    @EventHandler
    public void onSpawn(EntitySpawnEvent event){
        if (event.getEntity().getType() != EntityType.PIGLIN_BRUTE) return;

        PiglinBrute entity = (PiglinBrute) event.getEntity();
        ItemStack oldAxe = entity.getEquipment().getItemInMainHand();
        ItemStack newAxe = Items.SHINY_AXE.getItem();
        newAxe.addEnchantments(oldAxe.getEnchantments());
        entity.getEquipment().setItemInMainHand(newAxe);
    }
}
