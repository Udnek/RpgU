package me.udnek.rpgu.item.utility;

import com.destroystokyo.paper.ParticleBuilder;
import me.udnek.itemscoreu.customitem.ConstructableCustomItem;
import me.udnek.itemscoreu.customitem.CustomItem;
import me.udnek.rpgu.component.ComponentTypes;
import me.udnek.rpgu.component.ability.ConstructableActiveAbilityComponent;
import me.udnek.rpgu.component.ability.RayTraceActiveAbility;
import me.udnek.rpgu.component.ability.property.AttributeBasedProperty;
import me.udnek.rpgu.component.ability.property.type.AttributeBasedPropertyType;
import me.udnek.rpgu.effect.Effects;
import me.udnek.rpgu.equipment.slot.EquipmentSlots;
import me.udnek.rpgu.item.Items;
import me.udnek.rpgu.lore.ActiveAbilityLorePart;
import me.udnek.rpgu.mechanic.magicpotential.LinearMPFormula;
import me.udnek.rpgu.particle.RootParticle;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
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
    public @NotNull Material getMaterial() {
        return Material.GUNPOWDER;
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
        getComponents().set(new NaturesStaffComponent());
    }

    public class NaturesStaffComponent extends ConstructableActiveAbilityComponent<PlayerInteractEvent> implements RayTraceActiveAbility<PlayerInteractEvent> {

        public static double BASE_RADIUS = 2.5;
        public static double BASE_DURATION = 2 * 20;
        public static double DURATION_PER_MP = 10;

        public NaturesStaffComponent() {
            getComponents().set(AttributeBasedProperty.from(20*20, ComponentTypes.ABILITY_COOLDOWN));
            getComponents().set(AttributeBasedProperty.from(15, ComponentTypes.ABILITY_CAST_RANGE));
            getComponents().set(AttributeBasedProperty.from(BASE_RADIUS, ComponentTypes.ABILITY_AREA_OF_EFFECT));
            getComponents().set(new AttributeBasedProperty(0){

                final LinearMPFormula formula = new LinearMPFormula(BASE_DURATION, DURATION_PER_MP);

                @Override
                public void describe(@NotNull ActiveAbilityLorePart componentable) {
                    ComponentTypes.ABILITY_DURATION.describe(formula.getDescriptionWithNumberModifier(value -> value/20d), componentable);
                }

                @Override
                public @NotNull AttributeBasedPropertyType getType() {
                    return ComponentTypes.ABILITY_DURATION;
                }

                @Override
                public @NotNull Double getBase() {
                    return formula.apply(0d);
                }

                @Override
                public @NotNull Double get(@NotNull Player player) {
                    return super.getWithBase(player, formula.apply(player));
                }
            });
        }



        @Override
        public @NotNull ActionResult action(@NotNull CustomItem customItem, @NotNull Player player, @NotNull PlayerInteractEvent event) {
            Collection<LivingEntity> livingEntitiesInRayTraceRadius = findLivingEntitiesInRayTraceRadius(player, new ParticleBuilder(Particle.DUST).color(Color.GREEN));
            final int duration = getComponents().getOrException(ComponentTypes.ABILITY_DURATION).get(player).intValue();

            if (livingEntitiesInRayTraceRadius == null || livingEntitiesInRayTraceRadius.isEmpty()) return ActionResult.PENALTY_COOLDOWN;
            for (LivingEntity livingEntity : livingEntitiesInRayTraceRadius) {
                Effects.ROOT_EFFECT.apply(livingEntity, duration, 0);
                new RootParticle(livingEntity).play();
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