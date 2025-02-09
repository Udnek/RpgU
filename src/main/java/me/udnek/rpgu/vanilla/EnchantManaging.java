package me.udnek.rpgu.vanilla;

import me.udnek.itemscoreu.customitem.CustomItem;
import me.udnek.itemscoreu.customrecipe.RecipeManager;
import me.udnek.itemscoreu.customrecipe.choice.CustomSingleRecipeChoice;
import me.udnek.rpgu.RpgU;
import me.udnek.rpgu.item.Items;
import me.udnek.rpgu.mechanic.enchanting.EnchantingRecipe;
import me.udnek.rpgu.mechanic.enchanting.upgrade.EnchantingTableUpgrade;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static me.udnek.rpgu.mechanic.enchanting.upgrade.EnchantingTableUpgrade.*;

public class EnchantManaging {
    public static void run(){
        addEnchantingRecipeMaterial(Enchantment.AQUA_AFFINITY, List.of(Material.SEAGRASS));
        addEnchantingRecipeMaterial(Enchantment.BANE_OF_ARTHROPODS, List.of(Material.SPIDER_EYE));
        addEnchantingRecipeCustomItem(Enchantment.BLAST_PROTECTION, List.of(Items.BLAST_COAL), Set.of(DECENT_BOOKSHELF));
        addEnchantingRecipeMaterial(Enchantment.BREACH, List.of(Material.BREEZE_ROD, Material.IRON_AXE), Set.of(TRIAL_CHAMBER));
        addEnchantingRecipeMaterial(Enchantment.CHANNELING, List.of(Material.LIGHTNING_ROD), Set.of(WATER));
        addEnchantingRecipeMaterial(Enchantment.BINDING_CURSE, List.of(Material.NETHER_BRICK), Set.of(LITTLE_BOOKSHELF));
        addEnchantingRecipeMaterial(Enchantment.VANISHING_CURSE, List.of(Material.LAVA_BUCKET, Material.CACTUS));
        addEnchantingRecipeMaterial(Enchantment.DENSITY, List.of(Material.BREEZE_ROD, Material.LADDER), Set.of(TRIAL_CHAMBER));
        addEnchantingRecipeMaterial(Enchantment.DEPTH_STRIDER, List.of(Material.GOLD_BLOCK));
        addEnchantingRecipeMaterial(Enchantment.EFFICIENCY, List.of(Material.GOLD_INGOT, Material.GOLD_INGOT), Set.of(LOTS_OF_BOOKSHELF, NETHER, AMETHYST));
        addEnchantingRecipeMaterial(Enchantment.FEATHER_FALLING, List.of(Material.FEATHER, Material.FEATHER, Material.FEATHER), Set.of(LOTS_OF_BOOKSHELF, AMETHYST));
        addEnchantingRecipeMaterial(Enchantment.FIRE_ASPECT, List.of(Material.TORCHFLOWER, Material.STICK), Set.of(LITTLE_BOOKSHELF));
        addEnchantingRecipeMaterial(Enchantment.FIRE_PROTECTION, List.of(Material.MAGMA_CREAM), Set.of(DECENT_BOOKSHELF));
        addEnchantingRecipeMaterial(Enchantment.FLAME, List.of(Material.TORCHFLOWER, Material.FLINT), Set.of(LITTLE_BOOKSHELF));
        addEnchantingRecipeMaterial(Enchantment.FORTUNE, List.of(Material.RABBIT_FOOT), Set.of(LITTLE_BOOKSHELF));
        addEnchantingRecipeMaterial(Enchantment.FROST_WALKER, List.of(Material.ICE, Material.PACKED_ICE));
        addEnchantingRecipeMaterial(Enchantment.IMPALING, List.of(Material.KELP), Set.of(WATER));
        addEnchantingRecipeMaterial(Enchantment.INFINITY, List.of(Material.WATER_BUCKET, Material.LAVA_BUCKET), Set.of(LITTLE_BOOKSHELF));
        addEnchantingRecipeMaterial(Enchantment.KNOCKBACK, List.of(Material.PISTON, Material.STICK));
        addEnchantingRecipeMaterial(Enchantment.LOOTING, List.of(Material.ENDER_EYE), Set.of(LITTLE_BOOKSHELF));
        addEnchantingRecipeCustomItem(Enchantment.LOYALTY, List.of(Items.WOLF_PELT), Set.of(WATER, LITTLE_BOOKSHELF));
        addEnchantingRecipeMaterial(Enchantment.LUCK_OF_THE_SEA, List.of(Material.TROPICAL_FISH));
        addEnchantingRecipeMaterial(Enchantment.LURE, List.of(Material.PUFFERFISH));
        addEnchantingRecipeMaterial(Enchantment.MENDING, List.of(Material.NETHER_STAR), Set.of(LOTS_OF_BOOKSHELF));
        addEnchantingRecipeMaterial(Enchantment.MULTISHOT, List.of(Material.SHEARS));
        addEnchantingRecipeMaterial(Enchantment.PIERCING, List.of(Material.HONEYCOMB));
        addEnchantingRecipeMaterial(Enchantment.POWER, List.of(Material.TARGET), Set.of(LOTS_OF_BOOKSHELF, NETHER));
        addEnchantingRecipeMaterial(Enchantment.PROJECTILE_PROTECTION, List.of(Material.DEEPSLATE,  Material.IRON_INGOT), Set.of(LITTLE_BOOKSHELF));
        addEnchantingRecipeMaterial(Enchantment.PROTECTION, List.of(Material.IRON_BLOCK), Set.of(LOTS_OF_BOOKSHELF));
        addEnchantingRecipeMaterial(Enchantment.PUNCH, List.of(Material.PISTON, Material.STRING), Set.of(LITTLE_BOOKSHELF));
        addEnchantingRecipe(Enchantment.QUICK_CHARGE, List.of(Material.TRIPWIRE_HOOK), List.of(Items.ESOTERIC_SALVE));
        addEnchantingRecipeCustomItem(Enchantment.RESPIRATION, List.of(Items.FISHERMAN_SNORKEL), Set.of(DECENT_BOOKSHELF));
        addEnchantingRecipeMaterial(Enchantment.RIPTIDE, List.of(Material.NAUTILUS_SHELL));
        addEnchantingRecipeMaterial(Enchantment.SHARPNESS, List.of(Material.GRINDSTONE, Material.DIAMOND), Set.of(LOTS_OF_BOOKSHELF));
        addEnchantingRecipeMaterial(Enchantment.SILK_TOUCH, List.of(Material.AMETHYST_SHARD, Material.AMETHYST_SHARD, Material.AMETHYST_SHARD), Set.of(LOTS_OF_BOOKSHELF));
        addEnchantingRecipeMaterial(Enchantment.SMITE, List.of(Material.GLISTERING_MELON_SLICE));
        addEnchantingRecipeMaterial(Enchantment.SOUL_SPEED, List.of(Material.SOUL_SAND, Material.SOUL_SOIL), Set.of(NETHER, LOTS_OF_BOOKSHELF));
        addEnchantingRecipeMaterial(Enchantment.SWEEPING_EDGE, List.of(Material.REDSTONE), Set.of(AMETHYST, DECENT_BOOKSHELF));
        addEnchantingRecipeMaterial(Enchantment.SWIFT_SNEAK, List.of(Material.SCULK_SENSOR), Set.of(LOTS_OF_BOOKSHELF, SCULK));
        addEnchantingRecipeMaterial(Enchantment.THORNS, List.of(Material.IRON_NUGGET, Material.CACTUS));
        addEnchantingRecipeMaterial(Enchantment.UNBREAKING, List.of(Material.IRON_INGOT, Material.DIAMOND), Set.of(LOTS_OF_BOOKSHELF));
        addEnchantingRecipeMaterial(Enchantment.WIND_BURST, List.of(Material.WIND_CHARGE));
    }

