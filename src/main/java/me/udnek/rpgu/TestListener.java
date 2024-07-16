package me.udnek.rpgu;

import io.papermc.paper.registry.RegistryAccess;
import io.papermc.paper.registry.RegistryKey;
import me.udnek.itemscoreu.nms.NMS;
import me.udnek.itemscoreu.nms.StructureStartSearchMethod;
import me.udnek.itemscoreu.utils.LogUtils;
import me.udnek.itemscoreu.utils.SelfRegisteringListener;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.HoverEvent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.generator.structure.Structure;
import org.bukkit.generator.structure.StructureType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.map.MapCursor;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Iterator;

public class TestListener extends SelfRegisteringListener {
    public TestListener(JavaPlugin plugin) {
        super(plugin);
    }

/*    @EventHandler
    public void onSlot(InventoryClickEvent event){
        Player player = (Player) event.getWhoClicked();

        HoverEvent<?> hoverEvent = HoverEvent.showItem(HoverEvent.ShowItem.showItem(Material.BRICK, 1));
        Component text = Component.text("123").hoverEvent(hoverEvent);
        player.sendMessage(text);

        Entity entity = player.getWorld().spawnEntity(player.getLocation(), EntityType.ARMOR_STAND);
        hoverEvent = HoverEvent.showEntity(HoverEvent.ShowEntity.showEntity(EntityType.ARMOR_STAND, entity.getUniqueId()));
        text = Component.text("#$23").hoverEvent(hoverEvent);
        player.sendMessage(text);
        entity.remove();
    }*/

    @EventHandler
    public void on(PlayerInteractEvent event){
/*        if (!event.getAction().isRightClick()) return;


        Structure structure = RegistryAccess.registryAccess().getRegistry(RegistryKey.STRUCTURE).get(new NamespacedKey("tcc", "tundra_keep"));

        event.getPlayer().sendMessage(String.valueOf(NMS.get().isInStructure(
                event.getPlayer().getLocation(),
                structure,
                StructureStartSearchMethod.NEAREST_CHUNKS_SCAN,
                7
        )));*/

/*        Player player = event.getPlayer();
        LogUtils.log("started");
*//*        Structure
        for (StructureType structureType : Registry.STRUCTURE_TYPE) {
            player.sendMessage(structureType.getKey().toString());
        }*//*


        ItemStack itemStack = NMS.get().generateExplorerMap(player.getLocation(), Structure.RUINED_PORTAL_MOUNTAIN, 500, false, MapCursor.Type.MONUMENT);

*//*
        ItemStack itemStack = Bukkit.createExplorerMap(
                player.getWorld(),
                player.getLocation(),
                Structure.TRIAL_CHAMBERS.getStructureType(),
                MapCursor.Type.RED_X,
                5000,
                false
        );
*//*

        LogUtils.log("done");
        if (itemStack == null){
            LogUtils.log(":(");
            return;
        }
        player.getInventory().addItem(itemStack);*/
    }

}
