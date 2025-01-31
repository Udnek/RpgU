package me.udnek.rpgu.vanilla;

import me.udnek.itemscoreu.customitem.CustomItem;
import me.udnek.itemscoreu.customitem.ItemUtils;
import me.udnek.itemscoreu.customitem.VanillaItemManager;
import me.udnek.itemscoreu.customrecipe.RecipeManager;
import me.udnek.itemscoreu.customrecipe.choice.CustomCompatibleRecipeChoice;
import me.udnek.itemscoreu.customrecipe.choice.CustomRecipeChoice;
import me.udnek.itemscoreu.customrecipe.choice.CustomSingleRecipeChoice;
import me.udnek.itemscoreu.nms.Nms;
import me.udnek.rpgu.RpgU;
import me.udnek.rpgu.item.Items;
import me.udnek.rpgu.mechanic.alloying.AlloyingRecipe;
import me.udnek.rpgu.util.Utils;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Tag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.loot.LootTables;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class RecipeManaging {
    public static void run(){
        new RecipeBuilder(Material.LODESTONE).recipeShape(new String[]{ "BBB", "BMB", "BBB"}).materialIngredients(Map.of('B', Material.STONE_BRICKS))
                .customItemIngredients(Map.of('M', Items.MAGNETITE_INGOT)).build();
        new RecipeBuilder(Material.POWERED_RAIL).recipeShape(new String[]{"M M", "ISI", "MRM"}).materialIngredients(Map.of('S', Material.STICK, 'R',
                Material.REDSTONE, 'I', Material.IRON_INGOT)).customItemIngredients(Map.of('M', Items.MAGNETITE_INGOT)).setAmount(6).build();
        new RecipeBuilder(Material.COMPASS).recipeShape(new String[]{" C ", "CMC", " C "}).materialIngredients(Map.of('C', Material.COPPER_INGOT))
                .customItemIngredients(Map.of('M', Items.MAGNETITE_INGOT)).build();
        new RecipeBuilder(Material.BUCKET).recipeShape(new String[]{"C C", " C "}).materialIngredients(Map.of('C', Material.COPPER_INGOT)).build();
        new RecipeBuilder(Material.CROSSBOW).recipeShape(new String[]{"SCS", "GTG", " S "}).materialIngredients(Map.of('S', Material.STICK, 'C',
                Material.COPPER_INGOT, 'G', Material.STRING, 'T', Material.TRIPWIRE_HOOK)).build();
        new RecipeBuilder(Material.SHEARS).recipeShape(new String[]{" C", "C "}).materialIngredients(Map.of('C', Material.COPPER_INGOT)).build();
        new RecipeBuilder(Material.SHIELD).recipeShape(new String[]{"CPC", "CPC", "CPC"}).materialIngredients(Map.of('C', Material.COPPER_INGOT))
                .tagIngredients(Map.of('P', Tag.PLANKS)).build();
        new RecipeBuilder(Material.CLOCK).recipeShape(new String[]{" C ", "CRC", " C "}).materialIngredients(Map.of('C', Material.COPPER_INGOT,
                'R', Material.REDSTONE)).build();
        ////////////////////////////////////////////////////////////////////////////////////////////
        new RecipeBuilder(Material.IRON_CHESTPLATE).recipeShape(new String[]{"ILI", "III", "III"}).materialIngredients(Map.of('I', Material.IRON_INGOT,
                'L', Material.LEATHER_CHESTPLATE)).build();
        new RecipeBuilder(Material.IRON_HELMET).recipeShape(new String[]{"III", "ILI"}).materialIngredients(Map.of('I', Material.IRON_INGOT,
                'L', Material.LEATHER_HELMET)).build();
        new RecipeBuilder(Material.IRON_LEGGINGS).recipeShape(new String[]{"III", "ILI", "I I"}).materialIngredients(Map.of('I', Material.IRON_INGOT,
                'L', Material.LEATHER_LEGGINGS)).build();
        new RecipeBuilder(Material.IRON_BOOTS).recipeShape(new String[]{"ILI", "I I"}).materialIngredients(Map.of('I', Material.IRON_INGOT,
                'L', Material.LEATHER_BOOTS)).build();
        ////////////////////////////////////////////////////////////////////////////////////////////
        new RecipeBuilder(Material.LEATHER_CHESTPLATE).recipeShape(new String[]{"L L", "FLF", "LFL"}).materialIngredients(Map.of( 'L', Material.LEATHER))
                .customItemIngredients(Map.of('F', Items.FABRIC)).build();
        new RecipeBuilder(Material.LEATHER_HELMET).recipeShape(new String[]{"FLF", "L L"}).materialIngredients(Map.of( 'L', Material.LEATHER))
                .customItemIngredients(Map.of('F', Items.FABRIC)).build();
        new RecipeBuilder(Material.LEATHER_LEGGINGS).recipeShape(new String[]{"LFL", "F F", "L L"}).materialIngredients(Map.of( 'L', Material.LEATHER))
                .customItemIngredients(Map.of('F', Items.FABRIC)).build();
        new RecipeBuilder(Material.LEATHER_BOOTS).recipeShape(new String[]{"F F", "L L"}).materialIngredients(Map.of( 'L', Material.LEATHER))
                .customItemIngredients(Map.of('F', Items.FABRIC)).build();
        ////////////////////////////////////////////////////
        new RecipeBuilder(Material.BLAST_FURNACE).recipeShape(new String[]{"SSS", "FFA", "BBB"}).materialIngredients(Map.of('F', Material.FURNACE,
                'S', Material.SMOOTH_STONE,'B', Material.BRICKS,'A', Material.AMETHYST_BLOCK)).build();
        ////////////////////////////////////////////////////
        new AlloyingRecipeBuilder(Material.NETHERITE_INGOT).addMaterialAlloy(Material.NETHERITE_SCRAP, 3).addCustomItemAlloy(Items.MAGNETITE_INGOT, 3)
                .customItemFuel(Set.of(Items.BLAST_COAL)).customItemAddition(Set.of(Items.INGOT_MOLD)).build();
        ///////////////////////////////////////////////////
        new AlloyingRecipeBuilder(Material.NETHERITE_HELMET).recipeKey("netherite_helmet_smithing").addMaterialAlloy(Material.NETHERITE_INGOT, 1)
                .materialFuel(Tag.ITEMS_COALS.getValues()).customItemAddition(Set.of(Items.FERRUDAM_HELMET)).build();
        new AlloyingRecipeBuilder(Material.NETHERITE_CHESTPLATE).recipeKey("netherite_chestplate_smithing").addMaterialAlloy(Material.NETHERITE_INGOT, 1)
                .materialFuel(Tag.ITEMS_COALS.getValues()).customItemAddition(Set.of(Items.FERRUDAM_CHESTPLATE)).build();
        new AlloyingRecipeBuilder(Material.NETHERITE_LEGGINGS).recipeKey("netherite_leggings_smithing").addMaterialAlloy(Material.NETHERITE_INGOT, 1)
                .materialFuel(Tag.ITEMS_COALS.getValues()).customItemAddition(Set.of(Items.FERRUDAM_LEGGINGS)).build();
        new AlloyingRecipeBuilder(Material.NETHERITE_BOOTS).recipeKey("netherite_boots_smithing").addMaterialAlloy(Material.NETHERITE_INGOT, 1)
                .materialFuel(Tag.ITEMS_COALS.getValues()).customItemAddition(Set.of(Items.FERRUDAM_BOOTS)).build();
        /////////////////////////////////////////////
        new AlloyingRecipeBuilder(Material.NETHERITE_HOE).recipeKey("netherite_hoe_smithing").addMaterialAlloy(Material.NETHERITE_INGOT, 1)
                .materialFuel(Tag.ITEMS_COALS.getValues()).customItemAddition(Set.of(Items.FERRUDAM_HOE)).build();
        new AlloyingRecipeBuilder(Material.NETHERITE_AXE).recipeKey("netherite_axe_smithing").addMaterialAlloy(Material.NETHERITE_INGOT, 1)
                .materialFuel(Tag.ITEMS_COALS.getValues()).customItemAddition(Set.of(Items.FERRUDAM_AXE)).build();
        new AlloyingRecipeBuilder(Material.NETHERITE_PICKAXE).recipeKey("netherite_pickaxe_smithing").addMaterialAlloy(Material.NETHERITE_INGOT, 1)
                .materialFuel(Tag.ITEMS_COALS.getValues()).customItemAddition(Set.of(Items.FERRUDAM_PICKAXE)).build();
        new AlloyingRecipeBuilder(Material.NETHERITE_SHOVEL).recipeKey("netherite_shovel_smithing").addMaterialAlloy(Material.NETHERITE_INGOT, 1)
                .materialFuel(Tag.ITEMS_COALS.getValues()).customItemAddition(Set.of(Items.FERRUDAM_SHOVEL)).build();
        new AlloyingRecipeBuilder(Material.NETHERITE_SWORD).recipeKey("netherite_sword_smithing").addMaterialAlloy(Material.NETHERITE_INGOT, 1)
                .materialFuel(Tag.ITEMS_COALS.getValues()).customItemAddition(Set.of(Items.FERRUDAM_SWORD)).build();
        ////////////////////////////////////////////
        new RecipeBuilder(Material.CHAINMAIL_CHESTPLATE).recipeShape(new String[]{"C C", "CCC", "CCC"}).materialIngredients(Map.of('C', Material.CHAIN))
                .build();
        new RecipeBuilder(Material.CHAINMAIL_HELMET).recipeShape(new String[]{"CCC", "C C"}).materialIngredients(Map.of('C', Material.CHAIN))
                .build();
        new RecipeBuilder(Material.CHAINMAIL_LEGGINGS).recipeShape(new String[]{"CCC", "C C", "C C"}).materialIngredients(Map.of('C', Material.CHAIN))
                .build();
        new RecipeBuilder(Material.CHAINMAIL_BOOTS).recipeShape(new String[]{"C C", "C C"}).materialIngredients(Map.of('C', Material.CHAIN))
                .build();
        /////////////////////////////////////////////////
        new RecipeBuilder(Material.GOLDEN_CHESTPLATE).recipeShape(new String[]{"GLG", "GGG", "GGG"}).materialIngredients(Map.of('G', Material.GOLD_INGOT,
                'L', Material.LEATHER_CHESTPLATE)).build();
        new RecipeBuilder(Material.GOLDEN_HELMET).recipeShape(new String[]{"GGG", "GLG"}).materialIngredients(Map.of('G', Material.GOLD_INGOT,
                'L', Material.LEATHER_HELMET)).build();
        new RecipeBuilder(Material.GOLDEN_LEGGINGS).recipeShape(new String[]{"GGG", "GLG", "G G"}).materialIngredients(Map.of('G', Material.GOLD_INGOT,
                'L', Material.LEATHER_LEGGINGS)).build();
        new RecipeBuilder(Material.GOLDEN_BOOTS).recipeShape(new String[]{"GLG", "G G"}).materialIngredients(Map.of('G', Material.GOLD_INGOT,
                'L', Material.LEATHER_BOOTS)).build();
        //////////////////////////////////////////////
        for (Material material: Tag.BEDS.getValues()){
            new RecipeBuilder(material).recipeShape(new String[]{"FFW", "PPP"}).materialIngredients(Map.of('W', Utils.replaceSufix(material,
                    "_wool"))).customItemIngredients(Map.of('F', Items.FABRIC)).tagIngredients(Map.of('P', Tag.PLANKS)).build();
        }
        //////////////////////////////////////////////
        for (Material material: Tag.WOODEN_TRAPDOORS.getValues()){
            new RecipeBuilder(material).setAmount(6).recipeShape(new String[]{"PPP", "PPP"})
                    .materialIngredients(Map.of('P', Utils.replaceSufix(material, "_planks"))).build();
        }
        new RecipeBuilder(Material.COPPER_TRAPDOOR).setAmount(6).recipeShape(new String[]{"PPP", "PPP"})
                .materialIngredients(Map.of('P', Material.COPPER_INGOT)).build();
        //////////////////////////////////////////////
        new ShapelessRecipeBuilder(Material.ANVIL).addIngredient(Material.IRON_INGOT, 3).
                addIngredient(Material.CHIPPED_ANVIL, 1).build();
        new ShapelessRecipeBuilder(Material.CHIPPED_ANVIL).addIngredient(Material.IRON_INGOT, 3).
                addIngredient(Material.DAMAGED_ANVIL, 1).build();
        //////////////////////////////////////////////
        new RecipeBuilder(Material.MINECART).recipeShape(new String[]{"I I", "MMM"}).materialIngredients(Map.of('I', Material.IRON_INGOT)).
                customItemIngredients(Map.of('M', Items.MAGNETITE_INGOT)).build();

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

        Nms.get().removeAllEntriesContains(LootTables.RUINED_PORTAL.getLootTable(), itemStack -> ItemUtils.isVanillaMaterial(itemStack,Material.CLOCK));
    }

    public static class ShapelessRecipeBuilder {
        private final ItemStack replace;
        private String recipeKey;
        private final List<RecipeChoice> recipeChoices = new ArrayList<>();

        public ShapelessRecipeBuilder(@NotNull Material recipeMaterial) {
            this.replace = new ItemStack(recipeMaterial);
            this.recipeKey = recipeMaterial.getKey().getKey();
        }

        public ShapelessRecipeBuilder(@NotNull CustomItem recipeCustomItem) {
            this.replace = recipeCustomItem.getItem();
            this.recipeKey = recipeCustomItem.getNewRecipeKey().getKey();
        }

        public ShapelessRecipeBuilder recipeKey(@NotNull String recipeKey){
            this.recipeKey = recipeKey;
            return this;
        }

        public ShapelessRecipeBuilder addIngredient(@NotNull CustomItem customItemAlloy, int amount){
            for (int i = 0; i < amount; i++){this.recipeChoices.add(new RecipeChoice.ExactChoice(customItemAlloy.getItem()));}
            return this;
        }

        public ShapelessRecipeBuilder addIngredient(@NotNull Material materialAlloy, int amount){
            for (int i = 0; i < amount; i++){this.recipeChoices.add(new RecipeChoice.MaterialChoice(materialAlloy));}
            return this;
        }

        public ShapelessRecipeBuilder addIngredient(@NotNull Tag<Material> materialAddition, int amount){
            for (int i = 0; i < amount; i++){this.recipeChoices.add(new RecipeChoice.MaterialChoice(materialAddition));}
            return this;
        }

        public ShapelessRecipeBuilder build(){
            ShapelessRecipe recipe = new ShapelessRecipe(new NamespacedKey(RpgU.getInstance(), recipeKey), replace);

            for (RecipeChoice recipeChoice : recipeChoices){recipe.addIngredient(recipeChoice);}

            RecipeManager.getInstance().register(recipe);

            return this;
        }
    }

    public static class AlloyingRecipeBuilder{
        private final ItemStack replace;
        private String recipeKey;
        private final List<CustomSingleRecipeChoice> alloys = new ArrayList<>();
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

        public AlloyingRecipeBuilder addCustomItemAlloy(@NotNull CustomItem customItemAlloy, int amount){
            for (int i = 0; i < amount; i++){this.alloys.add(new CustomSingleRecipeChoice(customItemAlloy));}
            return this;
        }

        public AlloyingRecipeBuilder addMaterialAlloy(@NotNull Material materialAlloy, int amount){
            for (int i = 0; i < amount; i++){this.alloys.add(new CustomSingleRecipeChoice(materialAlloy));}
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

        public AlloyingRecipeBuilder build(){
            RecipeManager.getInstance().unregister(NamespacedKey.minecraft(recipeKey));

            AlloyingRecipe recipe = new AlloyingRecipe(new NamespacedKey(RpgU.getInstance(), recipeKey), alloys, fuel(), addition(), replace);

            RecipeManager.getInstance().register(recipe);

            return this;
        }
    }

    public static class RecipeBuilder {
        private final ItemStack replace;
        private String recipeKey;
        private String[] recipeShape;
        private boolean rewriteRecipe = true;
        private Map<Character, Material> materialIngredients = new HashMap<>();
        private Map<Character, CustomItem> customItemIngredients = new HashMap<>();
        private Map<Character, Tag<Material>> tagIngredients = new HashMap<>();

        public RecipeBuilder(@NotNull Material replaceMaterial){
            this.replace = new ItemStack(replaceMaterial);
            this.recipeKey = replaceMaterial.getKey().getKey().toLowerCase();
        }

        public RecipeBuilder recipeKey(@NotNull String recipeKey){
            this.recipeKey = recipeKey;
            return this;
        }

        public RecipeBuilder recipeShape(@NotNull String[] recipeShape){
            this.recipeShape = recipeShape;
            return this;
        }

        public RecipeBuilder materialIngredients(@NotNull Map<Character, Material> materialIngredients){
            this.materialIngredients = materialIngredients;
            return this;
        }

        public RecipeBuilder customItemIngredients(@NotNull Map<Character, CustomItem> customItemIngredients){
            this.customItemIngredients = customItemIngredients;
            return this;
        }

        public RecipeBuilder tagIngredients(@NotNull Map<Character, Tag<Material>> tagIngredients){
            this.tagIngredients = tagIngredients;
            return this;
        }

        public RecipeBuilder setAmount(int amount){
            this.replace.setAmount(amount);
            return this;
        }

        public RecipeBuilder rewriteRecipe(boolean rewriteRecipe) {
            this.rewriteRecipe = rewriteRecipe;
            return this;
        }

        public RecipeBuilder build(){
            if (rewriteRecipe){
                RecipeManager.getInstance().unregister(NamespacedKey.minecraft(recipeKey));
            }

            ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(RpgU.getInstance(), recipeKey), replace);
            recipe.shape(recipeShape);

            for (Map.Entry<Character, Material> material : materialIngredients.entrySet()) {
                recipe.setIngredient(material.getKey(), new RecipeChoice.MaterialChoice(material.getValue()));
            }
            for (Map.Entry<Character, CustomItem> customItem : customItemIngredients.entrySet()) {
                recipe.setIngredient(customItem.getKey(), new RecipeChoice.ExactChoice(customItem.getValue().getItem()));
            }
            for (Map.Entry<Character, Tag<Material>> tag : tagIngredients.entrySet()) {
                recipe.setIngredient(tag.getKey(), new RecipeChoice.MaterialChoice(tag.getValue()));
            }

            RecipeManager.getInstance().register(recipe);
            return this;
        }
    }
}
