package me.udnek.rpgu;

import me.udnek.itemscoreu.customevent.CustomItemGeneratedEvent;
import me.udnek.itemscoreu.customevent.InitializationEvent;
import me.udnek.itemscoreu.customitem.CustomItem;
import me.udnek.itemscoreu.customitem.VanillaBasedCustomItem;
import me.udnek.itemscoreu.util.InitializationProcess;
import me.udnek.itemscoreu.util.SelfRegisteringListener;
import me.udnek.rpgu.component.ComponentTypes;
import me.udnek.rpgu.lore.AttributeLoreGenerator;
import me.udnek.rpgu.util.RecipeManaging;
import net.kyori.adventure.text.Component;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.plugin.java.JavaPlugin;

public class TestListener extends SelfRegisteringListener {
    public TestListener(JavaPlugin plugin) {
        super(plugin);
    }

    @EventHandler
    public void setBasePlayerHealth(PlayerJoinEvent event){
        Player player = event.getPlayer();

        player.getAttribute(Attribute.MAX_HEALTH).setBaseValue(10);
    }

    @EventHandler
    public void disableLibrarian(PlayerInteractEntityEvent event){
        if (!(event.getRightClicked() instanceof Villager villager)) return;
        if (villager.getProfession() == Villager.Profession.LIBRARIAN) event.setCancelled(true);
    }

    @EventHandler
    public void abilityActivation(PlayerInteractEvent event){
        if (!event.getAction().isRightClick()) return;
        CustomItem.consumeIfCustom(event.getItem(), customItem -> {
            customItem.getComponentOrDefault(ComponentTypes.ACTIVE_ABILITY_ITEM).onRightClick(customItem, event);
        });
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
    public void craftingTableInventoryRename(InventoryOpenEvent event){
        if (event.getInventory().getType() == InventoryType.WORKBENCH &&
            event.getView().title().toString().equals(Component.translatable("container.crafting").toString()))
        {
            event.titleOverride(Component.text("//TODO REPLACE"));
        }

    }

    @EventHandler
    public void recipeInitialization(InitializationEvent event){
        if (event.getStep() != InitializationProcess.Step.AFTER_REGISTRIES_INITIALIZATION) return;
        RecipeManaging.run();
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void itemGenerates(CustomItemGeneratedEvent event){
        //System.out.println("CALLED EVENT");
        AttributeLoreGenerator.generate(event.getItemStack(), event.getLoreBuilder());
        event.getCustomItem().getComponentOrDefault(ComponentTypes.ACTIVE_ABILITY_ITEM).getLore(event.getLoreBuilder());
        if (event.getCustomItem() instanceof VanillaBasedCustomItem){
           // System.out.println("MODIFING");
            event.getItemStack().addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            ///System.out.println(event.getItemStack().getItemFlags());
        }
    }
}
