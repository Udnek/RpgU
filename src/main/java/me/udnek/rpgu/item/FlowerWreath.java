package me.udnek.rpgu.item;

import me.udnek.itemscoreu.customattribute.AttributeUtils;
import me.udnek.itemscoreu.customattribute.equipmentslot.CustomEquipmentSlot;
import me.udnek.itemscoreu.customitem.ItemUtils;
import me.udnek.rpgu.Utils;
import me.udnek.rpgu.attribute.VanillaAttributeContainer;
import me.udnek.rpgu.attribute.equipmentslot.EquipmentSlots;
import me.udnek.rpgu.item.abstraction.ArtifactItem;
import me.udnek.rpgu.item.abstraction.ExtraDescriptionItem;
import me.udnek.rpgu.lore.LoreUtils;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Player;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.FireworkEffectMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import oshi.util.tuples.Pair;

import java.util.*;

public class FlowerWreath extends ArtifactItem implements ExtraDescriptionItem {

    private final VanillaAttributeContainer vanillaAttributeContainer = new VanillaAttributeContainer.Builder()
            .add(Attribute.GENERIC_SCALE, getRawId(), 1, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlots.ARTIFACT)
            .build();

    public final float xRange = 7;
    public final float yRange = 4;
    public final float yOffset = 4;
    public final int duration = 20*20;

    private EnumMap<Material, Integer> flowerColors = new EnumMap<>(Material.class);

    protected FlowerWreath(){
        put(Material.DANDELION, TextColor.color(255, 236, 79));
        put(Material.POPPY, TextColor.color(237,48,44));
        put(Material.BLUE_ORCHID, TextColor.color(42,191,253));
        put(Material.ALLIUM, TextColor.color(210,166,246));
        put(Material.AZURE_BLUET, TextColor.color(250,255,148));
        put(Material.RED_TULIP, TextColor.color(237, 48, 44));
        put(Material.ORANGE_TULIP, TextColor.color(241,157,37));
        put(Material.WHITE_TULIP, TextColor.color(247,247,247));
        put(Material.PINK_TULIP, TextColor.color(246,226,255));
        put(Material.OXEYE_DAISY,  TextColor.color(255, 242, 143));
        put(Material.CORNFLOWER,  TextColor.color(70,106,235));
        put(Material.LILY_OF_THE_VALLEY,  TextColor.color(228,228,228));
        put(Material.PINK_PETALS, TextColor.color(246,161,212));
        put(Material.SUNFLOWER,  TextColor.color(245,186,39));
    }

    protected void put(Material material, TextColor color){
        flowerColors.put(material,  color.value());
    }

    @Override
    public Integer getCustomModelData() {
        return 3100;
    }

    @Override
    protected ItemFlag[] getTooltipHides() {
        return new ItemFlag[]{ItemFlag.HIDE_ADDITIONAL_TOOLTIP, ItemFlag.HIDE_ATTRIBUTES};
    }

    @Override
    protected void modifyFinalItemMeta(ItemMeta itemMeta) {
        super.modifyFinalItemMeta(itemMeta);
        ItemUtils.setFireworkColor((FireworkEffectMeta) itemMeta, Color.fromRGB(this.getColorByFlower(Material.DANDELION)));
    }
    @Override
    protected void modifyFinalItemStack(ItemStack itemStack) {
        super.modifyFinalItemStack(itemStack);
        LoreUtils.generateFullLoreAndApply(itemStack);
    }
    @Override
    public Material getMaterial() {
        return Material.FIREWORK_STAR;
    }
    @Override
    public String getRawId() {
        return "flower_wreath";
    }


    @Override
    public Map<CustomEquipmentSlot, Pair<Integer, Integer>> getExtraDescription() {
        return ExtraDescriptionItem.getSimple(EquipmentSlots.ARTIFACT, 1);
    }

    @Override
    public Map<Attribute, List<AttributeModifier>> getAttributes() {
        HashMap<Attribute, List<AttributeModifier>> map = new HashMap<>();
        map.put(Attribute.GENERIC_GRAVITY, Collections.singletonList(
                new AttributeModifier(AttributeUtils.UUIDFromSeed("asd8q9dhajfh kf"), "", 1, AttributeModifier.Operation.MULTIPLY_SCALAR_1, EquipmentSlotGroup.OFFHAND)));
        return map;
    }

    @Override
    protected List<Recipe> generateRecipes() {
        ShapedRecipe recipe = new ShapedRecipe(this.getRecipeNamespace(0), this.getItem());
        recipe.shape("AAA","A A","AAA");

        RecipeChoice.MaterialChoice choices = new RecipeChoice.MaterialChoice(flowerColors.keySet().toArray(new Material[0]));
        recipe.setIngredient('A', choices);

        return Collections.singletonList(recipe);
    }

    @Override
    public ItemStack getItemFromCraftingMatrix(ItemStack result, ItemStack[] matrix, Recipe recipe) {
        Color finalColor = Color.fromRGB(255, 255, 255);
        Color[] colors = new Color[8];
        int i = 0;
        for (ItemStack flower : matrix) {
            if (flower != null){
                colors[i] = Color.fromRGB(this.getColorByFlower(flower.getType()));
                i++;
            }
        }
        ItemMeta itemMeta = result.getItemMeta();
        ItemUtils.setFireworkColor((FireworkEffectMeta) itemMeta, finalColor.mixColors(colors));
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public int getColorByFlower(Material flower){
        return flowerColors.getOrDefault(flower, 0);
    }

    @Override
    public VanillaAttributeContainer getDefaultVanillaAttributes() {
        return vanillaAttributeContainer;
    }

    @Override
    public void tickBeingEquipped(Player player) {
        Material material = player.getLocation().getWorld().getBlockAt(randomOffset(player.getLocation())).getType();
        boolean isInForest = isForestMaterial(material);
        if (isInForest) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, duration, 0, true, true));
        }
    }

    public Location randomOffset(Location location){
        Random random = new Random();
        location.add((random.nextFloat()-0.5f)*2*xRange, (random.nextFloat()-0.5f)*2*yRange+yOffset, (random.nextFloat()-0.5f)*2*xRange);
        return location;
    }
    public boolean isForestMaterial(Material material){
        return Tag.LOGS.isTagged(material) || Tag.LEAVES.isTagged(material);
    }
}



















