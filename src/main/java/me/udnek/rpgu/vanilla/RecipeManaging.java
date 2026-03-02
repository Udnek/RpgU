package me.udnek.rpgu.vanilla;

import me.udnek.coreu.custom.item.CustomItem;
import me.udnek.coreu.custom.recipe.RecipeManager;
import me.udnek.coreu.custom.recipe.builder.ShapedRecipeBuilder;
import me.udnek.coreu.custom.recipe.builder.ShapelessRecipeBuilder;
import me.udnek.coreu.custom.recipe.choice.CustomCompatibleRecipeChoice;
import me.udnek.coreu.custom.recipe.choice.CustomRecipeChoice;
import me.udnek.coreu.custom.recipe.choice.CustomSingleRecipeChoice;
import me.udnek.rpgu.RpgU;
import me.udnek.rpgu.item.Items;
import me.udnek.rpgu.mechanic.machine.alloying.AlloyingRecipe;
import me.udnek.rpgu.mechanic.machine.crusher.CrusherRecipe;
import me.udnek.rpgu.util.Utils;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Tag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class RecipeManaging {
    public static void run(){
        new ShapedRecipeBuilder(Material.LODESTONE).shape(new String[]{ "BBB", "BMB", "BBB"}).materialIngredients(Map.of('B', Material.STONE_BRICKS))
                .customItemIngredients(Map.of('M', Items.MAGNETITE_INGOT)).build(RpgU.getInstance());
        new ShapedRecipeBuilder(Material.POWERED_RAIL).shape(new String[]{"M M", "ISI", "MRM"}).materialIngredients(Map.of('S', Material.STICK, 'R',
                Material.REDSTONE, 'I', Material.IRON_INGOT)).customItemIngredients(Map.of('M', Items.MAGNETITE_INGOT)).resultAmount(6).build(RpgU.getInstance());
        new ShapedRecipeBuilder(Material.COMPASS).shape(new String[]{" C ", "CMC", " C "}).materialIngredients(Map.of('C', Material.COPPER_INGOT))
                .customItemIngredients(Map.of('M', Items.MAGNETITE_INGOT)).build(RpgU.getInstance());
        new ShapedRecipeBuilder(Material.BUCKET).shape(new String[]{"C C", " C "}).materialIngredients(Map.of('C', Material.COPPER_INGOT)).build(RpgU.getInstance());
        new ShapedRecipeBuilder(Material.SHEARS).shape(new String[]{" C", "C "}).materialIngredients(Map.of('C', Material.COPPER_INGOT)).build(RpgU.getInstance());
        new ShapedRecipeBuilder(Material.CLOCK).shape(new String[]{" C ", "CRC", " C "}).materialIngredients(Map.of('C', Material.COPPER_INGOT,
                'R', Material.REDSTONE)).build(RpgU.getInstance());
        ////////////////////////////////////////////////////////////////////////////////////////////
        new ShapedRecipeBuilder(Material.IRON_CHESTPLATE).shape(new String[]{"ILI", "III", "III"}).materialIngredients(Map.of('I', Material.IRON_INGOT,
                'L', Material.LEATHER_CHESTPLATE)).build(RpgU.getInstance());
        new ShapedRecipeBuilder(Material.IRON_HELMET).shape(new String[]{"III", "ILI"}).materialIngredients(Map.of('I', Material.IRON_INGOT,
                'L', Material.LEATHER_HELMET)).build(RpgU.getInstance());
        new ShapedRecipeBuilder(Material.IRON_LEGGINGS).shape(new String[]{"III", "ILI", "I I"}).materialIngredients(Map.of('I', Material.IRON_INGOT,
                'L', Material.LEATHER_LEGGINGS)).build(RpgU.getInstance());
        new ShapedRecipeBuilder(Material.IRON_BOOTS).shape(new String[]{"ILI", "I I"}).materialIngredients(Map.of('I', Material.IRON_INGOT,
                'L', Material.LEATHER_BOOTS)).build(RpgU.getInstance());
        ////////////////////////////////////////////////////////////////////////////////////////////
        new ShapedRecipeBuilder(Material.LEATHER_CHESTPLATE).shape(new String[]{"L L", "FLF", "LFL"}).stackIngredients(Map.of( 'L',
                List.of(new ItemStack(Material.LEATHER), Items.WOLF_PELT.getItem()))).customItemIngredients(Map.of('F', Items.FABRIC)).build(RpgU.getInstance());
        new ShapedRecipeBuilder(Material.LEATHER_HELMET).shape(new String[]{"FLF", "L L"}).stackIngredients(Map.of( 'L',
                List.of(new ItemStack(Material.LEATHER), Items.WOLF_PELT.getItem()))).customItemIngredients(Map.of('F', Items.FABRIC)).build(RpgU.getInstance());
        new ShapedRecipeBuilder(Material.LEATHER_LEGGINGS).shape(new String[]{"LFL", "F F", "L L"}).stackIngredients(Map.of( 'L',
                List.of(new ItemStack(Material.LEATHER), Items.WOLF_PELT.getItem()))).customItemIngredients(Map.of('F', Items.FABRIC)).build(RpgU.getInstance());
        new ShapedRecipeBuilder(Material.LEATHER_BOOTS).shape(new String[]{"F F", "L L"}).stackIngredients(Map.of( 'L',
                List.of(new ItemStack(Material.LEATHER), Items.WOLF_PELT.getItem()))).customItemIngredients(Map.of('F', Items.FABRIC)).build(RpgU.getInstance());
        ////////////////////////////////////////////////////
        new ShapedRecipeBuilder(Material.BLAST_FURNACE).shape(new String[]{"SSS", "FFA", "BBB"}).materialIngredients(Map.of('F', Material.FURNACE,
                'S', Material.SMOOTH_STONE,'B', Material.BRICKS,'A', Material.AMETHYST_BLOCK)).build(RpgU.getInstance());
        ////////////////////////////////////////////////////
        new AlloyingRecipeBuilder(Material.NETHERITE_INGOT).addStuffs(Material.NETHERITE_SCRAP, 3).addStuffs(Items.MAGNETITE_INGOT, 3)
                .customItemFuel(Set.of(Items.BLAST_COAL)).customItemAddition(Set.of(Items.INGOT_MOLD)).build(RpgU.getInstance());
        ///////////////////////////////////////////////////
        new AlloyingRecipeBuilder(Material.NETHERITE_HELMET).recipeKey("netherite_helmet_smithing").addStuffs(Material.NETHERITE_INGOT, 1)
                .materialFuel(Tag.ITEMS_COALS.getValues()).customItemAddition(Set.of(Items.FERRUDAM_HELMET)).build(RpgU.getInstance());
        new AlloyingRecipeBuilder(Material.NETHERITE_CHESTPLATE).recipeKey("netherite_chestplate_smithing").addStuffs(Material.NETHERITE_INGOT, 1)
                .materialFuel(Tag.ITEMS_COALS.getValues()).customItemAddition(Set.of(Items.FERRUDAM_CHESTPLATE)).build(RpgU.getInstance());
        new AlloyingRecipeBuilder(Material.NETHERITE_LEGGINGS).recipeKey("netherite_leggings_smithing").addStuffs(Material.NETHERITE_INGOT, 1)
                .materialFuel(Tag.ITEMS_COALS.getValues()).customItemAddition(Set.of(Items.FERRUDAM_LEGGINGS)).build(RpgU.getInstance());
        new AlloyingRecipeBuilder(Material.NETHERITE_BOOTS).recipeKey("netherite_boots_smithing").addStuffs(Material.NETHERITE_INGOT, 1)
                .materialFuel(Tag.ITEMS_COALS.getValues()).customItemAddition(Set.of(Items.FERRUDAM_BOOTS)).build(RpgU.getInstance());
        /////////////////////////////////////////////
        new AlloyingRecipeBuilder(Material.NETHERITE_HOE).recipeKey("netherite_hoe_smithing").addStuffs(Material.NETHERITE_INGOT, 1)
                .materialFuel(Tag.ITEMS_COALS.getValues()).customItemAddition(Set.of(Items.FERRUDAM_HOE)).build(RpgU.getInstance());
        new AlloyingRecipeBuilder(Material.NETHERITE_AXE).recipeKey("netherite_axe_smithing").addStuffs(Material.NETHERITE_INGOT, 1)
                .materialFuel(Tag.ITEMS_COALS.getValues()).customItemAddition(Set.of(Items.FERRUDAM_AXE)).build(RpgU.getInstance());
        new AlloyingRecipeBuilder(Material.NETHERITE_PICKAXE).recipeKey("netherite_pickaxe_smithing").addStuffs(Material.NETHERITE_INGOT, 1)
                .materialFuel(Tag.ITEMS_COALS.getValues()).customItemAddition(Set.of(Items.FERRUDAM_PICKAXE)).build(RpgU.getInstance());
        new AlloyingRecipeBuilder(Material.NETHERITE_SHOVEL).recipeKey("netherite_shovel_smithing").addStuffs(Material.NETHERITE_INGOT, 1)
                .materialFuel(Tag.ITEMS_COALS.getValues()).customItemAddition(Set.of(Items.FERRUDAM_SHOVEL)).build(RpgU.getInstance());
        new AlloyingRecipeBuilder(Material.NETHERITE_SWORD).recipeKey("netherite_sword_smithing").addStuffs(Material.NETHERITE_INGOT, 1)
                .materialFuel(Tag.ITEMS_COALS.getValues()).customItemAddition(Set.of(Items.FERRUDAM_SWORD)).build(RpgU.getInstance());
        ////////////////////////////////////////////
        new ShapedRecipeBuilder(Material.CHAINMAIL_CHESTPLATE).shape(new String[]{"C C", "CCC", "CCC"}).materialIngredients(Map.of('C', Material.IRON_CHAIN))
                .build(RpgU.getInstance());
        new ShapedRecipeBuilder(Material.CHAINMAIL_HELMET).shape(new String[]{"CCC", "C C"}).materialIngredients(Map.of('C', Material.IRON_CHAIN))
                .build(RpgU.getInstance());
        new ShapedRecipeBuilder(Material.CHAINMAIL_LEGGINGS).shape(new String[]{"CCC", "C C", "C C"}).materialIngredients(Map.of('C', Material.IRON_CHAIN))
                .build(RpgU.getInstance());
        new ShapedRecipeBuilder(Material.CHAINMAIL_BOOTS).shape(new String[]{"C C", "C C"}).materialIngredients(Map.of('C', Material.IRON_CHAIN))
                .build(RpgU.getInstance());
        /////////////////////////////////////////////////
        new ShapedRecipeBuilder(Material.GOLDEN_CHESTPLATE).shape(new String[]{"GLG", "GGG", "GGG"}).materialIngredients(Map.of('G', Material.GOLD_INGOT,
                'L', Material.LEATHER_CHESTPLATE)).build(RpgU.getInstance());
        new ShapedRecipeBuilder(Material.GOLDEN_HELMET).shape(new String[]{"GGG", "GLG"}).materialIngredients(Map.of('G', Material.GOLD_INGOT,
                'L', Material.LEATHER_HELMET)).build(RpgU.getInstance());
        new ShapedRecipeBuilder(Material.GOLDEN_LEGGINGS).shape(new String[]{"GGG", "GLG", "G G"}).materialIngredients(Map.of('G', Material.GOLD_INGOT,
                'L', Material.LEATHER_LEGGINGS)).build(RpgU.getInstance());
        new ShapedRecipeBuilder(Material.GOLDEN_BOOTS).shape(new String[]{"GLG", "G G"}).materialIngredients(Map.of('G', Material.GOLD_INGOT,
                'L', Material.LEATHER_BOOTS)).build(RpgU.getInstance());
        //////////////////////////////////////////////
        for (Material material: Tag.BEDS.getValues().stream().sorted().toList()){
            new ShapedRecipeBuilder(material).shape(new String[]{"FFW", "PPP"}).materialIngredients(Map.of('W', Utils.replaceSuffix(material,
                    "_wool"))).customItemIngredients(Map.of('F', Items.FABRIC)).tagIngredients(Map.of('P', Tag.PLANKS)).build(RpgU.getInstance());
        }
        //////////////////////////////////////////////
        for (Material material: Tag.WOODEN_TRAPDOORS.getValues().stream().sorted().toList()){
            new ShapedRecipeBuilder(material).resultAmount(6).shape(new String[]{"PPP", "PPP"})
                    .materialIngredients(Map.of('P', Utils.replaceSuffix(material, "_planks"))).build(RpgU.getInstance());
        }
        new ShapedRecipeBuilder(Material.COPPER_TRAPDOOR).resultAmount(6).shape(new String[]{"PPP", "PPP"})
                .materialIngredients(Map.of('P', Material.COPPER_INGOT)).build(RpgU.getInstance());
        //////////////////////////////////////////////
        new ShapelessRecipeBuilder(Material.ANVIL).addIngredient(Material.IRON_INGOT, 3).
                addIngredient(Material.CHIPPED_ANVIL, 1).build(RpgU.getInstance());
        new ShapelessRecipeBuilder(Material.CHIPPED_ANVIL).addIngredient(Material.IRON_INGOT, 3).
                addIngredient(Material.DAMAGED_ANVIL, 1).build(RpgU.getInstance());
        //////////////////////////////////////////////
        new ShapedRecipeBuilder(Material.MINECART).shape(new String[]{"I I", "MMM"}).materialIngredients(Map.of('I', Material.IRON_INGOT)).
                customItemIngredients(Map.of('M', Items.MAGNETITE_INGOT)).build(RpgU.getInstance());
        new ShapedRecipeBuilder(Material.PISTON).shape(new String[]{"WWW", "CMC", "CRC"}).materialIngredients(Map.of('C', Material.COBBLESTONE,
                'R', Material.REDSTONE)).customItemIngredients(Map.of('M', Items.MAGNETITE_INGOT)).tagIngredients(Map.of('W', Tag.PLANKS))
                        .build(RpgU.getInstance());

        RecipeManager.getInstance().register(new CrusherRecipe(new NamespacedKey(RpgU.getInstance(), "grass_block"),
                new CustomSingleRecipeChoice(Material.GRASS_BLOCK),
                List.of(
                        new CrusherRecipe.ResultEntry(Material.GRASS_BLOCK.asItemType().createItemStack(), 1),
                        new CrusherRecipe.ResultEntry(Items.BLAST_COAL.getItem(), 0.5)
                )));//TODO delite

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
    }

    public static class AlloyingRecipeBuilder{
        private final ItemStack replace;
        private String recipeKey;
        private final List<CustomSingleRecipeChoice> stuff = new ArrayList<>();
        private final List<CustomSingleRecipeChoice> alloy = new ArrayList<>();
        private Set<CustomItem> customItemFuel = new HashSet<>();
        private Set<Material> materialFuel = new HashSet<>();
        private Set<CustomItem> customItemAddition = new HashSet<>();
        private Set<Material> materialAddition = new HashSet<>();

        public AlloyingRecipeBuilder(@NotNull Material replaceMaterial) {
            this.replace = new ItemStack(replaceMaterial);
            this.recipeKey = replaceMaterial.getKey().getKey().toLowerCase();
        }

        public AlloyingRecipeBuilder recipeKey(@NotNull String recipeKey){
            this.recipeKey = recipeKey;
            return this;
        }

        public AlloyingRecipeBuilder customItemFuel(@NotNull Set<CustomItem> customItemFuel){
            this.customItemFuel = customItemFuel;
            return this;
        }

        public AlloyingRecipeBuilder materialFuel(@NotNull Set<Material> materialFuel){
            this.materialFuel = materialFuel;
            return this;
        }

        public AlloyingRecipeBuilder addStuffs(@NotNull CustomItem customItemAlloy, int amount){
            for (int i = 0; i < amount; i++){this.stuff.add(new CustomSingleRecipeChoice(customItemAlloy));}
            return this;
        }

        public AlloyingRecipeBuilder addStuffs(@NotNull Material materialAlloy, int amount){
            for (int i = 0; i < amount; i++){this.stuff.add(new CustomSingleRecipeChoice(materialAlloy));}
            return this;
        }

        public AlloyingRecipeBuilder addAlloy(@NotNull CustomItem customItemAlloy, int amount){
            for (int i = 0; i < amount; i++){this.stuff.add(new CustomSingleRecipeChoice(customItemAlloy));}
            return this;
        }

        public AlloyingRecipeBuilder addAlloy(@NotNull Material materialAlloy, int amount){
            for (int i = 0; i < amount; i++){this.stuff.add(new CustomSingleRecipeChoice(materialAlloy));}
            return this;
        }

        public AlloyingRecipeBuilder customItemAddition(@NotNull Set<CustomItem> customItemAddition){
            this.customItemAddition = customItemAddition;
            return this;
        }

        public AlloyingRecipeBuilder materialAddition(@NotNull Set<Material> materialAddition){
            this.materialAddition = materialAddition;
            return this;
        }

        public CustomRecipeChoice fuel(){
            return new CustomCompatibleRecipeChoice(customItemFuel, materialFuel);
        }

        public CustomRecipeChoice addition(){
            return new CustomCompatibleRecipeChoice(customItemAddition, materialAddition);
        }

        public AlloyingRecipeBuilder build(@NotNull Plugin plugin){
            RecipeManager.getInstance().unregister(NamespacedKey.minecraft(recipeKey));

            AlloyingRecipe recipe = new AlloyingRecipe(new NamespacedKey(plugin, recipeKey), stuff, fuel(), alloy, addition(), replace);

            RecipeManager.getInstance().register(recipe);

            return this;
        }
    }
}
