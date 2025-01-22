package me.udnek.rpgu.util;

import io.papermc.paper.datacomponent.DataComponentTypes;
import io.papermc.paper.datacomponent.item.ItemAttributeModifiers;
import io.papermc.paper.event.player.PlayerStopUsingItemEvent;
import me.udnek.itemscoreu.customevent.CustomItemGeneratedEvent;
import me.udnek.itemscoreu.customevent.InitializationEvent;
import me.udnek.itemscoreu.customitem.CustomItem;
import me.udnek.itemscoreu.customitem.VanillaItemManager;
import me.udnek.itemscoreu.customregistry.InitializationProcess;
import me.udnek.itemscoreu.util.SelfRegisteringListener;
import me.udnek.rpgu.RpgU;
import me.udnek.rpgu.component.ComponentTypes;
import me.udnek.rpgu.item.Items;
import me.udnek.rpgu.lore.AttributeLoreGenerator;
import me.udnek.rpgu.vanilla.EnchantManaging;
import me.udnek.rpgu.vanilla.RecipeManaging;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
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
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MerchantRecipe;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Map;

public class GeneralListener extends SelfRegisteringListener {
    public GeneralListener(JavaPlugin plugin) {
        super(plugin);
    }

    @EventHandler
    public void setBasePlayerHealth(PlayerJoinEvent event){
        double basePlayerHealth = 10;
        Player player = event.getPlayer();
        new BukkitRunnable(){
            @Override
            public void run() {
                AttributeInstance attribute = player.getAttribute(Attribute.MAX_HEALTH);
                double value = attribute.getValue();
                if (value != basePlayerHealth) attribute.setBaseValue(basePlayerHealth);
                if (value >= basePlayerHealth) player.setHealth(basePlayerHealth);
            }
        }.runTaskLater(RpgU.getInstance(), 5);
    }

    @EventHandler
    public void disableLibrarian(PlayerInteractEntityEvent event){
        if (!(event.getRightClicked() instanceof Villager villager)) return;
        if (villager.getProfession() == Villager.Profession.LIBRARIAN) event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void villagerTrades(VillagerAcquireTradeEvent event){
        if (!(event.getEntity() instanceof Villager)) return;
        MerchantRecipe recipe = event.getRecipe();
        Material material = recipe.getResult().getType();


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
            case Material.DIAMOND_SWORD -> Items.FERRUDAM_SWORD.getItem();

            case Material.DIAMOND_HELMET -> Items.FERRUDAM_HELMET.getItem();
            case Material.DIAMOND_CHESTPLATE -> Items.FERRUDAM_CHESTPLATE.getItem();
            case Material.DIAMOND_LEGGINGS -> Items.FERRUDAM_LEGGINGS.getItem();
            case Material.DIAMOND_BOOTS -> Items.FERRUDAM_BOOTS.getItem();

            default -> null;
        };
        if (itemStack == null) return;
        event.setRecipe(replaceRecipe(recipe, itemStack));
    }

    public MerchantRecipe replaceRecipe(MerchantRecipe recipe, ItemStack newItem){
        Map<Enchantment, Integer> oldItemEnchants = recipe.getResult().getEnchantments();
        newItem.addEnchantments(oldItemEnchants);
        MerchantRecipe customRecipe = new MerchantRecipe(
                newItem, recipe.getUses(),
                recipe.getMaxUses(),
                recipe.hasExperienceReward(),
                recipe.getVillagerExperience(),
                recipe.getPriceMultiplier(),
                recipe.getDemand(),
                recipe.getSpecialPrice(),
                recipe.shouldIgnoreDiscounts());

        customRecipe.setIngredients(recipe.getIngredients());
        return customRecipe;
    }

    @EventHandler
    public void abilityRightClick(PlayerInteractEvent event){
        if (!event.getAction().isRightClick()) return;
        CustomItem.consumeIfCustom(event.getItem(), customItem ->
                customItem.getComponents().getOrDefault(ComponentTypes.ACTIVE_ABILITY_ITEM).onRightClick(customItem, event));
    }
    @EventHandler
    public void abilityStopUsing(PlayerStopUsingItemEvent event){
        CustomItem.consumeIfCustom(event.getItem(), customItem ->
                customItem.getComponents().getOrDefault(ComponentTypes.ACTIVE_ABILITY_ITEM).onStopUsing(customItem, event));
    }
    @EventHandler
    public void abilityConsume(PlayerItemConsumeEvent event){
        CustomItem.consumeIfCustom(event.getItem(), customItem ->
                customItem.getComponents().getOrDefault(ComponentTypes.ACTIVE_ABILITY_ITEM).onConsume(customItem, event));
    }


    @EventHandler
    public void craftingTableInventoryRename(InventoryOpenEvent event){
        if (event.getInventory().getType() == InventoryType.WORKBENCH &&
            event.getView().title().toString().equals(Component.translatable("container.crafting").toString()))
        {
            event.titleOverride(Component.translatable(Material.CRAFTING_TABLE.translationKey()));
        }

    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void itemGenerates(CustomItemGeneratedEvent event){
        event.getCustomItem().getComponents().getOrDefault(ComponentTypes.ACTIVE_ABILITY_ITEM).getLore(event.getLoreBuilder());
        event.getCustomItem().getComponents().getOrDefault(ComponentTypes.PASSIVE_ABILITY_ITEM).getLore(event.getLoreBuilder());
        AttributeLoreGenerator.generate(event.getItemStack(), event.getLoreBuilder());
        if (VanillaItemManager.isReplaced(event.getCustomItem())){
            ItemAttributeModifiers attributeModifiers = event.getItemStack().getData(DataComponentTypes.ATTRIBUTE_MODIFIERS);
            if (attributeModifiers == null) return;
            event.getItemStack().setData(DataComponentTypes.ATTRIBUTE_MODIFIERS, attributeModifiers.showInTooltip(false));
        }
    }


    @EventHandler
    public void recipeInitialization(InitializationEvent event){
        if (event.getStep() != InitializationProcess.Step.AFTER_REGISTRIES_INITIALIZATION) return;
        RecipeManaging.run();
        EnchantManaging.run();
    }
}
