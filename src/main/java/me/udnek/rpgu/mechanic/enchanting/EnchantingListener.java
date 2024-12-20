package me.udnek.rpgu.mechanic.enchanting;

import me.udnek.itemscoreu.customrecipe.RecipeManager;
import me.udnek.itemscoreu.util.ItemUtils;
import me.udnek.itemscoreu.util.SelfRegisteringListener;
import me.udnek.jeiu.util.MenuQuery;
import me.udnek.jeiu.util.MenuQueryEvent;
import org.bukkit.Material;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class EnchantingListener extends SelfRegisteringListener {
    public EnchantingListener(JavaPlugin plugin) {
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
        new EnchantingTableInventory(event.getClickedBlock().getLocation()).open(event.getPlayer());
    }

/*    @EventHandler
    public void onJeiU(MenuQueryEvent event){
        MenuQuery query = event.getQuery();
        if (query.getType() == MenuQuery.Type.USAGES){
            if (!(ItemUtils.isVanillaMaterial(query.getItemStack(), Material.BOOK) || ItemUtils.isVanillaMaterial(query.getItemStack(), Material.LAPIS_LAZULI))) return;
            event.getAllRecipes().addAll(RecipeManager.getInstance().getByType(EnchantingRecipeType.INSTANCE));

        } else if (query.getType() == MenuQuery.Type.RECIPES){
            if (!ItemUtils.isVanillaMaterial(query.getItemStack(), Material.ENCHANTED_BOOK)) return;

        }
    }*/
}
