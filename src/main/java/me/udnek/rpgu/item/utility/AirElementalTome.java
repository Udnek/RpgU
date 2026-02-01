package me.udnek.rpgu.item.utility;

import com.destroystokyo.paper.ParticleBuilder;
import me.udnek.coreu.custom.component.CustomComponent;
import me.udnek.coreu.custom.component.CustomComponentType;
import me.udnek.coreu.custom.component.instance.TranslatableThing;
import me.udnek.coreu.custom.equipment.universal.BaseUniversalSlot;
import me.udnek.coreu.custom.equipment.universal.UniversalInventorySlot;
import me.udnek.coreu.custom.item.ConstructableCustomItem;
import me.udnek.coreu.custom.item.CustomItem;
import me.udnek.coreu.rpgu.attribute.RPGUAttributes;
import me.udnek.coreu.rpgu.component.RPGUActiveItem;
import me.udnek.coreu.rpgu.component.RPGUComponents;
import me.udnek.coreu.rpgu.component.ability.active.RPGUConstructableActiveAbility;
import me.udnek.coreu.rpgu.component.ability.active.RayTraceActiveAbility;
import me.udnek.coreu.rpgu.component.ability.property.AttributeBasedProperty;
import me.udnek.coreu.rpgu.component.ability.property.EffectsProperty;
import me.udnek.coreu.rpgu.component.ability.property.function.PropertyFunctions;
import me.udnek.coreu.util.Utils;
import me.udnek.rpgu.RpgU;
import me.udnek.rpgu.component.ability.Abilities;
import me.udnek.rpgu.component.ability.RPGUActiveTriggerableAbility;
import me.udnek.rpgu.component.ability.property.Functions;
import me.udnek.rpgu.effect.Effects;
import net.kyori.adventure.text.Component;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.util.TriConsumer;
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
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

public class AirElementalTome extends ConstructableCustomItem {

    @Override
    public @NotNull String getRawId() {
        return "air_elemental_tome";
    }
    @Override
    public @Nullable TranslatableThing getTranslations() {
        return TranslatableThing.ofEngAndRu("Air Elemental Tome", "Фолиант элементаля воздуха");
    }
    @Override
    protected void generateRecipes(@NotNull Consumer<Recipe> consumer) {
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

        getComponents().getOrCreateDefault(RPGUComponents.ACTIVE_ABILITY_ITEM).getComponents().set(Ability.DEFAULT);
    }

    public static class Ability extends RPGUConstructableActiveAbility<PlayerInteractEvent>
            implements RayTraceActiveAbility<PlayerInteractEvent>, RPGUActiveTriggerableAbility<PlayerInteractEvent> {

        public static final double HEIGHT = 15;

        public static final Ability DEFAULT = new Ability();

        public Ability(){
            getComponents().set(new AttributeBasedProperty(20*20, RPGUComponents.ABILITY_COOLDOWN_TIME));
            getComponents().set(new AttributeBasedProperty(15, RPGUComponents.ABILITY_CAST_RANGE));
            getComponents().set(new AttributeBasedProperty(2.5, RPGUComponents.ABILITY_AREA_OF_EFFECT));
            getComponents().set(new AttributeBasedProperty(4*20, RPGUComponents.ABILITY_DURATION));
            getComponents().set(new EffectsProperty(new EffectsProperty.PotionData(
                    Effects.HEAVY_FALLING.getBukkitType(),
                    PropertyFunctions.CEIL(PropertyFunctions.ATTRIBUTE_WITH_BASE(RPGUAttributes.ABILITY_DURATION, PropertyFunctions.CONSTANT(20*4d))),
                    PropertyFunctions.FLOOR(PropertyFunctions.LINEAR(Functions.ENTITY_TO_MP(), 2d, 0.2))
            )));
        }

        @Override
        protected @NotNull ActionResult action(@NotNull CustomItem customItem, @NotNull LivingEntity livingEntity, @NotNull UniversalInventorySlot universalInventorySlot, @NotNull PlayerInteractEvent event) {
            Collection<LivingEntity> livingEntitiesInRadius = findLivingEntitiesInRayTraceRadius(livingEntity, new ParticleBuilder(Particle.SMALL_GUST));

            if (livingEntitiesInRadius == null) return ActionResult.NO_COOLDOWN;
            if (livingEntitiesInRadius.isEmpty()) return ActionResult.PENALTY_COOLDOWN;
            for (LivingEntity livingEntityInRadius : livingEntitiesInRadius) {
                new BukkitRunnable() {
                    int count = 0;
                    final int duration = getComponents().getOrException(RPGUComponents.ABILITY_DURATION).get(livingEntity).intValue();
                    final int upDuration = duration/5;
                    @Override
                    public void run() {
                        Location locationEntity = livingEntityInRadius.getLocation();
                        if (count < upDuration) {
                            livingEntityInRadius.setVelocity(new Vector(0, HEIGHT / upDuration, 0));
                            new ParticleBuilder(Particle.GUST_EMITTER_SMALL).count(0).location(locationEntity).spawn();
                        } else if (count < duration && (count % 5 == 0)) {
                            new ParticleBuilder(Particle.GUST).count(3).location(locationEntity).offset(1,0,1).spawn();
                        } else if (count >= duration) {
                            if (livingEntityInRadius == livingEntity)
                                Effects.NO_FALL_DAMAGE.applyInvisible(livingEntity, 20 * 2, 0);
                            else
                                getComponents().getOrException(RPGUComponents.ABILITY_EFFECTS).applyOn(livingEntity, livingEntityInRadius);
                            new ParticleBuilder(Particle.GUST_EMITTER_LARGE).count(4).location(locationEntity.add(0, 2, 0)).spawn();
                            cancel();
                        }

                        if (count == upDuration) {
                            Effects.NO_GRAVITY.applyInvisible(livingEntityInRadius, upDuration * 4, 0);
                            livingEntityInRadius.setVelocity(new Vector());
                        }

                        count++;
                    }
                }.runTaskTimer(RpgU.getInstance(), 0, 1);
            }

            return ActionResult.FULL_COOLDOWN;
        }

        @Override
        public void onRightClick(@NotNull CustomItem customItem, @NotNull PlayerInteractEvent event) {
            activate(customItem, event.getPlayer(), new BaseUniversalSlot(Objects.requireNonNull(event.getHand())) , event);
        }

        @Override
        public void getEngAndRuProperties(TriConsumer<String, String, List<Component>> Eng_Ru_Args) {
            super.getEngAndRuProperties(Eng_Ru_Args);
            Eng_Ru_Args.accept("Lift Height: %s blocks", "Высота подъёма: %s блоков", List.of(Component.text(Utils.roundToTwoDigits(HEIGHT))));
        }

        @Override
        public @Nullable Pair<List<String>, List<String>> getEngAndRuDescription() {
            return Pair.of(List.of("Raises targets into the air", " and makes them fall with increased damage"),
                    List.of("Поднимает цели в воздух", " и ударяет о землю с увеличенным уроном"));
        }

        @Override
        public @NotNull CustomComponentType<? super RPGUActiveItem, ? extends CustomComponent<? super RPGUActiveItem>> getType() {
            return Abilities.AIR_ELEMENTAL_TOME;
        }
    }
}