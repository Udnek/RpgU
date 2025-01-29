package me.udnek.rpgu.item.utility;

import com.destroystokyo.paper.ParticleBuilder;
import io.papermc.paper.datacomponent.item.Consumable;
import io.papermc.paper.datacomponent.item.consumable.ItemUseAnimation;
import me.udnek.itemscoreu.customitem.ConstructableCustomItem;
import me.udnek.itemscoreu.customitem.CustomItem;
import me.udnek.rpgu.attribute.Attributes;
import me.udnek.rpgu.component.ComponentTypes;
import me.udnek.rpgu.component.ability.active.ConstructableActiveAbilityComponent;
import me.udnek.rpgu.component.ability.property.AttributeBasedProperty;
import me.udnek.rpgu.component.ability.property.CastTimeProperty;
import me.udnek.rpgu.component.ability.property.DamageProperty;
import me.udnek.rpgu.component.ability.property.function.MPBasedDamageFunction;
import me.udnek.rpgu.item.Items;
import me.udnek.rpgu.lore.ability.ActiveAbilityLorePart;
import me.udnek.rpgu.mechanic.damaging.DamageUtils;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Registry;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.util.RayTraceResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class ShamanTambourine extends ConstructableCustomItem{

    public static int CAST_TIME = (int) (0.75 * 20);

    @Override
    public @NotNull String getRawId() {return "shaman_tambourine";}

    @Override
    public @Nullable DataSupplier<Consumable> getConsumable() {
        Consumable build = Consumable.consumable().consumeSeconds(CAST_TIME / 20f).animation(ItemUseAnimation.BOW).hasConsumeParticles(false)
                .sound(Registry.SOUNDS.getKeyOrThrow(Sound.INTENTIONALLY_EMPTY).key()).build();
        return DataSupplier.of(build);
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

    public class ShamanTambourineComponent extends ConstructableActiveAbilityComponent<PlayerItemConsumeEvent> {

        public ShamanTambourineComponent(){
            getComponents().set(new DamageProperty(MPBasedDamageFunction.linearMageOnly(3, 1)));
            getComponents().set(new AttributeBasedProperty(20*10, ComponentTypes.ABILITY_COOLDOWN));
            getComponents().set(new AttributeBasedProperty(15, ComponentTypes.ABILITY_CAST_RANGE));
            getComponents().set(new CastTimeProperty(CAST_TIME));
        }

        @Override
        public void addLoreLines(@NotNull ActiveAbilityLorePart componentable) {
            componentable.addFullAbilityDescription(ShamanTambourine.this, 1);
            super.addLoreLines(componentable);
        }

        @Override
        public @NotNull ActionResult action(@NotNull CustomItem customItem, @NotNull LivingEntity livingEntity, @NotNull PlayerItemConsumeEvent event) {
            RayTraceResult rayTraceResult = livingEntity.getWorld().rayTraceEntities(
                    livingEntity.getEyeLocation(),
                    livingEntity.getLocation().getDirection(),
                    getComponents().getOrException(ComponentTypes.ABILITY_CAST_RANGE).get(livingEntity),
                    1,
                    entity -> entity!=livingEntity);
            if (!(rayTraceResult != null && rayTraceResult.getHitEntity() instanceof LivingEntity living)) {
                ParticleBuilder builder = new ParticleBuilder(Particle.SHRIEK).count(1).location(livingEntity.getLocation().add(livingEntity.getLocation().getDirection().
                        multiply(getComponents().getOrException(ComponentTypes.ABILITY_CAST_RANGE).get(livingEntity))).add(0, 1, 0));
                builder.data(0);
                builder.spawn();
                return ActionResult.NO_COOLDOWN;
            }
            DamageUtils.damage(
                    living,
                    getComponents().getOrException(ComponentTypes.ABILITY_DAMAGE).get(Attributes.MAGICAL_POTENTIAL.calculate(livingEntity)),
                    livingEntity);
            new ParticleBuilder(Particle.SONIC_BOOM).count(1).location(rayTraceResult.getHitPosition().toLocation(livingEntity.getWorld())).spawn();
            return ActionResult.FULL_COOLDOWN;
        }

        @Override
        public void onConsume(@NotNull CustomItem customItem, @NotNull PlayerItemConsumeEvent event) {
            event.setCancelled(true);
            activate(customItem, event.getPlayer(), event);
        }
    }
}
