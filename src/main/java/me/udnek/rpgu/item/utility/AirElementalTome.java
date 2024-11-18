package me.udnek.rpgu.item.utility;

import com.destroystokyo.paper.ParticleBuilder;
import me.udnek.itemscoreu.customitem.ConstructableCustomItem;
import me.udnek.itemscoreu.customitem.CustomItem;
import me.udnek.rpgu.RpgU;
import me.udnek.rpgu.component.ConstructableActiveAbilityComponent;
import me.udnek.rpgu.effect.Effects;
import me.udnek.rpgu.lore.ActiveAbilityLorePart;
import me.udnek.rpgu.mechanic.damaging.formula.DamageFormula;
import me.udnek.rpgu.particle.ParticleUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.function.Consumer;

public class AirElementalTome extends ConstructableCustomItem {

    @Override
    public @NotNull String getRawId() {
        return "air_elemental_tome";
    }

    @Override
    public @NotNull Material getMaterial() {
        return Material.GUNPOWDER;
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

        setComponent(new AirElementalTomeComponent());
    }

    public class AirElementalTomeComponent implements ConstructableActiveAbilityComponent<PlayerInteractEvent, Object> {

        public static double BASE_RADIUS = 2.5;
        public static double CAST_TIME = 200;
        public static double UP_TIME = CAST_TIME / 5;
        public static double HEIGHT = 10;

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
            double RADIUS = getAreaOfEffect(player);
            Collection<LivingEntity> nearbyLivingEntities = location.getWorld().getNearbyLivingEntities(location, RADIUS, RADIUS, RADIUS, livingEntity -> !(livingEntity.getLocation().distance(location) > 5));
            ParticleUtils.summonCircle(new ParticleBuilder(Particle.SMALL_GUST).location(location), RADIUS);

            if (nearbyLivingEntities.isEmpty()) {return ActionResult.APPLY_COOLDOWN;}
            for (LivingEntity livingEntity : nearbyLivingEntities) {
                new BukkitRunnable() {
                    int count = 0;
                    @Override
                    public void run() {
                        Location locationEntity = livingEntity.getLocation();
                        if (count >= 0 && count < UP_TIME) {
                            livingEntity.setVelocity(new Vector(0, HEIGHT / UP_TIME, 0));
                            new ParticleBuilder(Particle.GUST_EMITTER_SMALL).count(0).location(locationEntity).spawn();
                        } else if (count >= UP_TIME && count < CAST_TIME) {
                            livingEntity.setVelocity(new Vector());
                            new ParticleBuilder(Particle.GUST).count(3).location(locationEntity).offset(1,0,1).spawn();
                        } else if (count == CAST_TIME) {
                            if (livingEntity == player) Effects.NO_FALL_DAMAGE.apply(player, 10, 0);
                            else Effects.INCREASED_FALL_DAMAGE.apply(livingEntity, 10, 1) ;
                            livingEntity.setVelocity(new Vector(0, -4, 0));
                            new ParticleBuilder(Particle.GUST_EMITTER_LARGE).count(4).location(locationEntity.add(0, 2, 0)).spawn();
                        } else cancel();
                        if (count == UP_TIME) Effects.NO_GRAVITY.apply(livingEntity, (int) (UP_TIME * 4), 0);

                        count++;
                    }
                }.runTaskTimer(RpgU.getInstance(), 0, 1);
            }

            return ActionResult.APPLY_COOLDOWN;
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