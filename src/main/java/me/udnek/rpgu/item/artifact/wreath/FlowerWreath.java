package me.udnek.rpgu.item.artifact.wreath;

import com.destroystokyo.paper.ParticleBuilder;
import io.papermc.paper.datacomponent.DataComponentTypes;
import io.papermc.paper.datacomponent.item.CustomModelData;
import me.udnek.coreu.custom.equipmentslot.slot.CustomEquipmentSlot;
import me.udnek.coreu.custom.equipmentslot.slot.CustomEquipmentSlot.Single;
import me.udnek.coreu.custom.equipmentslot.universal.UniversalInventorySlot;
import me.udnek.coreu.custom.item.ConstructableCustomItem;
import me.udnek.coreu.custom.item.CustomItem;
import me.udnek.coreu.util.Either;
import me.udnek.rpgu.attribute.Attributes;
import me.udnek.rpgu.component.ComponentTypes;
import me.udnek.rpgu.component.ability.passive.ConstructablePassiveAbility;
import me.udnek.rpgu.component.ability.property.AttributeBasedProperty;
import me.udnek.rpgu.component.ability.property.EffectsProperty;
import me.udnek.rpgu.component.ability.property.function.Functions;
import me.udnek.rpgu.equipment.slot.EquipmentSlots;
import me.udnek.rpgu.item.Items;
import me.udnek.rpgu.lore.ability.PassiveAbilityLorePart;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.*;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
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

    protected void put(@NotNull Material material, @NotNull TextColor color){flowerColors.put(material, color.value());}

    @Override
    public @NotNull String getRawId() {return "flower_wreath";}

    @Override
    protected void generateRecipes(@NotNull Consumer<@NotNull Recipe> consumer) {
        ShapedRecipe recipe = new ShapedRecipe(getNewRecipeKey(), this.getItem());
        recipe.shape(
                "AAA",
                "A A",
                "AAA");

        RecipeChoice.MaterialChoice choices = new RecipeChoice.MaterialChoice(new ArrayList<>(flowerColors.keySet()));
        recipe.setIngredient('A', choices);

        consumer.accept(recipe);
    }

    @Override
    public @Nullable DataSupplier<CustomModelData> getCustomModelData() {
        return DataSupplier.of(CustomModelData.custom.ModelData().addColor(Color.fromRGB(flowerColors.get(Material.DANDELION))).build());
    }

    @Override
    public @Nullable Boolean getHideAdditionalTooltip() {return true;}

    @Override
    public ItemStack getItemFromCraftingMatrix(ItemStack result, ItemStack[] matrix, @NotNull Recipe recipe) {
        Color finalColor = Color.fromRGB(255, 255, 255);
        Color[] colors = new Color[8];
        int i = 0;
        for (ItemStack flower : matrix) {
            if (flower != null){
                colors[i] = getColorByFlower(flower.getType());
                i++;
            }
        }
        setColor(result, finalColor.mixColors(colors));
        return result;
    }

    public void setColor(@NotNull ItemStack itemStack, @NotNull Color color){
        itemStack.setData(DataComponentTypes.CUSTOM_MODEL_DATA, CustomModelData.custom.ModelData().addColor(color).build());
    }

    public int getIntColorByFlower(@NotNull Material flower){return flowerColors.getOrDefault(flower, 0);}
    public @NotNull Color getColorByFlower(@NotNull Material flower){
        return Color.fromRGB(getIntColorByFlower(flower));
    }

    @Override
    public void initializeComponents() {
        super.initializeComponents();

        getComponents().getOrCreateDefault(ComponentTypes.EQUIPPABLE_ITEM).addPassive(new PassiveAbility());
    }

    public static class PassiveAbility extends ConstructablePassiveAbility<Object> {

        public static final float yOffset = 4;
        public static final int DURATION = 20*20;

        public PassiveAbility(){
            getComponents().set(new AttributeBasedProperty(6, ComponentTypes.ABILITY_CAST_RANGE));
            getComponents().set(new EffectsProperty(new EffectsProperty.PotionData(
                    PotionEffectType.REGENERATION,
                    Functions.CEIL(Functions.ATTRIBUTE(Attributes.ABILITY_DURATION, DURATION)),
                    Functions.CONSTANT(0),
                    true, true, true
            )));
        }

        @Override
        public void addLoreLines(@NotNull PassiveAbilityLorePart componentable) {
            componentable.addFullAbilityDescription(Items.FLOWER_WREATH, 1);
            super.addLoreLines(componentable);
        }

        public @NotNull Location randomOffset(@NotNull LivingEntity livingEntity) {
            Random random = new Random();
            double castRange = getComponents().getOrException(ComponentTypes.ABILITY_CAST_RANGE).get(livingEntity);
            Location location = livingEntity.getLocation();
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
        public @NotNull ActionResult action(@NotNull CustomItem custom.Item, @NotNull LivingEntity livingEntity, @NotNull Either<UniversalInventorySlot, CustomEquipmentSlot.Single> slot,
                                            @Nullable Object o) {
            Location location = randomOffset(livingEntity);
            boolean isInForest = isForestMaterial(location.getBlock().getType());
            if (!isInForest) return ActionResult.NO_COOLDOWN;

            Location from = livingEntity.getEyeLocation().add(0, -0.8, 0);
            Location to =location.toCenterLocation();

            ParticleBuilder particle = Particle.TRAIL.builder().location(from).count(10).offset(0.2, 0.2, 0.2);
            particle.data(new Particle.Trail(to, Color.fromRGB(92, 169, 4), 50)).spawn();
            particle.data(new Particle.Trail(to, Color.fromRGB(139,69,19),50)).spawn();
            getComponents().getOrException(ComponentTypes.ABILITY_EFFECTS).applyOn(livingEntity, livingEntity);
            return ActionResult.FULL_COOLDOWN;
        }

        @Override
        public void tick(@NotNull CustomItem custom.Item, @NotNull LivingEntity livingEntity, @NotNull CustomEquipmentSlot.Single slot) {
            activate(custom.Item, livingEntity, new Either<>(null, slot) , new Object());
        }
    }
}


















