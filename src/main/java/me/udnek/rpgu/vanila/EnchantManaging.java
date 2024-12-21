package me.udnek.rpgu.vanila;

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

public class EnchantManaging {
    public static void run(){
        addEnchantingRecipeMaterial("aqua_affinity", Enchantment.AQUA_AFFINITY, List.of(Material.SEAGRASS));
        addEnchantingRecipeMaterial("bane_of_arthropods", Enchantment.BANE_OF_ARTHROPODS, List.of(Material.STRING));
        addEnchantingRecipeCustomItem("blast_protection", Enchantment.BLAST_PROTECTION, List.of(Items.BLAST_COAL));
        addEnchantingRecipeMaterial("breach", Enchantment.BREACH, List.of(Material.BREEZE_ROD, Material.IRON_AXE), Set.of(EnchantingTableUpgrade.TRIAL_CHAMBER));
        addEnchantingRecipeMaterial("channeling", Enchantment.CHANNELING, List.of(Material.LIGHTNING_ROD), Set.of(EnchantingTableUpgrade.WATER));
        addEnchantingRecipeMaterial("curse_of_binding", Enchantment.BINDING_CURSE, List.of(Material.NETHER_BRICK));
        addEnchantingRecipeMaterial("curse_of_vanishing", Enchantment.VANISHING_CURSE, List.of());//TODO
        addEnchantingRecipeMaterial("density", Enchantment.DENSITY, List.of(Material.BREEZE_ROD, Material.LADDER), Set.of(EnchantingTableUpgrade.TRIAL_CHAMBER));
        addEnchantingRecipeMaterial("depth_strider", Enchantment.DEPTH_STRIDER, List.of(Material.SOUL_SAND), Set.of(EnchantingTableUpgrade.NETHER));
        addEnchantingRecipeMaterial("efficiency", Enchantment.EFFICIENCY, List.of(Material.GOLDEN_PICKAXE));
        addEnchantingRecipeMaterial("feather_falling", Enchantment.FEATHER_FALLING, List.of(Material.FEATHER));
        addEnchantingRecipeMaterial("fire_aspect", Enchantment.FIRE_ASPECT, List.of(Material.TORCHFLOWER, Material.STICK));
        addEnchantingRecipeMaterial("fire_protection", Enchantment.FIRE_PROTECTION, List.of(Material.MAGMA_CREAM));
        addEnchantingRecipeMaterial("flame", Enchantment.FLAME, List.of(Material.TORCHFLOWER, Material.FLINT));
        addEnchantingRecipeMaterial("fortune", Enchantment.FORTUNE, List.of(Material.RABBIT_FOOT));
        addEnchantingRecipeMaterial("frost_walker", Enchantment.FROST_WALKER, List.of(Material.ICE, Material.PACKED_ICE));
        addEnchantingRecipeMaterial("impaling", Enchantment.IMPALING, List.of(Material.KELP), Set.of(EnchantingTableUpgrade.WATER));
        addEnchantingRecipeMaterial("infinity", Enchantment.INFINITY, List.of(Material.WATER_BUCKET, Material.LAVA_BUCKET));
        addEnchantingRecipeMaterial("knockback", Enchantment.KNOCKBACK, List.of(Material.PISTON, Material.STICK));
        addEnchantingRecipeMaterial("looting", Enchantment.LOOTING, List.of(Material.ENDER_EYE));
        addEnchantingRecipeCustomItem("loyalty", Enchantment.LOYALTY, List.of(Items.WOLF_PELT), Set.of(EnchantingTableUpgrade.WATER));
        addEnchantingRecipeMaterial("luck_of_the_sea", Enchantment.LUCK_OF_THE_SEA, List.of(Material.TROPICAL_FISH));
        addEnchantingRecipeMaterial("lure", Enchantment.LURE, List.of(Material.PUFFERFISH));
        addEnchantingRecipeMaterial("mending", Enchantment.MENDING, List.of(Material.NETHER_STAR));
        addEnchantingRecipeMaterial("multishot", Enchantment.MULTISHOT, List.of(Material.GOLDEN_PICKAXE));
        addEnchantingRecipeMaterial("efficiency", Enchantment.EFFICIENCY, List.of(Material.GOLDEN_PICKAXE));
        addEnchantingRecipeMaterial("efficiency", Enchantment.EFFICIENCY, List.of(Material.GOLDEN_PICKAXE));
        addEnchantingRecipeMaterial("efficiency", Enchantment.EFFICIENCY, List.of(Material.GOLDEN_PICKAXE));
        addEnchantingRecipeMaterial("efficiency", Enchantment.EFFICIENCY, List.of(Material.GOLDEN_PICKAXE));
        addEnchantingRecipeMaterial("efficiency", Enchantment.EFFICIENCY, List.of(Material.GOLDEN_PICKAXE));
        addEnchantingRecipeMaterial("efficiency", Enchantment.EFFICIENCY, List.of(Material.GOLDEN_PICKAXE));
        addEnchantingRecipeMaterial("efficiency", Enchantment.EFFICIENCY, List.of(Material.GOLDEN_PICKAXE));
        addEnchantingRecipeMaterial("efficiency", Enchantment.EFFICIENCY, List.of(Material.GOLDEN_PICKAXE));
        addEnchantingRecipeMaterial("efficiency", Enchantment.EFFICIENCY, List.of(Material.GOLDEN_PICKAXE));
        addEnchantingRecipeMaterial("efficiency", Enchantment.EFFICIENCY, List.of(Material.GOLDEN_PICKAXE));
        addEnchantingRecipeMaterial("efficiency", Enchantment.EFFICIENCY, List.of(Material.GOLDEN_PICKAXE));
        addEnchantingRecipeMaterial("efficiency", Enchantment.EFFICIENCY, List.of(Material.GOLDEN_PICKAXE));
        addEnchantingRecipeMaterial("efficiency", Enchantment.EFFICIENCY, List.of(Material.GOLDEN_PICKAXE));
        addEnchantingRecipeMaterial("efficiency", Enchantment.EFFICIENCY, List.of(Material.GOLDEN_PICKAXE));
        addEnchantingRecipeMaterial("efficiency", Enchantment.EFFICIENCY, List.of(Material.GOLDEN_PICKAXE));
        addEnchantingRecipeMaterial("efficiency", Enchantment.EFFICIENCY, List.of(Material.GOLDEN_PICKAXE));
        addEnchantingRecipeMaterial("efficiency", Enchantment.EFFICIENCY, List.of(Material.GOLDEN_PICKAXE));
        addEnchantingRecipeMaterial("efficiency", Enchantment.EFFICIENCY, List.of(Material.GOLDEN_PICKAXE));
        addEnchantingRecipeMaterial("efficiency", Enchantment.EFFICIENCY, List.of(Material.GOLDEN_PICKAXE));
        addEnchantingRecipeMaterial("efficiency", Enchantment.EFFICIENCY, List.of(Material.GOLDEN_PICKAXE));
        addEnchantingRecipeMaterial("efficiency", Enchantment.EFFICIENCY, List.of(Material.GOLDEN_PICKAXE));
        addEnchantingRecipeMaterial("efficiency", Enchantment.EFFICIENCY, List.of(Material.GOLDEN_PICKAXE));
        addEnchantingRecipeMaterial("efficiency", Enchantment.EFFICIENCY, List.of(Material.GOLDEN_PICKAXE));
    }