    private static void addEnchantingRecipeMaterial(@NotNull Enchantment enchantment, @NotNull List<Material> materials){
        addEnchantingRecipe(enchantment, materials, List.of());
    }

    private static void addEnchantingRecipeMaterial(@NotNull Enchantment enchantment, @NotNull List<Material> materials, @NotNull Set<EnchantingTableUpgrade> tableUpgradeSet){
        addEnchantingRecipe(enchantment, materials, List.of(), tableUpgradeSet);
    }

    private static void addEnchantingRecipeCustomItem(@NotNull Enchantment enchantment, @NotNull List<CustomItem> customItems){
        addEnchantingRecipe(enchantment, List.of(), customItems);
    }

    private static void addEnchantingRecipeCustomItem(@NotNull Enchantment enchantment, @NotNull List<CustomItem> customItems, @NotNull Set<EnchantingTableUpgrade> tableUpgradeSet){
        addEnchantingRecipe(enchantment, List.of(), customItems, tableUpgradeSet);
    }

    private static void addEnchantingRecipe(@NotNull Enchantment enchantment, @NotNull List<Material> materials, @NotNull List<CustomItem> customItems){
        addEnchantingRecipe(enchantment, materials, customItems, Set.of());
    }

    private static void addEnchantingRecipe(@NotNull Enchantment enchantment, @NotNull List<Material> materials, @NotNull List<CustomItem> customItems, @NotNull Set<EnchantingTableUpgrade> tableUpgradeSet){
        List<CustomSingleRecipeChoice> recipeChoices = new ArrayList<>();
        for (Material material : materials){recipeChoices.add(new CustomSingleRecipeChoice(material));}
        for (CustomItem customItem : customItems){recipeChoices.add(new CustomSingleRecipeChoice(customItem));}

        RecipeManager.getInstance().register(
                new EnchantingRecipe(new NamespacedKey(RpgU.getInstance(), enchantment.key().asMinimalString()), enchantment, recipeChoices, tableUpgradeSet)
        );
    }

}
