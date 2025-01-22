package me.udnek.rpgu.entity;

import me.udnek.itemscoreu.util.SelfRegisteringListener;
import me.udnek.rpgu.item.Items;
import org.bukkit.entity.Entity;
import org.bukkit.entity.PiglinBrute;
import org.bukkit.entity.WitherSkeleton;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class ModifiedEntitySpawnListener extends SelfRegisteringListener {
    public ModifiedEntitySpawnListener(JavaPlugin plugin) {
        super(plugin);
    }

    @EventHandler
    public void onSpawn(EntitySpawnEvent event){
        Entity entity = event.getEntity();
        switch (entity.getType()) {
            case PIGLIN_BRUTE -> {
                PiglinBrute entityPiglinBrute = (PiglinBrute) entity;
                EntityEquipment equipment = entityPiglinBrute.getEquipment();
                ItemStack oldAxe = equipment.getItemInMainHand();
                ItemStack newAxe = Items.SHINY_AXE.getItem();
                newAxe.addEnchantments(oldAxe.getEnchantments());
                equipment.setItemInMainHand(newAxe);
            }
            case WITHER_SKELETON -> {
                WitherSkeleton entityWitherSkeleton = (WitherSkeleton) entity;
                entityWitherSkeleton.getEquipment().setItemInMainHand(Items.FLINT_SWORD.getItem());
            }
        }
    }
}
