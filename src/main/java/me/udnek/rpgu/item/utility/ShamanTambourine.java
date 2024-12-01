package me.udnek.rpgu.item.utility;

import com.destroystokyo.paper.ParticleBuilder;
import me.udnek.itemscoreu.customitem.ConstructableCustomItem;
import me.udnek.itemscoreu.customitem.CustomItem;
import me.udnek.itemscoreu.nms.ConsumableAnimation;
import me.udnek.itemscoreu.nms.ConsumableComponent;
import me.udnek.rpgu.attribute.Attributes;
import me.udnek.rpgu.component.ComponentTypes;
import me.udnek.rpgu.component.ability.ConstructableActiveAbilityComponent;
import me.udnek.rpgu.component.ability.property.AttributeBasedProperty;
import me.udnek.rpgu.component.ability.property.CastTimeProperty;
import me.udnek.rpgu.component.ability.property.DamageProperty;
import me.udnek.rpgu.item.Items;
import me.udnek.rpgu.mechanic.damaging.DamageUtils;
import me.udnek.rpgu.mechanic.damaging.formula.MPBasedDamageFormula;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.RayTraceResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class ShamanTambourine extends ConstructableCustomItem{

    public static int CAST_TIME = (int) (0.75 * 20);

    @Override
    public @NotNull String getRawId() {return "shaman_tambourine";}
    @Override
    public @NotNull Material getMaterial() {return Material.GUNPOWDER;}

    @Override
    public @Nullable ConsumableComponent getConsumable() {
        ConsumableComponent component = new ConsumableComponent();
        component.setConsumeTicks(CAST_TIME);
        component.setAnimation(ConsumableAnimation.BOW);
        component.setHasConsumeParticles(false);
        component.setSound(null);
        return component;
    }

    @Override
    protected void generateRecipes(@NotNull Consumer<@NotNull Recipe> consumer) {
        ShapedRecipe recipe = new ShapedRecipe(getNewRecipeKey(), this.getItem());
        recipe.shape(
                "RSB",
                "SFS",
                " S ");

        recipe.setIngredient('S', new RecipeChoice.MaterialChoice(Material.STICK));
        recipe.setIngredient('F', new RecipeChoice.ExactChoice(Items.FABRIC.getItem()));
        recipe.setIngredient('B', new RecipeChoice.MaterialChoice(Material.FEATHER));
        recipe.setIngredient('R', new RecipeChoice.ExactChoice(Items.ESOTERIC_SALVE.getItem()));

        consumer.accept(recipe);
    }

    @Override
    public void initializeComponents() {
        super.initializeComponents();
        getComponents().set(new ShamanTambourineComponent());
    }

    public static class ShamanTambourineComponent extends ConstructableActiveAbilityComponent<PlayerItemConsumeEvent> {

        public ShamanTambourineComponent(){
            getComponents().set(new DamageProperty(MPBasedDamageFormula.linearMageOnly(3, 1)));
            getComponents().set(AttributeBasedProperty.from(20, ComponentTypes.ABILITY_COOLDOWN));
            getComponents().set(AttributeBasedProperty.from(15, ComponentTypes.ABILITY_CAST_RANGE));
            getComponents().set(new CastTimeProperty(CAST_TIME));
        }

        @Override
        public @NotNull ActionResult action(@NotNull CustomItem customItem, @NotNull Player player, @NotNull PlayerItemConsumeEvent event) {
            RayTraceResult rayTraceResult = player.getWorld().rayTraceEntities(
                    player.getEyeLocation(),
                    player.getLocation().getDirection(),
                    getComponents().getOrException(ComponentTypes.ABILITY_CAST_RANGE).get(player),
                    1,
                    entity -> entity!=player);
            if (!(rayTraceResult != null && rayTraceResult.getHitEntity() instanceof LivingEntity living)) {
                ParticleBuilder builder = new ParticleBuilder(Particle.SHRIEK).count(1).location(player.getLocation().add(player.getLocation().getDirection().multiply(getComponents().get(ComponentTypes.ABILITY_CAST_RANGE).get(player))).add(0, 1, 0));
                builder.data(0);
                builder.spawn();
                return ActionResult.NO_COOLDOWN;
            }
            living.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 20, 0));
            DamageUtils.damage(living, getComponents().getOrException(ComponentTypes.ABILITY_DAMAGE).get(Attributes.MAGICAL_POTENTIAL.calculate(player)), player);
            new ParticleBuilder(Particle.SONIC_BOOM).count(1).location(rayTraceResult.getHitPosition().toLocation(player.getWorld())).spawn();
            return ActionResult.FULL_COOLDOWN;
        }

        @Override
        public void onConsume(@NotNull CustomItem customItem, @NotNull PlayerItemConsumeEvent event) {
            event.setCancelled(true);
            activate(customItem, event.getPlayer(), event);
        }
    }
}