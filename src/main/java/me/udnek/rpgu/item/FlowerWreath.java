package me.udnek.rpgu.item;

import me.udnek.itemscoreu.customitem.CustomModelDataItem;
import me.udnek.itemscoreu.utils.ItemUtils;
import me.udnek.itemscoreu.utils.TranslateUtils;
import me.udnek.rpgu.item.abstracts.ArtifactItem;
import me.udnek.rpgu.lore.LoreConstructor;
import me.udnek.rpgu.lore.TranslationKeys;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.entity.Player;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.FireworkEffectMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Random;

public class FlowerWreath extends CustomModelDataItem implements ArtifactItem {

    public static float xRange = 7;
    public static float yRange = 4;
    public static float yOffset = 4;
    public static int duration = 20*7;

    private static EnumMap<Material, Color> flowerColors = new EnumMap<Material, Color>(Material.class);

    static {
        flowerColors.put(Material.DANDELION, Color.fromRGB(255, 236, 79));
        flowerColors.put(Material.POPPY, Color.fromRGB(237,48,44));
        flowerColors.put(Material.BLUE_ORCHID, Color.fromRGB(42,191,253));
        flowerColors.put(Material.ALLIUM, Color.fromRGB(210,166,246));
        flowerColors.put(Material.AZURE_BLUET, Color.fromRGB(59,35,100));
        flowerColors.put(Material.RED_TULIP, Color.fromRGB(237, 48, 44));
        flowerColors.put(Material.ORANGE_TULIP, Color.fromRGB(241,157,37));
        flowerColors.put(Material.WHITE_TULIP, Color.fromRGB(247,247,247));
        flowerColors.put(Material.PINK_TULIP, Color.fromRGB(246,226,255));
        flowerColors.put(Material.OXEYE_DAISY, Color.fromRGB(255, 242, 143));
        flowerColors.put(Material.CORNFLOWER, Color.fromRGB(70,106,235));
        flowerColors.put(Material.LILY_OF_THE_VALLEY, Color.fromRGB(228,228,228));
        flowerColors.put(Material.PINK_PETALS, Color.fromRGB(246,161,212));
        flowerColors.put(Material.SUNFLOWER, Color.fromRGB(245,186,39));
    }

    @Override
    public int getCustomModelData() {
        return 3100;
    }


    @Override
    protected void modifyFinalItemMeta(ItemMeta itemMeta) {
        super.modifyFinalItemMeta(itemMeta);
        itemMeta.addItemFlags(ItemFlag.HIDE_ITEM_SPECIFICS);
        ItemUtils.setFireworkColor((FireworkEffectMeta) itemMeta, this.getColorByFlower(Material.DANDELION));
        LoreConstructor loreConstructor = new LoreConstructor();
        loreConstructor.setArtifactInformation(TranslateUtils.getTranslated("item.rpgu.flower_wreath.description.0"));
        loreConstructor.apply(itemMeta);
    }

    @Override
    public Material getMaterial() {
        return Material.FIREWORK_STAR;
    }

    @Override
    protected String getRawDisplayName() {
        return TranslationKeys.itemPrefix + getItemName();
    }

    @Override
    protected String getItemName() {
        return "flower_wreath";
    }

    @Override
    protected List<Recipe> generateRecipes() {
        ShapedRecipe recipe = new ShapedRecipe(this.getRecipeNamespace(), this.getItem());
        recipe.shape("AAA","A A","AAA");

        RecipeChoice.MaterialChoice choices = new RecipeChoice.MaterialChoice(flowerColors.keySet().toArray(new Material[0]));
        recipe.setIngredient('A', choices);

        return Collections.singletonList(recipe);
    }

    @Override
    public ItemStack itemFromMatrix(ItemStack result, ItemStack[] matrix, Recipe recipe) {
        Color finalColor = Color.fromRGB(255, 255, 255);
        Color[] colors = new Color[8];
        int i = 0;
        for (ItemStack flower : matrix) {
            if (flower != null){
                colors[i] = this.getColorByFlower(flower.getType());
                i++;
            }
        }
        ItemMeta itemMeta = result.getItemMeta();
        ItemUtils.setFireworkColor((FireworkEffectMeta) itemMeta, finalColor.mixColors(colors));
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public Color getColorByFlower(Material flower){
        return flowerColors.getOrDefault(flower, Color.fromRGB(0, 0, 0));
    }

    @Override
    public void onWhileBeingEquipped(Player player) {
        Material material = player.getLocation().getWorld().getBlockAt(randomOffset(player.getLocation())).getType();
        boolean isInForest = isForestMaterial(material);
        if (isInForest) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, duration, 0, true, true));
        }
    }

    public static Location randomOffset(Location location){
        Random random = new Random();
        location.add((random.nextFloat()-0.5f)*2*xRange, (random.nextFloat()-0.5f)*2*yRange+yOffset, (random.nextFloat()-0.5f)*2*xRange);
        return location;
    }
    public static boolean isForestMaterial(Material material){
        return Tag.LOGS.isTagged(material) || Tag.LEAVES.isTagged(material);
    }
}



















