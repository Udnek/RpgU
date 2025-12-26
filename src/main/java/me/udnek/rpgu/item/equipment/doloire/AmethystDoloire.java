package me.udnek.rpgu.item.equipment.doloire;

import com.destroystokyo.paper.ParticleBuilder;
import io.papermc.paper.datacomponent.DataComponentTypes;
import io.papermc.paper.datacomponent.item.Consumable;
import io.papermc.paper.datacomponent.item.consumable.ItemUseAnimation;
import me.udnek.coreu.custom.attribute.AttributeUtils;
import me.udnek.coreu.custom.attribute.CustomAttributeModifier;
import me.udnek.coreu.custom.attribute.CustomAttributesContainer;
import me.udnek.coreu.custom.component.CustomComponent;
import me.udnek.coreu.custom.component.CustomComponentType;
import me.udnek.coreu.custom.component.instance.AutoGeneratingFilesItem;
import me.udnek.coreu.custom.component.instance.CustomAttributedItem;
import me.udnek.coreu.custom.equipmentslot.slot.CustomEquipmentSlot;
import me.udnek.coreu.custom.equipmentslot.universal.BaseUniversalSlot;
import me.udnek.coreu.custom.equipmentslot.universal.UniversalInventorySlot;
import me.udnek.coreu.custom.item.ConstructableCustomItem;
import me.udnek.coreu.custom.item.CustomItem;
import me.udnek.coreu.custom.item.RepairData;
import me.udnek.coreu.rpgu.component.RPGUActiveItem;
import me.udnek.coreu.rpgu.component.RPGUComponents;
import me.udnek.coreu.rpgu.component.ability.active.RPGUConstructableActiveAbility;
import me.udnek.coreu.rpgu.component.ability.property.AttributeBasedProperty;
import me.udnek.coreu.rpgu.component.ability.property.EffectsProperty;
import me.udnek.coreu.rpgu.component.ability.property.function.PropertyFunctions;
import me.udnek.rpgu.RpgU;
import me.udnek.rpgu.attribute.Attributes;
import me.udnek.rpgu.component.Components;
import me.udnek.rpgu.component.ability.Abilities;
import me.udnek.rpgu.component.ability.RPGUActiveTriggerableAbility;
import me.udnek.rpgu.component.ability.property.DamageProperty;
import me.udnek.rpgu.component.ability.property.Functions;
import me.udnek.rpgu.mechanic.damaging.Damage;
import me.udnek.rpgu.mechanic.damaging.DamageUtils;
import me.udnek.rpgu.particle.AmethystSpikeParticle;
import me.udnek.rpgu.particle.ParticleUtils;
import me.udnek.rpgu.util.Utils;
import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.*;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;

public class AmethystDoloire extends ConstructableCustomItem {

    public static int CAST_TIME = (int) (0.75 * 20);
    public static final double MELEE_MAGICAL_DAMAGE_MULTIPLIER = 0.4;

    @Override
    public @NotNull Material getMaterial() {
        return Material.STONE_SWORD;
    }
    @Override
    public @NotNull String getRawId() {return "amethyst_doloire";}
    @Override
    public @Nullable List<ItemFlag> getTooltipHides() {
        return List.of(new ItemFlag[]{ItemFlag.HIDE_ATTRIBUTES});
    }

    @Override
    public @Nullable DataSupplier<ItemRarity> getRarity() {return DataSupplier.of(ItemRarity.UNCOMMON);}

    @Override
    public boolean addDefaultAttributes() {return true;}

    @Override
    public void initializeAdditionalAttributes(@NotNull ItemStack itemStack) {
        super.initializeAdditionalAttributes(itemStack);
        getComponents().set(AutoGeneratingFilesItem.HANDHELD_20X20);
        AttributeUtils.appendAttribute(itemStack, Attribute.ATTACK_DAMAGE, null, 1, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.MAINHAND);
        AttributeUtils.appendAttribute(itemStack, Attribute.ATTACK_SPEED, null, -0.4, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.MAINHAND);
    }

    @Override
    public @Nullable DataSupplier<Integer> getMaxDamage() {
        return DataSupplier.of(Material.IRON_SWORD.getDefaultData(DataComponentTypes.MAX_DAMAGE));
    }

    @Override
    protected void generateRecipes(@NotNull Consumer<@NotNull Recipe> consumer) {
        ShapedRecipe recipe = new ShapedRecipe(getNewRecipeKey(), this.getItem());
        recipe.shape(
                "CA ",
                "ASN",
                "S  ");

        recipe.setIngredient('C', new RecipeChoice.MaterialChoice(Material.AMETHYST_CLUSTER));
        recipe.setIngredient('A', new RecipeChoice.MaterialChoice(Material.AMETHYST_SHARD));
        recipe.setIngredient('S', new RecipeChoice.MaterialChoice(Material.STICK));
        recipe.setIngredient('N', new RecipeChoice.MaterialChoice(Material.NAUTILUS_SHELL));

        consumer.accept(recipe);
    }

