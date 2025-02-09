package me.udnek.rpgu.item.utility;

import com.destroystokyo.paper.ParticleBuilder;
import me.udnek.itemscoreu.customcomponent.instance.AutoGeneratingFilesItem;
import me.udnek.itemscoreu.customitem.ConstructableCustomItem;
import me.udnek.itemscoreu.customitem.CustomItem;
import me.udnek.rpgu.attribute.Attributes;
import me.udnek.rpgu.component.ComponentTypes;
import me.udnek.rpgu.component.ability.active.ConstructableActiveAbilityComponent;
import me.udnek.rpgu.component.ability.active.RayTraceActiveAbility;
import me.udnek.rpgu.component.ability.property.AttributeBasedProperty;
import me.udnek.rpgu.component.ability.property.EffectsProperty;
import me.udnek.rpgu.component.ability.property.function.Functions;
import me.udnek.rpgu.component.ability.property.function.LinearMPFunction;
import me.udnek.rpgu.effect.Effects;
import me.udnek.itemscoreu.customequipmentslot.UniversalInventorySlot;
import me.udnek.rpgu.lore.ability.ActiveAbilityLorePart;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
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
        getComponents().set(new NaturesStaffComponent());
    }

    public class NaturesStaffComponent extends ConstructableActiveAbilityComponent<PlayerInteractEvent> implements RayTraceActiveAbility<PlayerInteractEvent> {

        public static double BASE_RADIUS = 2.5;
        public static double BASE_DURATION = 2 * 20;
        public static double DURATION_PER_MP = 10;

        public NaturesStaffComponent() {
            getComponents().set(new AttributeBasedProperty(20 * 20, ComponentTypes.ABILITY_COOLDOWN));
            getComponents().set(new AttributeBasedProperty(15, ComponentTypes.ABILITY_CAST_RANGE));
            getComponents().set(new AttributeBasedProperty(BASE_RADIUS, ComponentTypes.ABILITY_AREA_OF_EFFECT));
            getComponents().set(new EffectsProperty(new EffectsProperty.PotionData(
                    Effects.ROOT_EFFECT.getBukkitType(),
                    Functions.CEIL(Functions.ATTRIBUTE(Attributes.ABILITY_DURATION, Functions.APPLY_MP(new LinearMPFunction(BASE_DURATION, DURATION_PER_MP)))),
                    Functions.CONSTANT(0)))
            );
        }



        @Override
        public @NotNull ActionResult action(@NotNull CustomItem customItem, @NotNull LivingEntity livingEntity, @NotNull UniversalInventorySlot slot, @NotNull PlayerInteractEvent event) {
            Collection<LivingEntity> livingEntitiesInRayTraceRadius = findLivingEntitiesInRayTraceRadius(livingEntity, new ParticleBuilder(Particle.DUST).color(Color.GREEN));

            EffectsProperty effects = getComponents().getOrException(ComponentTypes.ABILITY_EFFECTS);

            if (livingEntitiesInRayTraceRadius == null || livingEntitiesInRayTraceRadius.isEmpty()) return ActionResult.PENALTY_COOLDOWN;


            for (LivingEntity livingEntitieInRayTraceRadius : livingEntitiesInRayTraceRadius) {
                if (livingEntitieInRayTraceRadius == livingEntity) continue;
                effects.applyOn(livingEntity, livingEntitieInRayTraceRadius);
            }

            return ActionResult.FULL_COOLDOWN;
        }

        @Override
        public void addLoreLines(@NotNull ActiveAbilityLorePart componentable) {
            componentable.addFullAbilityDescription(NaturesStaff.this, 1);
            super.addLoreLines(componentable);
        }

        @Override
        public void onRightClick(@NotNull CustomItem customItem, @NotNull PlayerInteractEvent event) {
            activate(customItem, event.getPlayer(), event);
        }


    }
}