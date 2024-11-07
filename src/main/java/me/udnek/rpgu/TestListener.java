package me.udnek.rpgu;

import io.papermc.paper.event.player.PlayerStopUsingItemEvent;
import me.udnek.itemscoreu.customevent.CustomItemGeneratedEvent;
import me.udnek.itemscoreu.customevent.InitializationEvent;
import me.udnek.itemscoreu.customitem.CustomItem;
import me.udnek.itemscoreu.customitem.VanillaBasedCustomItem;
import me.udnek.itemscoreu.util.InitializationProcess;
import me.udnek.itemscoreu.util.SelfRegisteringListener;
import me.udnek.rpgu.component.ComponentTypes;
import me.udnek.rpgu.item.Items;
import me.udnek.rpgu.lore.AttributeLoreGenerator;
import me.udnek.rpgu.util.RecipeManaging;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.VillagerAcquireTradeEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MerchantRecipe;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Map;

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

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void villagerTrades(VillagerAcquireTradeEvent event){
        if (!(event.getEntity() instanceof Villager villager)) return;
        MerchantRecipe recipe = event.getRecipe();
        Material material = recipe.getResult().getType();

        if (villager.getProfession() == Villager.Profession.TOOLSMITH) {
            ItemStack itemStack = switch (material) {
                case Material.STONE_AXE -> Items.FLINT_AXE.getItem();
                case Material.STONE_PICKAXE -> Items.FLINT_PICKAXE.getItem();
                case Material.STONE_SHOVEL -> Items.FLINT_SHOVEL.getItem();
                case Material.STONE_HOE -> Items.FLINT_HOE.getItem();
                case Material.STONE_SWORD -> Items.FLINT_SWORD.getItem();
                case Material.DIAMOND_AXE -> Items.FERRUDAM_AXE.getItem();
                case Material.DIAMOND_PICKAXE -> Items.FERRUDAM_PICKAXE.getItem();
                case Material.DIAMOND_SHOVEL -> Items.FERRUDAM_SHOVEL.getItem();
                case Material.DIAMOND_HOE -> Items.FERRUDAM_HOE.getItem();
                default -> null;
            };
            if (itemStack == null) return;
            event.setRecipe(replaceRecipe(recipe, itemStack));
        }

        if (villager.getProfession() == Villager.Profession.ARMORER) {
            ItemStack itemStack = switch (material) {
                case Material.DIAMOND_HELMET -> Items.FERRUDAM_HELMET.getItem();
                case Material.DIAMOND_CHESTPLATE -> Items.FERRUDAM_CHESTPLATE.getItem();
                case Material.DIAMOND_LEGGINGS -> Items.FERRUDAM_LEGGINGS.getItem();
                case Material.DIAMOND_BOOTS -> Items.FERRUDAM_BOOTS.getItem();
                default -> null;
            };
            if (itemStack == null) return;
            event.setRecipe(replaceRecipe(recipe, itemStack));
        }

        if (villager.getProfession() == Villager.Profession.WEAPONSMITH) {
            ItemStack itemStack = switch (material) {
                case Material.DIAMOND_AXE -> Items.FERRUDAM_AXE.getItem();
                case Material.DIAMOND_SWORD -> Items.FERRUDAM_SWORD.getItem();
                default -> null;
            };
            if (itemStack == null) return;
            event.setRecipe(replaceRecipe(recipe, itemStack));
        }
    }

    public MerchantRecipe replaceRecipe(MerchantRecipe recipe, ItemStack newItem){
        Map<Enchantment, Integer> oldItemEnchants = recipe.getResult().getEnchantments();
        newItem.addEnchantments(oldItemEnchants);
        MerchantRecipe customRecipe = new MerchantRecipe(newItem, recipe.getUses(), recipe.getMaxUses(), recipe.hasExperienceReward(), recipe.getVillagerExperience(), recipe.getPriceMultiplier(),
                recipe.getDemand(), recipe.getSpecialPrice(), recipe.shouldIgnoreDiscounts());
        customRecipe.setIngredients(recipe.getIngredients());

        return customRecipe;
    }

    @EventHandler
    public void abilityRightClick(PlayerInteractEvent event){
        if (!event.getAction().isRightClick()) return;
        CustomItem.consumeIfCustom(event.getItem(), customItem -> {
            customItem.getComponentOrDefault(ComponentTypes.ACTIVE_ABILITY_ITEM).onRightClick(customItem, event);
        });
    }

    @EventHandler
    public void abilityStopUsing(PlayerStopUsingItemEvent event){
        CustomItem.consumeIfCustom(event.getItem(), customItem -> {
            customItem.getComponentOrDefault(ComponentTypes.ACTIVE_ABILITY_ITEM).onStopUsing(customItem, event);
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
