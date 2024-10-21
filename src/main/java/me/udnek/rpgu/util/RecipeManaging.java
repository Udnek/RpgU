package me.udnek.rpgu.util;

import me.udnek.itemscoreu.customrecipe.RecipeManager;
import me.udnek.itemscoreu.customrecipe.choice.CustomCompatibleRecipeChoice;
import me.udnek.itemscoreu.customrecipe.choice.CustomSingleRecipeChoice;
import me.udnek.itemscoreu.util.VanillaItemManager;
import me.udnek.rpgu.RpgU;
import me.udnek.rpgu.item.Items;
import me.udnek.rpgu.mechanic.alloying.AlloyingRecipe;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Tag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class RecipeManaging {


    public static void run(){

        netheriteIngot();

        blastFurnace();

        leatherArmor();

        ironArmor();

        unregister();

        netheriteTolls();

        netheriteArmor();
    }

    private static void unregister() {
        RecipeManager.getInstance().unregister(NamespacedKey.minecraft("diamond_hoe"));
        RecipeManager.getInstance().unregister(NamespacedKey.minecraft("diamond_axe"));
        RecipeManager.getInstance().unregister(NamespacedKey.minecraft("diamond_pickaxe"));
        RecipeManager.getInstance().unregister(NamespacedKey.minecraft("diamond_shovel"));
        RecipeManager.getInstance().unregister(NamespacedKey.minecraft("diamond_sword"));

        RecipeManager.getInstance().unregister(NamespacedKey.minecraft("netherite_hoe_smithing"));
        RecipeManager.getInstance().unregister(NamespacedKey.minecraft("netherite_axe_smithing"));
        RecipeManager.getInstance().unregister(NamespacedKey.minecraft("netherite_pickaxe_smithing"));
        RecipeManager.getInstance().unregister(NamespacedKey.minecraft("netherite_shovel_smithing"));
        RecipeManager.getInstance().unregister(NamespacedKey.minecraft("netherite_sword_smithing"));

        RecipeManager.getInstance().unregister(NamespacedKey.minecraft("leather_helmet"));
        RecipeManager.getInstance().unregister(NamespacedKey.minecraft("leather_chestplate"));
        RecipeManager.getInstance().unregister(NamespacedKey.minecraft("leather_leggings"));
        RecipeManager.getInstance().unregister(NamespacedKey.minecraft("leather_boots"));

        RecipeManager.getInstance().unregister(NamespacedKey.minecraft("iron_helmet"));
        RecipeManager.getInstance().unregister(NamespacedKey.minecraft("iron_chestplate"));
        RecipeManager.getInstance().unregister(NamespacedKey.minecraft("iron_leggings"));
        RecipeManager.getInstance().unregister(NamespacedKey.minecraft("iron_boots"));

        RecipeManager.getInstance().unregister(NamespacedKey.minecraft("diamond_helmet"));
        RecipeManager.getInstance().unregister(NamespacedKey.minecraft("diamond_chestplate"));
        RecipeManager.getInstance().unregister(NamespacedKey.minecraft("diamond_leggings"));
        RecipeManager.getInstance().unregister(NamespacedKey.minecraft("diamond_boots"));

        RecipeManager.getInstance().unregister(NamespacedKey.minecraft("netherite_helmet_smithing"));
        RecipeManager.getInstance().unregister(NamespacedKey.minecraft("netherite_chestplate_smithing"));
        RecipeManager.getInstance().unregister(NamespacedKey.minecraft("netherite_leggings_smithing"));
        RecipeManager.getInstance().unregister(NamespacedKey.minecraft("netherite_boots_smithing"));

        RecipeManager.getInstance().unregister(NamespacedKey.minecraft("blast_furnace"));

        RecipeManager.getInstance().unregister(NamespacedKey.minecraft("netherite_ingot"));

        VanillaItemManager.getInstance().disableVanillaMaterial(Material.STONE_SWORD);
        VanillaItemManager.getInstance().disableVanillaMaterial(Material.STONE_PICKAXE);
        VanillaItemManager.getInstance().disableVanillaMaterial(Material.STONE_AXE);
        VanillaItemManager.getInstance().disableVanillaMaterial(Material.STONE_SHOVEL);
        VanillaItemManager.getInstance().disableVanillaMaterial(Material.STONE_HOE);
    }

    private  static  void netheriteArmor(){
        CustomCompatibleRecipeChoice fuel = new CustomCompatibleRecipeChoice(Set.of(), Tag.ITEMS_COALS.getValues());
        List<CustomSingleRecipeChoice> alloys = List.of(new CustomSingleRecipeChoice(Material.NETHERITE_INGOT));

        AlloyingRecipe recipe = new AlloyingRecipe(
                new NamespacedKey(RpgU.getInstance(), "netherite_helmet"),
                alloys, fuel,
                new CustomSingleRecipeChoice(Material.DIAMOND_HELMET),
                new ItemStack(Material.NETHERITE_HELMET)
        );
        RecipeManager.getInstance().register(recipe);

        recipe = new AlloyingRecipe(
                new NamespacedKey(RpgU.getInstance(), "netherite_chestplate"),
                alloys, fuel,
                new CustomSingleRecipeChoice(Material.DIAMOND_CHESTPLATE),
                new ItemStack(Material.NETHERITE_CHESTPLATE)
        );
        RecipeManager.getInstance().register(recipe);

        recipe = new AlloyingRecipe(
                new NamespacedKey(RpgU.getInstance(), "netherite_leggings"),
                alloys, fuel,
                new CustomSingleRecipeChoice(Material.DIAMOND_LEGGINGS),
                new ItemStack(Material.NETHERITE_LEGGINGS)
        );
        RecipeManager.getInstance().register(recipe);

        recipe = new AlloyingRecipe(
                new NamespacedKey(RpgU.getInstance(), "netherite_boots"),
                alloys, fuel,
                new CustomSingleRecipeChoice(Material.DIAMOND_BOOTS),
                    new ItemStack(Material.NETHERITE_BOOTS)
        );
        RecipeManager.getInstance().register(recipe);
    }

    private  static  void netheriteTolls(){
        CustomCompatibleRecipeChoice fuel = new CustomCompatibleRecipeChoice(Set.of(), Tag.ITEMS_COALS.getValues());
        List<CustomSingleRecipeChoice> alloys = List.of(new CustomSingleRecipeChoice(Material.NETHERITE_INGOT));

        AlloyingRecipe recipe = new AlloyingRecipe(
                new NamespacedKey(RpgU.getInstance(), "netherite_hoe"),
                alloys, fuel,
                new CustomSingleRecipeChoice(Material.DIAMOND_HOE),
                new ItemStack(Material.NETHERITE_HOE)
        );
        RecipeManager.getInstance().register(recipe);

        recipe = new AlloyingRecipe(
                new NamespacedKey(RpgU.getInstance(), "netherite_axe"),
                alloys, fuel,
                new CustomSingleRecipeChoice(Material.DIAMOND_AXE),
                new ItemStack(Material.NETHERITE_AXE)
        );
        RecipeManager.getInstance().register(recipe);

        recipe = new AlloyingRecipe(
                new NamespacedKey(RpgU.getInstance(), "netherite_pickaxe"),
                alloys, fuel,
                new CustomSingleRecipeChoice(Material.DIAMOND_PICKAXE),
                new ItemStack(Material.NETHERITE_PICKAXE)
        );
        RecipeManager.getInstance().register(recipe);

        recipe = new AlloyingRecipe(
                new NamespacedKey(RpgU.getInstance(), "netherite_shovel"),
                alloys, fuel,
                new CustomSingleRecipeChoice(Material.DIAMOND_SHOVEL),
                new ItemStack(Material.NETHERITE_SHOVEL)
        );
        RecipeManager.getInstance().register(recipe);

        recipe = new AlloyingRecipe(
                new NamespacedKey(RpgU.getInstance(), "netherite_sword"),
                alloys, fuel,
                new CustomSingleRecipeChoice(Material.DIAMOND_SWORD),
                new ItemStack(Material.NETHERITE_SWORD)
        );
        RecipeManager.getInstance().register(recipe);
    }

    private static void ironArmor() {
        RecipeChoice.MaterialChoice iron = new RecipeChoice.MaterialChoice(Material.IRON_INGOT);
        RecipeChoice.MaterialChoice leatherHelmet = new RecipeChoice.MaterialChoice(Material.LEATHER_HELMET);
        RecipeChoice.MaterialChoice leatherChestPlate = new RecipeChoice.MaterialChoice(Material.LEATHER_CHESTPLATE);
        RecipeChoice.MaterialChoice leatherLeggings = new RecipeChoice.MaterialChoice(Material.LEATHER_LEGGINGS);
        RecipeChoice.MaterialChoice leatherBoots = new RecipeChoice.MaterialChoice(Material.LEATHER_BOOTS);

        ShapedRecipe recipeIronChestPlate = new ShapedRecipe(new NamespacedKey(RpgU.getInstance() ,"iron_chestplate"), new ItemStack(Material.IRON_CHESTPLATE));
        recipeIronChestPlate.shape(
                "ILI",
                "III",
                "III");

        recipeIronChestPlate.setIngredient('I', iron);
        recipeIronChestPlate.setIngredient('L', leatherChestPlate);

        RecipeManager.getInstance().register(recipeIronChestPlate);

        ShapedRecipe recipeIronHelmet = new ShapedRecipe(new NamespacedKey(RpgU.getInstance() ,"iron_helmet"), new ItemStack(Material.IRON_HELMET));
        recipeIronHelmet.shape(
                "III",
                "ILI");

        recipeIronHelmet.setIngredient('I', iron);
        recipeIronHelmet.setIngredient('L', leatherHelmet);

        RecipeManager.getInstance().register(recipeIronHelmet);

        ShapedRecipe recipeIronLeggings = new ShapedRecipe(new NamespacedKey(RpgU.getInstance() ,"iron_leggings"), new ItemStack(Material.IRON_LEGGINGS));
        recipeIronLeggings.shape(
                "III",
                "ILI",
                "I I");

        recipeIronLeggings.setIngredient('I', iron);
        recipeIronLeggings.setIngredient('L', leatherLeggings);

        RecipeManager.getInstance().register(recipeIronLeggings);

        ShapedRecipe recipeIronBoots = new ShapedRecipe(new NamespacedKey(RpgU.getInstance() ,"iron_boots"), new ItemStack(Material.IRON_BOOTS));
        recipeIronBoots.shape(
                "ILI",
                "I I");

        recipeIronBoots.setIngredient('I', iron);
        recipeIronBoots.setIngredient('L', leatherBoots);

        RecipeManager.getInstance().register(recipeIronBoots);
    }

    private static void leatherArmor(){
        RecipeChoice.MaterialChoice leather = new RecipeChoice.MaterialChoice(Material.LEATHER);
        RecipeChoice.ExactChoice fabric = new RecipeChoice.ExactChoice(Items.FABRIC.getItem());

        ShapedRecipe recipeLeatherChestPlate = new ShapedRecipe(new NamespacedKey(RpgU.getInstance() ,"leather_chestplate"), new ItemStack(Material.LEATHER_CHESTPLATE));
        recipeLeatherChestPlate.shape(
                "L L",
                "FLF",
                "LFL");

        recipeLeatherChestPlate.setIngredient('L', leather);
        recipeLeatherChestPlate.setIngredient('F', fabric);

        RecipeManager.getInstance().register(recipeLeatherChestPlate);

        ShapedRecipe recipeLeatherHelmet = new ShapedRecipe(new NamespacedKey(RpgU.getInstance() ,"leather_helmet"), new ItemStack(Material.LEATHER_HELMET));
        recipeLeatherHelmet.shape(
                "FLF",
                "L L");

        recipeLeatherHelmet.setIngredient('L', leather);
        recipeLeatherHelmet.setIngredient('F', fabric);

        RecipeManager.getInstance().register(recipeLeatherHelmet);

        ShapedRecipe recipeLeatherLeggings = new ShapedRecipe(new NamespacedKey(RpgU.getInstance() ,"leather_leggings"), new ItemStack(Material.LEATHER_LEGGINGS));
        recipeLeatherLeggings.shape(
                "LFL",
                "F F",
                "L L");

        recipeLeatherLeggings.setIngredient('L', leather);
        recipeLeatherLeggings.setIngredient('F', fabric);

        RecipeManager.getInstance().register(recipeLeatherLeggings);

        ShapedRecipe recipeLeatherBoots = new ShapedRecipe(new NamespacedKey(RpgU.getInstance() ,"leather_boots"), new ItemStack(Material.LEATHER_BOOTS));
        recipeLeatherBoots.shape(
                "F F",
                "L L");

        recipeLeatherBoots.setIngredient('L', leather);
        recipeLeatherBoots.setIngredient('F', fabric);

        RecipeManager.getInstance().register(recipeLeatherBoots);
    }

    private static void blastFurnace() {
        ShapedRecipe recipeBlastFurnace = new ShapedRecipe(new NamespacedKey(RpgU.getInstance() ,"blast_furnace"), new ItemStack(Material.BLAST_FURNACE));
        recipeBlastFurnace.shape(
                "SSS",
                "FFA",
                "BBB");

        RecipeChoice.MaterialChoice bricks = new RecipeChoice.MaterialChoice(Material.BRICKS);
        RecipeChoice.MaterialChoice furnace = new RecipeChoice.MaterialChoice(Material.FURNACE);
        RecipeChoice.MaterialChoice smoothStone = new RecipeChoice.MaterialChoice(Material.SMOOTH_STONE);
        RecipeChoice.MaterialChoice amethystBlock = new RecipeChoice.MaterialChoice(Material.AMETHYST_BLOCK);
        recipeBlastFurnace.setIngredient('F', furnace);
        recipeBlastFurnace.setIngredient('S', smoothStone);
        recipeBlastFurnace.setIngredient('B', bricks);
        recipeBlastFurnace.setIngredient('A', amethystBlock);

        RecipeManager.getInstance().register(recipeBlastFurnace);
    }

    private static void netheriteIngot() {
        List<CustomSingleRecipeChoice> alloys = new ArrayList<>();
        CustomSingleRecipeChoice magnetiteIngot = new CustomSingleRecipeChoice(Items.MAGNETITE_INGOT);
        alloys.add(magnetiteIngot);
        alloys.add(magnetiteIngot);
        alloys.add(magnetiteIngot);
        CustomSingleRecipeChoice netheriteScrap = new CustomSingleRecipeChoice(Material.NETHERITE_SCRAP);
        alloys.add(netheriteScrap);
        alloys.add(netheriteScrap);
        alloys.add(netheriteScrap);

        AlloyingRecipe netheriteRecipe = new AlloyingRecipe(
                new NamespacedKey(RpgU.getInstance(), "netherite_ingot"),
                alloys,
                new CustomSingleRecipeChoice(Items.BLAST_COAL),
                new CustomSingleRecipeChoice(Items.INGOT_MOLD),
                new ItemStack(Material.NETHERITE_INGOT)
        );

        RecipeManager.getInstance().register(netheriteRecipe);
    }
}
