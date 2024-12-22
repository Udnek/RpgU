package me.udnek.rpgu.vanila;

import me.udnek.itemscoreu.customitem.CustomItem;
import me.udnek.itemscoreu.customrecipe.RecipeManager;
import me.udnek.itemscoreu.customrecipe.choice.CustomCompatibleRecipeChoice;
import me.udnek.itemscoreu.customrecipe.choice.CustomRecipeChoice;
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
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class RecipeManaging {
    public static void run(){
        replaceRecipe(Material.LODESTONE, "lodestone", new String[]{ "BBB", "BMB", "BBB"}, Map.of('B', Material.STONE_BRICKS), Map.of('M', Items.MAGNETITE_INGOT));
        replaceRecipe(Material.POWERED_RAIL, "powered_rail", new String[]{"M M", "MSM", "MRM"}, Map.of('S', Material.STICK, 'R', Material.REDSTONE), Map.of('M', Items.MAGNETITE_INGOT));
        replaceRecipe(Material.COMPASS, "compass", new String[]{" I ", "IMI", " I "}, Map.of('I', Material.IRON_INGOT), Map.of('M', Items.MAGNETITE_INGOT));
        ////////////////////////////////////////////////////////////////////////////////////////////
        replaceMaterialRecipe(Material.IRON_CHESTPLATE, "iron_chestplate", new String[]{"ILI", "III", "III"}, Map.of('I', Material.IRON_INGOT, 'L', Material.LEATHER_CHESTPLATE));
        replaceMaterialRecipe(Material.IRON_HELMET, "iron_helmet", new String[]{"III", "ILI"}, Map.of('I', Material.IRON_INGOT, 'L', Material.LEATHER_HELMET));
        replaceMaterialRecipe(Material.IRON_LEGGINGS, "iron_leggings", new String[]{"III", "ILI", "I I"}, Map.of('I', Material.IRON_INGOT, 'L', Material.LEATHER_LEGGINGS));
        replaceMaterialRecipe(Material.IRON_BOOTS, "iron_boots", new String[]{"ILI", "I I"}, Map.of('I', Material.IRON_INGOT, 'L', Material.LEATHER_BOOTS));
        ////////////////////////////////////////////////////////////////////////////////////////////
        replaceRecipe(Material.LEATHER_CHESTPLATE, "leather_chestplate", new String[]{"L L", "FLF", "LFL"}, Map.of( 'L', Material.LEATHER), Map.of('F', Items.FABRIC));
        replaceRecipe(Material.LEATHER_HELMET, "leather_helmet", new String[]{"FLF", "L L"}, Map.of('L', Material.LEATHER), Map.of('F', Items.FABRIC));
        replaceRecipe(Material.LEATHER_LEGGINGS, "leather_leggings", new String[]{"LFL", "F F", "L L"}, Map.of('L', Material.LEATHER), Map.of('F', Items.FABRIC));
        replaceRecipe(Material.LEATHER_BOOTS, "leather_boots", new String[]{"F F", "L L"}, Map.of('L', Material.LEATHER), Map.of('F', Items.FABRIC));
        ////////////////////////////////////////////////////
        replaceMaterialRecipe(Material.BLAST_FURNACE, "blast_furnace", new String[]{"SSS", "FFA", "BBB"}, Map.of('F', Material.FURNACE,'S', Material.SMOOTH_STONE,'B', Material.BRICKS,'A', Material.AMETHYST_BLOCK));
        ////////////////////////////////////////////////////
        alloyingRecipe(Material.NETHERITE_INGOT, "netherite_ingot", getCustomSingleRecipeChoices(Map.of(Material.NETHERITE_SCRAP, 3), Map.of(Items.MAGNETITE_INGOT, 3)),
                getCustomRecipeChoice(Set.of(Items.BLAST_COAL), Set.of()), getCustomRecipeChoice(Set.of(Items.INGOT_MOLD), Set.of()));
        ///////////////////////////////////////////////////
        alloyingRecipe(Material.NETHERITE_HELMET, "netherite_helmet_smithing", getCustomSingleRecipeChoices(Map.of(Material.NETHERITE_INGOT, 1), Map.of()),
                getCustomRecipeChoiceMaterialTag(Set.of(), Set.of(), Tag.ITEMS_COALS), getCustomRecipeChoice(Set.of(Items.FERRUDAM_HELMET), Set.of()));
        alloyingRecipe(Material.NETHERITE_CHESTPLATE, "netherite_chestplate_smithing", getCustomSingleRecipeChoices(Map.of(Material.NETHERITE_INGOT, 1), Map.of()),
                getCustomRecipeChoiceMaterialTag(Set.of(), Set.of(), Tag.ITEMS_COALS), getCustomRecipeChoice(Set.of(Items.FERRUDAM_CHESTPLATE), Set.of()));
        alloyingRecipe(Material.NETHERITE_LEGGINGS, "netherite_leggings_smithing", getCustomSingleRecipeChoices(Map.of(Material.NETHERITE_INGOT, 1), Map.of()),
                getCustomRecipeChoiceMaterialTag(Set.of(), Set.of(), Tag.ITEMS_COALS), getCustomRecipeChoice(Set.of(Items.FERRUDAM_LEGGINGS), Set.of()));
        alloyingRecipe(Material.NETHERITE_BOOTS, "netherite_boots_smithing", getCustomSingleRecipeChoices(Map.of(Material.NETHERITE_INGOT, 1), Map.of()),
                getCustomRecipeChoiceMaterialTag(Set.of(), Set.of(), Tag.ITEMS_COALS), getCustomRecipeChoice(Set.of(Items.FERRUDAM_BOOTS), Set.of()));
        /////////////////////////////////////////////
        alloyingRecipe(Material.NETHERITE_HOE, "netherite_hoe_smithing", getCustomSingleRecipeChoices(Map.of(Material.NETHERITE_INGOT, 1), Map.of()),
                getCustomRecipeChoiceMaterialTag(Set.of(), Set.of(), Tag.ITEMS_COALS), getCustomRecipeChoice(Set.of(Items.FERRUDAM_HOE), Set.of()));
        alloyingRecipe(Material.NETHERITE_AXE, "netherite_axe_smithing", getCustomSingleRecipeChoices(Map.of(Material.NETHERITE_INGOT, 1), Map.of()),
                getCustomRecipeChoiceMaterialTag(Set.of(), Set.of(), Tag.ITEMS_COALS), getCustomRecipeChoice(Set.of(Items.FERRUDAM_AXE), Set.of()));
        alloyingRecipe(Material.NETHERITE_PICKAXE, "netherite_pickaxe_smithing", getCustomSingleRecipeChoices(Map.of(Material.NETHERITE_INGOT, 1), Map.of()),
                getCustomRecipeChoiceMaterialTag(Set.of(), Set.of(), Tag.ITEMS_COALS), getCustomRecipeChoice(Set.of(Items.FERRUDAM_PICKAXE), Set.of()));
        alloyingRecipe(Material.NETHERITE_SHOVEL, "netherite_shovel_smithing", getCustomSingleRecipeChoices(Map.of(Material.NETHERITE_INGOT, 1), Map.of()),
                getCustomRecipeChoiceMaterialTag(Set.of(), Set.of(), Tag.ITEMS_COALS), getCustomRecipeChoice(Set.of(Items.FERRUDAM_SHOVEL), Set.of()));
        alloyingRecipe(Material.NETHERITE_SWORD, "netherite_sword_smithing", getCustomSingleRecipeChoices(Map.of(Material.NETHERITE_INGOT, 1), Map.of()),
                getCustomRecipeChoiceMaterialTag(Set.of(), Set.of(), Tag.ITEMS_COALS), getCustomRecipeChoice(Set.of(Items.FERRUDAM_SWORD), Set.of()));
        ////////////////////////////////////////////
        replaceMaterialRecipe(Material.CHAINMAIL_CHESTPLATE, "chainmail_chestplate", new String[]{"C C", "CCC", "CCC"}, Map.of('C', Material.CHAIN));
        replaceMaterialRecipe(Material.CHAINMAIL_HELMET, "chainmail_helmet", new String[]{"CCC", "C C"}, Map.of('C', Material.CHAIN));
        replaceMaterialRecipe(Material.CHAINMAIL_LEGGINGS, "chainmail_leggings", new String[]{"CCC", "C C", "C C"}, Map.of('C', Material.CHAIN));
        replaceMaterialRecipe(Material.CHAINMAIL_BOOTS, "chainmail_boots", new String[]{"C C", "C C"}, Map.of('C', Material.CHAIN));
        /////////////////////////////////////////////////
        replaceMaterialRecipe(Material.GOLDEN_CHESTPLATE, "golden_chestplate", new String[]{"GLG", "GGG", "GGG"}, Map.of('G', Material.GOLD_INGOT, 'L', Material.LEATHER_CHESTPLATE));
        replaceMaterialRecipe(Material.GOLDEN_HELMET, "golden_helmet", new String[]{"GGG", "GLG"}, Map.of('G', Material.GOLD_INGOT, 'L', Material.LEATHER_HELMET));
        replaceMaterialRecipe(Material.GOLDEN_LEGGINGS, "golden_leggings", new String[]{"GGG", "GLG", "G G"}, Map.of('G', Material.GOLD_INGOT, 'L', Material.LEATHER_LEGGINGS));
        replaceMaterialRecipe(Material.GOLDEN_BOOTS, "golden_boots", new String[]{"GLG", "G G"}, Map.of('G', Material.GOLD_INGOT, 'L', Material.LEATHER_BOOTS));
        //////////////////////////////////////////////
        replaceBedRecipe(Material.BLACK_BED, "black_bed", Material.BLACK_WOOL);
        replaceBedRecipe(Material.GRAY_BED, "gray_bed", Material.GRAY_WOOL);
        replaceBedRecipe(Material.LIGHT_GRAY_BED, "light_gray_bed", Material.LIGHT_GRAY_WOOL);
        replaceBedRecipe(Material.BLUE_BED, "blue_bed", Material.BLUE_WOOL);
        replaceBedRecipe(Material.LIGHT_BLUE_BED, "light_blue_bed", Material.LIGHT_BLUE_WOOL);
        replaceBedRecipe(Material.BROWN_BED, "brown_bed", Material.BROWN_BED);
        replaceBedRecipe(Material.CYAN_BED, "cyan_bed", Material.CYAN_BED);
        replaceBedRecipe(Material.GREEN_BED, "green_bed", Material.GREEN_WOOL);
        replaceBedRecipe(Material.LIME_BED, "lime_bed", Material.LIME_WOOL);
        replaceBedRecipe(Material.MAGENTA_BED, "magenta_bed", Material.MAGENTA_WOOL);
        replaceBedRecipe(Material.ORANGE_BED, "orange_bed", Material.ORANGE_WOOL);
        replaceBedRecipe(Material.PINK_BED, "pink_bed", Material.PINK_WOOL);
        replaceBedRecipe(Material.PURPLE_BED, "purple_bed", Material.PURPLE_WOOL);
        replaceBedRecipe(Material.WHITE_BED, "white_bed", Material.WHITE_WOOL);
        replaceBedRecipe(Material.YELLOW_BED, "yellow_bed", Material.YELLOW_WOOL);
        replaceBedRecipe(Material.RED_BED, "red_bed", Material.RED_WOOL);
        //////////////////////////////////////////////

        unregister();
    }

    private static void unregister() {
        RecipeManager.getInstance().unregister(NamespacedKey.minecraft("diamond_hoe"));
        RecipeManager.getInstance().unregister(NamespacedKey.minecraft("diamond_axe"));
        RecipeManager.getInstance().unregister(NamespacedKey.minecraft("diamond_pickaxe"));
        RecipeManager.getInstance().unregister(NamespacedKey.minecraft("diamond_shovel"));
        RecipeManager.getInstance().unregister(NamespacedKey.minecraft("diamond_sword"));

        RecipeManager.getInstance().unregister(NamespacedKey.minecraft("diamond_helmet"));
        RecipeManager.getInstance().unregister(NamespacedKey.minecraft("diamond_chestplate"));
        RecipeManager.getInstance().unregister(NamespacedKey.minecraft("diamond_leggings"));
        RecipeManager.getInstance().unregister(NamespacedKey.minecraft("diamond_boots"));

        VanillaItemManager.getInstance().disableVanillaMaterial(Material.STONE_SWORD);
        VanillaItemManager.getInstance().disableVanillaMaterial(Material.STONE_PICKAXE);
        VanillaItemManager.getInstance().disableVanillaMaterial(Material.STONE_AXE);
        VanillaItemManager.getInstance().disableVanillaMaterial(Material.STONE_SHOVEL);
        VanillaItemManager.getInstance().disableVanillaMaterial(Material.STONE_HOE);

        VanillaItemManager.getInstance().disableVanillaMaterial(Material.TURTLE_HELMET);

        VanillaItemManager.getInstance().disableVanillaMaterial(Material.WOLF_ARMOR);
    }

    private static @NotNull List<CustomSingleRecipeChoice> getCustomSingleRecipeChoices(@NotNull Map<Material, Integer> materials, @NotNull Map<CustomItem, Integer> customItems) {
        List<CustomSingleRecipeChoice> alloys = new ArrayList<>();
        for (Map.Entry<Material, Integer> entry : materials.entrySet()){
            CustomSingleRecipeChoice ingredient = new CustomSingleRecipeChoice(entry.getKey());
            for (int i = 0; i < entry.getValue(); i++){
                alloys.add(ingredient);
            }
        }
        for (Map.Entry<CustomItem, Integer> entry : customItems.entrySet()){
            CustomSingleRecipeChoice ingredient = new CustomSingleRecipeChoice(entry.getKey());
            for (int i = 0; i < entry.getValue(); i++){
                alloys.add(ingredient);
            }
        }
        return alloys;
    }

    private static @NotNull CustomRecipeChoice getCustomRecipeChoice(@NotNull Set<CustomItem> customItemsSet, @NotNull Set<Material> materialsSet){
        return getCustomRecipeChoice(customItemsSet, materialsSet, null, null);
    }

    private static @NotNull CustomRecipeChoice getCustomRecipeChoiceMaterialTag(@NotNull Set<CustomItem> customItemsSet, @NotNull Set<Material> materialsSet, @Nullable Tag<Material> materialsTag){
        return getCustomRecipeChoice(customItemsSet, materialsSet, null, materialsTag);
    }

    private static @NotNull CustomRecipeChoice getCustomRecipeChoiceCustomItemTag(@NotNull Set<CustomItem> customItemsSet, @NotNull Set<Material> materialsSet, @Nullable Tag<CustomItem> customItemsTag){
        return getCustomRecipeChoice(customItemsSet, materialsSet, customItemsTag, null);
    }

    private static @NotNull CustomRecipeChoice getCustomRecipeChoice(@NotNull Set<CustomItem> customItemsSet, @NotNull Set<Material> materialsSet, @Nullable Tag<CustomItem> customItemsTag, @Nullable Tag<Material> materialsTag){
        HashSet<CustomItem> customItemHashSet = new HashSet<>(customItemsSet);
        HashSet<Material> materialHashSet = new HashSet<>(materialsSet);

        if (customItemsTag != null) {customItemHashSet.addAll(customItemsTag.getValues());}
        if (materialsTag != null) {materialHashSet.addAll(materialsTag.getValues());}

        return new CustomCompatibleRecipeChoice(customItemHashSet, materialHashSet);
    }

    private static void alloyingRecipe(@NotNull Material material, @NotNull String key, @NotNull List<CustomSingleRecipeChoice> alloys, @NotNull CustomRecipeChoice fuel, @NotNull CustomRecipeChoice addition){
        RecipeManager.getInstance().unregister(NamespacedKey.minecraft(key));

        AlloyingRecipe recipe = new AlloyingRecipe(
                new NamespacedKey(RpgU.getInstance(), key),
                alloys,
                fuel,
                addition,
                new ItemStack(material)
        );

        RecipeManager.getInstance().register(recipe);
    }

    private static void replaceMaterialRecipe(@NotNull Material materialCraft, @NotNull String key, @NotNull String[] shape, @NotNull Map<Character, Material> materials){
        replaceRecipe(materialCraft, key, shape, materials, Map.of());
    }

    private static void replaceRecipeCustomItem(@NotNull Material materialCraft, @NotNull String key, @NotNull String[] shape, @NotNull Map<Character, CustomItem> customItems){
        replaceRecipe(materialCraft, key, shape, Map.of(), customItems);
    }


    private static void replaceRecipe(@NotNull Material materialCraft, @NotNull String key, @NotNull String[] shape, @NotNull Map<Character, Material> materials, @NotNull Map<Character, CustomItem> customItems){
        RecipeManager.getInstance().unregister(NamespacedKey.minecraft(key));

        ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(RpgU.getInstance(), key), new ItemStack(materialCraft));
        recipe.shape(shape);

        for (Map.Entry<Character, Material> material : materials.entrySet()) {
            recipe.setIngredient(material.getKey(), new RecipeChoice.MaterialChoice(material.getValue()));
        }
        for (Map.Entry<Character, CustomItem> customItem : customItems.entrySet()) {
            recipe.setIngredient(customItem.getKey(), new RecipeChoice.ExactChoice(customItem.getValue().getItem()));
        }

        RecipeManager.getInstance().register(recipe);
    }

    private static void replaceBedRecipe(@NotNull Material materialCraft, @NotNull String key, @NotNull Material wool){
        RecipeManager.getInstance().unregister(NamespacedKey.minecraft(key));

        ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(RpgU.getInstance(), key), new ItemStack(materialCraft));
        recipe.shape("FFW", "PPP");

        recipe.setIngredient('W', new RecipeChoice.MaterialChoice(wool));
        recipe.setIngredient('F', new RecipeChoice.ExactChoice(Items.FABRIC.getItem()));
        recipe.setIngredient('P', new RecipeChoice.MaterialChoice(Tag.PLANKS));

        RecipeManager.getInstance().register(recipe);
    }
}
