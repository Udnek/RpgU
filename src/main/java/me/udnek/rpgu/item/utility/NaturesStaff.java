package me.udnek.rpgu.item.utility;

import com.destroystokyo.paper.ParticleBuilder;
import me.udnek.coreu.custom.component.CustomComponent;
import me.udnek.coreu.custom.component.CustomComponentType;
import me.udnek.coreu.custom.component.instance.AutoGeneratingFilesItem;
import me.udnek.coreu.custom.equipmentslot.universal.UniversalInventorySlot;
import me.udnek.coreu.custom.item.ConstructableCustomItem;
import me.udnek.coreu.custom.item.CustomItem;
import me.udnek.coreu.rpgu.component.RPGUActiveItem;
import me.udnek.coreu.rpgu.component.RPGUComponents;
import me.udnek.coreu.rpgu.component.ability.active.RPGUConstructableActiveAbility;
import me.udnek.coreu.rpgu.component.ability.active.RayTraceActiveAbility;
import me.udnek.coreu.rpgu.component.ability.property.AttributeBasedProperty;
import me.udnek.coreu.rpgu.component.ability.property.EffectsProperty;
import me.udnek.coreu.rpgu.component.ability.property.function.PropertyFunctions;
import me.udnek.rpgu.attribute.Attributes;
import me.udnek.rpgu.component.ability.Abilities;
import me.udnek.rpgu.component.ability.property.Functions;
import me.udnek.rpgu.effect.Effects;
import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;

public class NaturesStaff extends ConstructableCustomItem {

    @Override
    public @NotNull String getRawId() {
        return "natures_staff";
    }

    @Override
    protected void generateRecipes(@NotNull Consumer<@NotNull Recipe> consumer) {
        ShapedRecipe recipe = new ShapedRecipe(getNewRecipeKey(), this.getItem());
        recipe.shape(
                " RS",
                " BS",
                "SR ");

        recipe.setIngredient('B', new RecipeChoice.MaterialChoice(Material.GLOW_BERRIES));
        recipe.setIngredient('R', new RecipeChoice.MaterialChoice(Material.HANGING_ROOTS));
        recipe.setIngredient('S', new RecipeChoice.MaterialChoice(Material.STICK));

        consumer.accept(recipe);
    }


    @Override
    public void initializeComponents() {
        super.initializeComponents();
        getComponents().set(AutoGeneratingFilesItem.HANDHELD_20X20);
        getComponents().getOrCreateDefault(RPGUComponents.ACTIVE_ABILITY_ITEM).getComponents().set(Ability.DEFAULT);
    }

    public static class Ability extends RPGUConstructableActiveAbility<PlayerInteractEvent> implements RayTraceActiveAbility<PlayerInteractEvent> {

        public static final Ability DEFAULT = new Ability();

        public static final double BASE_RADIUS = 2.5;
        public static final double BASE_DURATION = 2 * 20;
        public static final double DURATION_PER_MP = 10;

        public Ability() {
            getComponents().set(new AttributeBasedProperty(20 * 20, RPGUComponents.ABILITY_COOLDOWN_TIME));
            getComponents().set(new AttributeBasedProperty(15, RPGUComponents.ABILITY_CAST_RANGE));
            getComponents().set(new AttributeBasedProperty(BASE_RADIUS, RPGUComponents.ABILITY_AREA_OF_EFFECT));
            getComponents().set(new EffectsProperty(new EffectsProperty.PotionData(
                    Effects.ROOT_EFFECT.getBukkitType(),
                    PropertyFunctions.CEIL(
                            PropertyFunctions.ATTRIBUTE_WITH_BASE(Attributes.ABILITY_DURATION,
                                    PropertyFunctions.LINEAR(Functions.ENTITY_TO_MP(), BASE_DURATION, DURATION_PER_MP))),
                    PropertyFunctions.CONSTANT(0)))
            );
        }


        @Override
        protected @NotNull ActionResult action(@NotNull CustomItem customItem, @NotNull LivingEntity livingEntity, @NotNull UniversalInventorySlot universalInventorySlot, @NotNull PlayerInteractEvent event) {
            Collection<LivingEntity> livingEntitiesInRayTraceRadius = findLivingEntitiesInRayTraceRadius(livingEntity, new ParticleBuilder(Particle.DUST).color(Color.GREEN));

            EffectsProperty effects = getComponents().getOrException(RPGUComponents.ABILITY_EFFECTS);

            if (livingEntitiesInRayTraceRadius == null || livingEntitiesInRayTraceRadius.isEmpty()) return ActionResult.PENALTY_COOLDOWN;


            for (LivingEntity livingEntitieInRayTraceRadius : livingEntitiesInRayTraceRadius) {
                if (livingEntitieInRayTraceRadius == livingEntity) continue;
                effects.applyOn(livingEntity, livingEntitieInRayTraceRadius);
            }

            return ActionResult.FULL_COOLDOWN;
        }

        @Override
        public @Nullable Pair<List<String>, List<String>> getEngAndRuDescription() {
            return null;
        }

        @Override
        public @NotNull CustomComponentType<? super RPGUActiveItem, ? extends CustomComponent<? super RPGUActiveItem>> getType() {
            return Abilities.NATURES_STAFF;
        }
    }
}