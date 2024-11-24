package me.udnek.rpgu.item.utility;

import com.destroystokyo.paper.ParticleBuilder;
import me.udnek.itemscoreu.customitem.ConstructableCustomItem;
import me.udnek.itemscoreu.customitem.CustomItem;
import me.udnek.rpgu.attribute.Attributes;
import me.udnek.rpgu.component.ConstructableActiveAbilityComponent;
import me.udnek.rpgu.effect.Effects;
import me.udnek.rpgu.lore.ActiveAbilityLorePart;
import me.udnek.rpgu.mechanic.damaging.formula.DamageFormula;
import me.udnek.rpgu.particle.ParticleUtils;
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
import org.jetbrains.annotations.Nullable;

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

        setComponent(new NatureStaffComponent());
    }

    public class NatureStaffComponent implements ConstructableActiveAbilityComponent<PlayerInteractEvent, Object> {

        public static double BASE_RADIUS = 2.5;
        public static double BASE_DURATION = 4 * 20;

        @Override
        public int getBaseCooldown() {return 20;}

        @Override
        public double getBaseCastRange() {return 15;}

        @Override
        public double getBaseAreaOfEffect() {return BASE_RADIUS;}

        @Override
        public @Nullable DamageFormula<Object> getDamage() {
            return null;
        }

        @Override
        public @NotNull ActionResult action(@NotNull CustomItem customItem, @NotNull Player player, @NotNull PlayerInteractEvent event) {
            RayTraceResult rayTraceResult = player.rayTraceBlocks(getBaseCastRange());
            if (rayTraceResult == null) return ActionResult.NO_COOLDOWN;
            Location location = rayTraceResult.getHitPosition().toLocation(player.getWorld());
            final double radius = getAreaOfEffect(player);
            Collection<LivingEntity> nearbyLivingEntities = location.getWorld().getNearbyLivingEntities(location, radius, livingEntity -> !(livingEntity.getLocation().distance(location) > radius));
            ParticleUtils.circle(new ParticleBuilder(Particle.DUST).color(Color.GREEN).location(location), radius, 5);
            final int duration = (int) (BASE_DURATION + Attributes.MAGICAL_POTENTIAL.calculate(player));

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
            ConstructableActiveAbilityComponent.super.addLoreLines(componentable);
        }

        @Override
        public void onRightClick(@NotNull CustomItem customItem, @NotNull PlayerInteractEvent event) {
            activate(customItem, event.getPlayer(), event);
        }
    }
}