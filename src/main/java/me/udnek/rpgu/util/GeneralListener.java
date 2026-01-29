package me.udnek.rpgu.util;

import com.google.gson.JsonParser;
import me.udnek.coreu.custom.component.instance.AutoGeneratingFilesItem;
import me.udnek.coreu.custom.event.InitializationEvent;
import me.udnek.coreu.custom.event.ResourcepackInitializationEvent;
import me.udnek.coreu.custom.item.CustomItem;
import me.udnek.coreu.custom.item.ItemUtils;
import me.udnek.coreu.custom.item.VanillaItemManager;
import me.udnek.coreu.custom.registry.InitializationProcess;
import me.udnek.coreu.nms.Nms;
import me.udnek.coreu.resourcepack.path.VirtualRpJsonFile;
import me.udnek.coreu.util.SelfRegisteringListener;
import me.udnek.rpgu.RpgU;
import me.udnek.rpgu.item.Items;
import me.udnek.rpgu.vanilla.EnchantManaging;
import me.udnek.rpgu.vanilla.RecipeManaging;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
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
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MerchantRecipe;
import org.bukkit.loot.LootTables;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Objects;

public class GeneralListener extends SelfRegisteringListener {
    public GeneralListener(JavaPlugin plugin) {
        super(plugin);
    }

    @EventHandler
    public void setBasePlayerHealth(PlayerJoinEvent event){
        double basePlayerHealth = 10;
        Player player = event.getPlayer();

        AttributeInstance attribute = player.getAttribute(Attribute.MAX_HEALTH);
        double value = Objects.requireNonNull(attribute).getValue();
        if (value != basePlayerHealth) attribute.setBaseValue(basePlayerHealth);
        if (value >= basePlayerHealth) player.setHealth(value);
        new BukkitRunnable(){
            @Override
            public void run() {

            }
        }.runTaskLater(RpgU.getInstance(), 5);
    }

    @EventHandler
    public void onResourcepackInitialization(ResourcepackInitializationEvent event){
        String model = """
                {
                	"parent": "rpgu:item/gui/alloying/progress/template",
                	"textures": {
                		"layer0": "rpgu:item/gui/alloying/progress/%lvl%"
                	}
                }""";
        for (int i = 0; i <= 29; i++) {
            event.addFile(new VirtualRpJsonFile(
                    JsonParser.parseString(model.replace("%lvl%", String.valueOf(i))).getAsJsonObject(),
                    AutoGeneratingFilesItem.GENERATED.getModelPath(new NamespacedKey(RpgU.getInstance(), "gui/alloying/progress/"+i))));
        }
        String definition = """
                {
                	"model": {
                		"type": "minecraft:model",
                		"model": "rpgu:item/gui/alloying/progress/%lvl%"
                	},
                    "oversized_in_gui": true
                }""";
        for (int i = 0; i <= 29; i++) {
            event.addFile(new VirtualRpJsonFile(
                    JsonParser.parseString(definition.replace("%lvl%", String.valueOf(i))).getAsJsonObject(),
                    AutoGeneratingFilesItem.GENERATED.getDefinitionPath(new NamespacedKey(RpgU.getInstance(), "gui/alloying/progress/"+i))));
        }
    }


    @EventHandler(priority = EventPriority.HIGH)
    public void villagerTrades(VillagerAcquireTradeEvent event){
        if (!(event.getEntity() instanceof Villager)) return;
        MerchantRecipe recipe = event.getRecipe();
        Material material = recipe.getResult().getType();
        if (CustomItem.isCustom(recipe.getResult())) return;
        if (ItemUtils.isSameIds(recipe.getResult(), new ItemStack(Material.ENCHANTED_BOOK))) event.setCancelled(true);

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

    public @NotNull MerchantRecipe replaceRecipe(@NotNull MerchantRecipe recipe, @NotNull ItemStack newItem){
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
    public void craftingTableInventoryRename(InventoryOpenEvent event){
        if (event.getInventory().getType() == InventoryType.WORKBENCH &&
            event.getView().title().toString().equals(Component.translatable("container.crafting").toString()))
        {
            event.titleOverride(Component.translatable(Material.CRAFTING_TABLE.translationKey()));
        }
    }


    @EventHandler
    public void initialization(InitializationEvent event){
        if (event.getStep() == InitializationProcess.Step.BEFORE_REGISTRIES_LOADED) unregisterMaterials();
        if (event.getStep() != InitializationProcess.Step.AFTER_GLOBAL_INITIALIZATION) return;
        RecipeManaging.run();
        EnchantManaging.run();
    }

    private void unregisterMaterials(){
        VanillaItemManager.getInstance().disableVanillaMaterial(Material.STONE_SWORD);
        VanillaItemManager.getInstance().disableVanillaMaterial(Material.STONE_PICKAXE);
        VanillaItemManager.getInstance().disableVanillaMaterial(Material.STONE_AXE);
        VanillaItemManager.getInstance().disableVanillaMaterial(Material.STONE_SHOVEL);
        VanillaItemManager.getInstance().disableVanillaMaterial(Material.STONE_HOE);

        VanillaItemManager.getInstance().disableVanillaMaterial(Material.TURTLE_HELMET);

        VanillaItemManager.getInstance().disableVanillaMaterial(Material.WOLF_ARMOR);

        Nms.get().removeAllEntriesContains(LootTables.RUINED_PORTAL.getLootTable(), itemStack -> ItemUtils.isVanillaMaterial(itemStack,Material.CLOCK));
    }
}