    @Override
    public @Nullable DataSupplier<Consumable> getConsumable() {
        Consumable build = Consumable.consumable().consumeSeconds(CAST_TIME / 20f).animation(ItemUseAnimation.SPEAR).hasConsumeParticles(false)
                .sound(Registry.SOUNDS.getKeyOrThrow(Sound.INTENTIONALLY_EMPTY).key()).build();
        return DataSupplier.of(build);
    }

    @Override
    public void initializeComponents() {
        super.initializeComponents();

        CustomAttributeModifier attribute = new CustomAttributeModifier(MELEE_MAGICAL_DAMAGE_MULTIPLIER,  AttributeModifier.Operation.ADD_NUMBER, CustomEquipmentSlot.MAIN_HAND);
        getComponents().set(new CustomAttributedItem(new CustomAttributesContainer.Builder().add(Attributes.MELEE_MAGICAL_DAMAGE_MULTIPLIER, attribute).build()));

        getComponents().getOrCreateDefault(RPGUComponents.ACTIVE_ABILITY_ITEM).getComponents().set(Ability.DEFAULT);
    }

    @Override
    public @Nullable RepairData initializeRepairData() {
        return new RepairData(Material.AMETHYST_SHARD);
    }

    public static class Ability extends RPGUConstructableActiveAbility<PlayerItemConsumeEvent> implements RPGUActiveTriggerableAbility<PlayerItemConsumeEvent> {
//TODO фикс абилки она не работает
        public static final Ability DEFAULT = new Ability();

        public static double BASE_RADIUS = 0.8;
        public static double BASE_DAMAGE = 1.5;

        public Ability() {
            getComponents().set(new AttributeBasedProperty(20*15, RPGUComponents.ABILITY_COOLDOWN_TIME));
            getComponents().set(new AttributeBasedProperty(10, RPGUComponents.ABILITY_CAST_RANGE));
            getComponents().set(new AttributeBasedProperty(BASE_RADIUS, RPGUComponents.ABILITY_AREA_OF_EFFECT));
            getComponents().set(new DamageProperty(Damage.Type.MAGICAL, PropertyFunctions.LINEAR(Functions.ENTITY_TO_MP(), BASE_DAMAGE, 0.2)));
            getComponents().set(new EffectsProperty(
                    new EffectsProperty.PotionData(
                            PotionEffectType.SLOWNESS,
                            PropertyFunctions.CEIL(PropertyFunctions.ATTRIBUTE_WITH_BASE(Attributes.ABILITY_DURATION, 20d*3d)),
                            PropertyFunctions.CONSTANT(2))
            ));
        }

        @Override
        protected @NotNull ActionResult action(@NotNull CustomItem customItem, @NotNull LivingEntity livingEntity, @NotNull UniversalInventorySlot universalInventorySlot, @NotNull PlayerItemConsumeEvent playerItemConsumeEvent) {
            Location location = Utils.rayTraceBlockUnder(livingEntity);

            if (location == null) return ActionResult.PENALTY_COOLDOWN;

            final double radius = getComponents().getOrException(RPGUComponents.ABILITY_AREA_OF_EFFECT).get(livingEntity);
            final double castRange = getComponents().getOrException(RPGUComponents.ABILITY_CAST_RANGE).get(livingEntity);
            List<PotionEffect> potionEffects = getComponents().getOrException(RPGUComponents.ABILITY_EFFECTS).get(livingEntity);

            Vector direction = livingEntity.getLocation().getDirection();
            direction.setY(0).normalize().multiply(radius*2);

            Damage damage = getComponents().getOrException(Components.ABILITY_DAMAGE).get(livingEntity);

            new BukkitRunnable(){
                int count = 0;
                @Override
                public void run() {
                    count++;
                    location.add(direction);
                    new AmethystSpikeParticle((float) radius*2f).play(location);
                    ParticleUtils.circle(new ParticleBuilder(Particle.DUST).color(Color.FUCHSIA).location(location), radius);
                    Collection<LivingEntity> nearbyLivingEntities = Utils.livingEntitiesInRadiusIntersects(location, radius);

                    for (LivingEntity entity : nearbyLivingEntities){
                        potionEffects.forEach(entity::addPotionEffect);
                        DamageUtils.damage(entity, damage, livingEntity);
                    }

                    if (2*radius*count > castRange) cancel();
                }
            }.runTaskTimer(RpgU.getInstance(), 0, 1);


            return ActionResult.FULL_COOLDOWN;
        }

        @Override
        public void onConsume(@NotNull CustomItem customItem, @NotNull PlayerItemConsumeEvent event) {
            event.setCancelled(true);
            activate(customItem, event.getPlayer(), new BaseUniversalSlot(event.getHand()), event);
        }

        @Override
        public @Nullable Pair<List<String>, List<String>> getEngAndRuDescription() {
            return null;
        }

        @Override
        public @NotNull CustomComponentType<? super RPGUActiveItem, ? extends CustomComponent<? super RPGUActiveItem>> getType() {
            return Abilities.AMETHYST_DOLOIRE;
        }
    }
}