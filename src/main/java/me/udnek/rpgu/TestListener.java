package me.udnek.rpgu;

import me.udnek.itemscoreu.utils.SelfRegisteringListener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;

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
