package me.udnek.rpgu.item.utility;

import com.destroystokyo.paper.ParticleBuilder;
import me.udnek.itemscoreu.customitem.ConstructableCustomItem;
import me.udnek.itemscoreu.customitem.CustomItem;
import me.udnek.rpgu.RpgU;
import me.udnek.rpgu.attribute.Attributes;
import me.udnek.rpgu.component.ComponentTypes;
import me.udnek.rpgu.component.ability.active.ConstructableActiveAbilityComponent;
import me.udnek.rpgu.component.ability.active.RayTraceActiveAbility;
import me.udnek.rpgu.component.ability.property.AttributeBasedProperty;
import me.udnek.rpgu.component.ability.property.EffectsProperty;
import me.udnek.rpgu.component.ability.property.function.Functions;
import me.udnek.rpgu.component.ability.property.function.LinearMPFunction;
import me.udnek.rpgu.effect.Effects;
import me.udnek.rpgu.lore.ability.ActiveAbilityLorePart;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.function.Consumer;

public class AirElementalTome extends ConstructableCustomItem {

    @Override
    public @NotNull String getRawId() {
        return "air_elemental_tome";
    }

    @Override
    protected void generateRecipes(@NotNull Consumer<@NotNull Recipe> consumer) {
        ShapedRecipe recipe = new ShapedRecipe(getNewRecipeKey(), this.getItem());
        recipe.shape(
                " R ",
                "RBR",
                " R ");

        recipe.setIngredient('B', new RecipeChoice.MaterialChoice(Material.BOOK));
        recipe.setIngredient('R', new RecipeChoice.MaterialChoice(Material.BREEZE_ROD));

        consumer.accept(recipe);
    }


    @Override
    public void initializeComponents() {
        super.initializeComponents();

        getComponents().set(new AirElementalTomeComponent());
    }

    public class AirElementalTomeComponent extends ConstructableActiveAbilityComponent<PlayerInteractEvent> implements RayTraceActiveAbility<PlayerInteractEvent> {

        public static double AOE_RADIUS = 2.5;
        public static double DURATION = 4 * 20;
        public static double HEIGHT = 15;
        public static double UP_DURATION = DURATION / 5;
        public static double EFFECT_DURATION = 10;



        public AirElementalTomeComponent(){
            getComponents().set(new AttributeBasedProperty(20*20, ComponentTypes.ABILITY_COOLDOWN));
            getComponents().set(new AttributeBasedProperty(15, ComponentTypes.ABILITY_CAST_RANGE));
            getComponents().set(new AttributeBasedProperty(AOE_RADIUS, ComponentTypes.ABILITY_AREA_OF_EFFECT));
            getComponents().set(new EffectsProperty(new EffectsProperty.PotionData(
                    Effects.HEAVY_FALLING.getBukkitType(),
                    Functions.CEIL(Functions.ATTRIBUTE(Attributes.ABILITY_DURATION, Functions.CONSTANT(EFFECT_DURATION))),
                    Functions.FLOOR(Functions.APPLY_MP(new LinearMPFunction(2, 0.2)))
            )));
        }

        @Override
        public @NotNull ActionResult action(@NotNull CustomItem customItem, @NotNull LivingEntity livingEntity, @NotNull PlayerInteractEvent event) {
            Collection<LivingEntity> livingEntitiesInRadius = findLivingEntitiesInRayTraceRadius(livingEntity, new ParticleBuilder(Particle.SMALL_GUST));

            if (livingEntitiesInRadius == null) return ActionResult.NO_COOLDOWN;
            if (livingEntitiesInRadius.isEmpty()) {return ActionResult.PENALTY_COOLDOWN;}
            for (LivingEntity livingEntityInRadius : livingEntitiesInRadius) {
                new BukkitRunnable() {
                    int count = 0;
                    @Override
                    public void run() {
                        Location locationEntity = livingEntityInRadius.getLocation();
                        if (count >= 0 && count < UP_DURATION) {
                            livingEntityInRadius.setVelocity(new Vector(0, HEIGHT / UP_DURATION, 0));
                            new ParticleBuilder(Particle.GUST_EMITTER_SMALL).count(0).location(locationEntity).spawn();
                        } else if (count >= UP_DURATION && count < DURATION && (count % 5 == 0)) {
                            new ParticleBuilder(Particle.GUST).count(3).location(locationEntity).offset(1,0,1).spawn();
                        } else if (count == DURATION) {
                            if (livingEntityInRadius == livingEntity) Effects.NO_FALL_DAMAGE.applyInvisible(livingEntity, 10, 0);
                            else getComponents().getOrException(ComponentTypes.ABILITY_EFFECTS).applyOn(livingEntity, livingEntityInRadius);
                            new ParticleBuilder(Particle.GUST_EMITTER_LARGE).count(4).location(locationEntity.add(0, 2, 0)).spawn();
                        } else if (count > DURATION) cancel();

                        if (count == UP_DURATION) {
                            Effects.NO_GRAVITY.applyInvisible(livingEntityInRadius, (int) (UP_DURATION * 4), 0);
                            livingEntityInRadius.setVelocity(new Vector());
                        }

                        count++;
                    }
                }.runTaskTimer(RpgU.getInstance(), 0, 1);
            }

            return ActionResult.FULL_COOLDOWN;
        }

        @Override
        public void addLoreLines(@NotNull ActiveAbilityLorePart componentable) {
            componentable.addFullAbilityDescription(AirElementalTome.this, 2);
            super.addLoreLines(componentable);
        }

        @Override
        public void onRightClick(@NotNull CustomItem customItem, @NotNull PlayerInteractEvent event) {
            activate(customItem, event.getPlayer(), event);
        }


    }
}