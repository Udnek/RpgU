package me.udnek.rpgu.item.artifact;

import me.udnek.itemscoreu.customattribute.CustomAttribute;
import me.udnek.itemscoreu.customequipmentslot.CustomEquipmentSlot;
import me.udnek.itemscoreu.customitem.ConstructableCustomItem;
import me.udnek.itemscoreu.customitem.CustomItem;
import me.udnek.itemscoreu.util.ItemUtils;
import me.udnek.itemscoreu.util.LoreBuilder;
import me.udnek.rpgu.component.ArtifactComponent;
import me.udnek.rpgu.equipment.slot.EquipmentSlots;
import me.udnek.rpgu.item.RpgUCustomItem;
import me.udnek.rpgu.lore.AttributesLorePart;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
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
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.EnumMap;
import java.util.Random;
import java.util.function.Consumer;

public class FlowerWreath extends ConstructableCustomItem implements RpgUCustomItem {

    private final EnumMap<Material, Integer> flowerColors = new EnumMap<>(Material.class);

    public FlowerWreath(){
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

    protected void put(Material material, TextColor color){flowerColors.put(material,  color.value());}

    @Override
    public @NotNull Material getMaterial() {return Material.FIREWORK_STAR;}
    @Override
    public @NotNull String getRawId() {return "flower_wreath";}

    @Override
    protected void generateRecipes(@NotNull Consumer<@NotNull Recipe> consumer) {
        ShapedRecipe recipe = new ShapedRecipe(getNewRecipeKey(), this.getItem());
        recipe.shape(
                "AAA",
                "A A",
                "AAA");

        RecipeChoice.MaterialChoice choices = new RecipeChoice.MaterialChoice(flowerColors.keySet().toArray(new Material[0]));
        recipe.setIngredient('A', choices);

        consumer.accept(recipe);
    }

    @Override
    public ItemFlag[] getTooltipHides() {return new ItemFlag[]{ItemFlag.HIDE_ADDITIONAL_TOOLTIP};}

    @Override
    protected void modifyFinalItemMeta(ItemMeta itemMeta) {
        super.modifyFinalItemMeta(itemMeta);
        ItemUtils.setFireworkColor((FireworkEffectMeta) itemMeta, Color.fromRGB(getColorByFlower(Material.DANDELION)));
    }

    @Override
    public ItemStack getItemFromCraftingMatrix(ItemStack result, ItemStack[] matrix, Recipe recipe) {
        Color finalColor = Color.fromRGB(255, 255, 255);
        Color[] colors = new Color[8];
        int i = 0;
        for (ItemStack flower : matrix) {
            if (flower != null){
                colors[i] = Color.fromRGB(getColorByFlower(flower.getType()));
                i++;
            }
        }
        ItemMeta itemMeta = result.getItemMeta();
        ItemUtils.setFireworkColor((FireworkEffectMeta) itemMeta, finalColor.mixColors(colors));
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public int getColorByFlower(Material flower){return flowerColors.getOrDefault(flower, 0);}

    @Override
    public @Nullable LoreBuilder getLoreBuilder() {
        LoreBuilder loreBuilder = new LoreBuilder();
        AttributesLorePart attributesLorePart = new AttributesLorePart();
        loreBuilder.set(LoreBuilder.Position.ATTRIBUTES, attributesLorePart);
        attributesLorePart.addAttribute(EquipmentSlots.ARTIFACTS, Component.translatable(getRawItemName() + ".description.0").color(CustomAttribute.PLUS_COLOR));

        return loreBuilder;
    }

    @Override
    public void initializeComponents() {
        super.initializeComponents();

        setComponent(new FlowerWreathComponent());
    }

    public static class FlowerWreathComponent implements ArtifactComponent {
        public static final float xRange = 7;
        public static final float yRange = 4;
        public static final float yOffset = 4;
        public static final int duration = 20*20;

        @Override
        public void tickBeingEquipped(@NotNull CustomItem item, @NotNull Player player, @NotNull CustomEquipmentSlot slot) {
            Material material = player.getLocation().getWorld().getBlockAt(randomOffset(player.getLocation())).getType();
            boolean isInForest = isForestMaterial(material);
            if (isInForest) {
                player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, duration, 0));
            }
        }

        public Location randomOffset(Location location) {
            Random random = new Random();
            location.add((random.nextFloat() - 0.5f) * 2 * xRange, (random.nextFloat() - 0.5f) * 2 * yRange + yOffset, (random.nextFloat() - 0.5f) * 2 * xRange);
            return location;
        }

        public boolean isForestMaterial(Material material) {
            return Tag.LOGS.isTagged(material) || Tag.LEAVES.isTagged(material);
        }
    }
}