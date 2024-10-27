package me.udnek.rpgu;

import io.papermc.paper.tag.BaseTag;
import me.udnek.itemscoreu.customevent.CustomItemGeneratedEvent;
import me.udnek.itemscoreu.customevent.InitializationEvent;
import me.udnek.itemscoreu.customitem.VanillaBasedCustomItem;
import me.udnek.itemscoreu.util.InitializationProcess;
import me.udnek.itemscoreu.util.SelfRegisteringListener;
import me.udnek.rpgu.lore.AttributeLoreGenerator;
import me.udnek.rpgu.util.RecipeManaging;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.block.BlockState;
import org.bukkit.block.data.BlockData;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.BlockInventoryHolder;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

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

/*
    @EventHandler
    public void onLoot(LootEntryCreateItemEvent event){
        if (!event.isSameLootTable(LootTables.ANCIENT_CITY.getLootTable())) return;
        LogUtils.log(event.getAll());
        if (!event.contains(Material.ECHO_SHARD)) return;
        LogUtils.log("FOUND ANCIENT ECHO SHARD");
        event.addItem(new ItemStack(Material.MACE));
        LogUtils.log(event.getAll());
    }
*/


    @EventHandler
    public void onOpen(InventoryOpenEvent event){
        if (event.getInventory().getType() == InventoryType.WORKBENCH &&
            event.getView().title().toString().equals(Component.translatable("container.crafting").toString()))
        {
            event.titleOverride(Component.text("//TODO REPLACE"));
        }

    }

    @EventHandler
    public void onInit(InitializationEvent event){
        if (event.getStep() != InitializationProcess.Step.AFTER_REGISTRIES_INITIALIZATION) return;
        RecipeManaging.run();
    }

    @EventHandler
    public void onItemGenerates(CustomItemGeneratedEvent event){
        //System.out.println("CALLED EVENT");
        AttributeLoreGenerator.generate(event.getItemStack(), event.getLoreBuilder());
        if (event.getCustomItem() instanceof VanillaBasedCustomItem){
           // System.out.println("MODIFING");
            event.getItemStack().addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            ///System.out.println(event.getItemStack().getItemFlags());
        }
    }


    @EventHandler
    public void onInteract(PlayerInteractEvent event){
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