    private static void addEnchantingRecipeMaterial(@NotNull String key, @NotNull Enchantment enchantment, @NotNull List<Material> materials){
        addEnchantingRecipe(key, enchantment, materials, List.of());
    }

    private static void addEnchantingRecipeMaterial(@NotNull String key, @NotNull Enchantment enchantment, @NotNull List<Material> materials, @NotNull Set<EnchantingTableUpgrade> tableUpgradeSet){
        addEnchantingRecipe(key, enchantment, materials, List.of(), tableUpgradeSet);
    }

    private static void addEnchantingRecipeCustomItem(@NotNull String key, @NotNull Enchantment enchantment, @NotNull List<CustomItem> customItems){
        addEnchantingRecipe(key, enchantment, List.of(), customItems);
    }

    private static void addEnchantingRecipeCustomItem(@NotNull String key, @NotNull Enchantment enchantment, @NotNull List<CustomItem> customItems, @NotNull Set<EnchantingTableUpgrade> tableUpgradeSet){
        addEnchantingRecipe(key, enchantment, List.of(), customItems, tableUpgradeSet);
    }

    private static void addEnchantingRecipe(@NotNull String key, @NotNull Enchantment enchantment, @NotNull List<Material> materials, @NotNull List<CustomItem> customItems){
        addEnchantingRecipe(key, enchantment, materials, customItems, Set.of());
    }

    private static void addEnchantingRecipe(@NotNull String key, @NotNull Enchantment enchantment, @NotNull List<Material> materials, @NotNull List<CustomItem> customItems, @NotNull Set<EnchantingTableUpgrade> tableUpgradeSet, CustomSingleRecipeChoice ... customSingleRecipeChoices ){
        List<CustomSingleRecipeChoice> recipeChoices = new ArrayList<>();
        for (Material material : materials){recipeChoices.add(new CustomSingleRecipeChoice(material));}
        for (CustomItem customItem : customItems){recipeChoices.add(new CustomSingleRecipeChoice(customItem));}

        RecipeManager.getInstance().register(
                new EnchantingRecipe(new NamespacedKey(RpgU.getInstance(), key), enchantment, recipeChoices, tableUpgradeSet)
        );
    }

}
