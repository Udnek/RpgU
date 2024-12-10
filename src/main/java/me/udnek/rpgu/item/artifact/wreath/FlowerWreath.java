package me.udnek.rpgu.item.artifact.wreath;

import me.udnek.itemscoreu.customequipmentslot.CustomEquipmentSlot;
import me.udnek.itemscoreu.customitem.ConstructableCustomItem;
import me.udnek.itemscoreu.customitem.CustomItem;
import me.udnek.itemscoreu.util.ItemUtils;
import me.udnek.itemscoreu.util.LoreBuilder;
import me.udnek.rpgu.component.ArtifactComponent;
import me.udnek.rpgu.component.ComponentTypes;
import me.udnek.rpgu.component.ability.passive.ConstructablePassiveAbilityComponent;
import me.udnek.rpgu.component.ability.passive.PassiveAbilityComponent;
import me.udnek.rpgu.component.ability.property.AttributeBasedProperty;
import me.udnek.rpgu.equipment.slot.EquipmentSlots;
import me.udnek.rpgu.lore.AttributesLorePart;
import me.udnek.rpgu.lore.ability.PassiveAbilityLorePart;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.*;
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

public class FlowerWreath extends ConstructableCustomItem {

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
    protected void modifyFinalItemMeta(@NotNull ItemMeta itemMeta) {
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
    public void initializeComponents() {
        super.initializeComponents();

        getComponents().set(new Artifact());
        getComponents().set(new PassiveAbility());
    }

    public class PassiveAbility extends ConstructablePassiveAbilityComponent<Object>{

        public static final float yOffset = 4;
        public static final int DURATION = 20*20;

        @Override
        public void addLoreLines(@NotNull PassiveAbilityLorePart componentable) {
            componentable.addFullAbilityDescription(FlowerWreath.this, 1);
            super.addLoreLines(componentable);
        }

        public PassiveAbility(){
            getComponents().set(AttributeBasedProperty.from(6, ComponentTypes.ABILITY_CAST_RANGE));
            getComponents().set(AttributeBasedProperty.from(DURATION, ComponentTypes.ABILITY_DURATION));
        }

        public @NotNull Location randomOffset(@NotNull Player player) {
            Random random = new Random();
            double castRange = getComponents().getOrException(ComponentTypes.ABILITY_CAST_RANGE).get(player);
            Location location = player.getLocation();
            location.add((random.nextFloat() - 0.5f) * 2 * castRange, (random.nextFloat() - 0.5f) * 2 * castRange + yOffset, (random.nextFloat() - 0.5f) * 2 * castRange);
            return location;
        }

        public boolean isForestMaterial(Material material) {
            return Tag.LOGS.isTagged(material) || Tag.LEAVES.isTagged(material);
        }

        @Override
        public @NotNull CustomEquipmentSlot getSlot() {
            return EquipmentSlots.ARTIFACTS;
        }

        @Override
        public @NotNull ActionResult action(@NotNull CustomItem customItem, @NotNull Player player, @Nullable Object o) {
            boolean isInForest = isForestMaterial(randomOffset(player).getBlock().getType());
            if (isInForest) {
                player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, DURATION, 0));
                return ActionResult.FULL_COOLDOWN;
            }
            return ActionResult.NO_COOLDOWN;
        }

    }

    public static class Artifact implements ArtifactComponent {
        @Override
        public void tickBeingEquipped(@NotNull CustomItem item, @NotNull Player player, @NotNull CustomEquipmentSlot slot) {
            if (Bukkit.getCurrentTick() % 10 != 0) return;
            PassiveAbilityComponent<?> passive = item.getComponents().get(ComponentTypes.PASSIVE_ABILITY_ITEM);
            if (passive instanceof PassiveAbility passiveAbility){
                passiveAbility.activate(item, player, new Object());
            }
        }
    }
}


















