package me.udnek.rpgu.item.utility;

import com.destroystokyo.paper.ParticleBuilder;
import me.udnek.itemscoreu.customitem.ConstructableCustomItem;
import me.udnek.itemscoreu.customitem.CustomItem;
import me.udnek.rpgu.component.ComponentTypes;
import me.udnek.rpgu.component.ability.ConstructableActiveAbilityComponent;
import me.udnek.rpgu.component.ability.property.AttributeBasedProperty;
import me.udnek.rpgu.component.ability.property.type.AttributeBasedPropertyType;
import me.udnek.rpgu.effect.Effects;
import me.udnek.rpgu.lore.ActiveAbilityLorePart;
import me.udnek.rpgu.mechanic.magicpotential.LinearMPFormula;
import me.udnek.rpgu.particle.ParticleUtils;
import me.udnek.rpgu.util.Utils;
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
import org.bukkit.util.RayTraceResult;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.function.Consumer;

public class NatureStaff extends ConstructableCustomItem {

    @Override
    public @NotNull String getRawId() {
        return "nature_staff";
    }

    @Override
    public @NotNull Material getMaterial() {
        return Material.GUNPOWDER;
    }

    @Override
    protected void generateRecipes(@NotNull Consumer<@NotNull Recipe> consumer) {
        /*ShapedRecipe recipe = new ShapedRecipe(getNewRecipeKey(), this.getItem());
        recipe.shape(
                " R ",
                "RBR",
                " R ");

        recipe.setIngredient('B', new RecipeChoice.MaterialChoice(Material.BOOK));
        recipe.setIngredient('R', new RecipeChoice.MaterialChoice(Material.BREEZE_ROD));

        consumer.accept(recipe);*/
    }


    @Override
    public void initializeComponents() {
        super.initializeComponents();
        getComponents().set(new NatureStaffComponent());
    }

    public class NatureStaffComponent extends ConstructableActiveAbilityComponent<PlayerInteractEvent> {

        public static double BASE_RADIUS = 2.5;
        public static double BASE_DURATION = 2 * 20;
        public static double DURATION_PER_MP = 10;

        public NatureStaffComponent() {
            getComponents().set(AttributeBasedProperty.from(20, ComponentTypes.ABILITY_COOLDOWN));
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
            RayTraceResult rayTraceResult = player.rayTraceBlocks(getComponents().get(ComponentTypes.ABILITY_CAST_RANGE).get(player));
            if (rayTraceResult == null) return ActionResult.NO_COOLDOWN;
            Location location = rayTraceResult.getHitPosition().toLocation(player.getWorld());
            final double radius = getComponents().get(ComponentTypes.ABILITY_AREA_OF_EFFECT).get(player);
            Collection<LivingEntity> nearbyLivingEntities = Utils.livingEntitiesInRadius(location, radius);
            ParticleUtils.circle(new ParticleBuilder(Particle.DUST).color(Color.GREEN).location(location), radius, 5);
            final int duration = getComponents().get(ComponentTypes.ABILITY_DURATION).get(player).intValue();

            if (nearbyLivingEntities.isEmpty()) {return ActionResult.FULL_COOLDOWN;}
            for (LivingEntity livingEntity : nearbyLivingEntities) {
                Effects.ROOT_EFFECT.apply(livingEntity, duration, 0);
            }

            return ActionResult.FULL_COOLDOWN;
        }

        @Override
        public void addLoreLines(@NotNull ActiveAbilityLorePart componentable) {
            componentable.add(Component.translatable(getRawItemName() + ".ability.0").color(NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false));
            componentable.add(Component.translatable(getRawItemName() + ".ability.1").color(NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false));
            componentable.add(Component.translatable(getRawItemName() + ".ability.2").color(NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false));
            super.addLoreLines(componentable);
        }

        @Override
        public void onRightClick(@NotNull CustomItem customItem, @NotNull PlayerInteractEvent event) {
            activate(customItem, event.getPlayer(), event);
        }


    }
}