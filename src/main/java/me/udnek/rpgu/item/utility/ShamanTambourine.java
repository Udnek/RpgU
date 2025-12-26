package me.udnek.rpgu.item.utility;

import com.destroystokyo.paper.ParticleBuilder;
import io.papermc.paper.datacomponent.item.Consumable;
import io.papermc.paper.datacomponent.item.consumable.ItemUseAnimation;
import me.udnek.coreu.custom.component.CustomComponent;
import me.udnek.coreu.custom.component.CustomComponentType;
import me.udnek.coreu.custom.component.instance.AutoGeneratingFilesItem;
import me.udnek.coreu.custom.equipmentslot.universal.BaseUniversalSlot;
import me.udnek.coreu.custom.equipmentslot.universal.UniversalInventorySlot;
import me.udnek.coreu.custom.item.ConstructableCustomItem;
import me.udnek.coreu.custom.item.CustomItem;
import me.udnek.coreu.rpgu.component.RPGUActiveItem;
import me.udnek.coreu.rpgu.component.RPGUComponents;
import me.udnek.coreu.rpgu.component.ability.active.RPGUConstructableActiveAbility;
import me.udnek.coreu.rpgu.component.ability.property.AttributeBasedProperty;
import me.udnek.coreu.rpgu.component.ability.property.CastTimeProperty;
import me.udnek.coreu.rpgu.component.ability.property.function.PropertyFunctions;
import me.udnek.rpgu.component.Components;
import me.udnek.rpgu.component.ability.Abilities;
import me.udnek.rpgu.component.ability.RPGUActiveTriggerableAbility;
import me.udnek.rpgu.component.ability.property.DamageProperty;
import me.udnek.rpgu.component.ability.property.Functions;
import me.udnek.rpgu.item.Items;
import me.udnek.rpgu.mechanic.damaging.Damage;
import me.udnek.rpgu.mechanic.damaging.DamageUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.*;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Consumer;

public class ShamanTambourine extends ConstructableCustomItem{

    @Override
    public @NotNull String getRawId() {return "shaman_tambourine";}

    @Override
    public @Nullable DataSupplier<Consumable> getConsumable() {
        Consumable build = Consumable.consumable().consumeSeconds(Ability.CAST_TIME / 20f).animation(ItemUseAnimation.BOW).hasConsumeParticles(false)
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
        getComponents().set(AutoGeneratingFilesItem.HANDHELD);
        getComponents().getOrCreateDefault(RPGUComponents.ACTIVE_ABILITY_ITEM).getComponents().set(Ability.DEFAULT);
    }

    public static class Ability extends RPGUConstructableActiveAbility<PlayerItemConsumeEvent> implements RPGUActiveTriggerableAbility<PlayerItemConsumeEvent> {

        public static final Ability DEFAULT = new Ability();
        public static final int CAST_TIME = (int) (0.75 * 20);

        public Ability(){
            getComponents().set(new DamageProperty(Damage.Type.MAGICAL, PropertyFunctions.LINEAR(Functions.ENTITY_TO_MP(), 3, 1)));
            getComponents().set(new AttributeBasedProperty(20*10, RPGUComponents.ABILITY_COOLDOWN_TIME));
            getComponents().set(new AttributeBasedProperty(15, RPGUComponents.ABILITY_CAST_RANGE));
            getComponents().set(new CastTimeProperty(CAST_TIME));
        }

        @Override
        protected @NotNull ActionResult action(@NotNull CustomItem customItem, @NotNull LivingEntity livingEntity, @NotNull UniversalInventorySlot universalInventorySlot, @NotNull PlayerItemConsumeEvent playerItemConsumeEvent) {
            double castRange = getComponents().getOrException(RPGUComponents.ABILITY_CAST_RANGE).get(livingEntity);
            Location eyeLocation = livingEntity.getEyeLocation();
            Vector direction = livingEntity.getLocation().getDirection();

            RayTraceResult rayTraceResult = livingEntity.getWorld().rayTraceEntities(
                    eyeLocation, direction, castRange,
                    entity -> entity!=livingEntity);

            // WITH BIGGER RADIUS
            if (rayTraceResult == null || rayTraceResult.getHitEntity() == null){
                rayTraceResult = livingEntity.getWorld().rayTraceEntities(
                        eyeLocation, direction, castRange,
                        1,
                        entity -> entity!=livingEntity);
            }

            if (!(rayTraceResult != null && rayTraceResult.getHitEntity() instanceof LivingEntity living)) {
                ParticleBuilder builder = new ParticleBuilder(Particle.SHRIEK).count(1).location(livingEntity.getLocation().add(direction.
                        multiply(castRange)).add(0, 1, 0));
                builder.data(0);
                builder.spawn();
                return ActionResult.NO_COOLDOWN;
            }
            DamageUtils.damage(
                    living,
                    getComponents().getOrException(Components.ABILITY_DAMAGE).get(livingEntity),
                    livingEntity);
            new ParticleBuilder(Particle.SONIC_BOOM).count(1).location(rayTraceResult.getHitPosition().toLocation(livingEntity.getWorld())).spawn();
            return ActionResult.FULL_COOLDOWN;
        }

        @Override
        public @Nullable Pair<List<String>, List<String>> getEngAndRuDescription() {
            return null;
        }

        @Override
        public @NotNull CustomComponentType<? super RPGUActiveItem, ? extends CustomComponent<? super RPGUActiveItem>> getType() {
            return Abilities.SHAMAN_TAMBOURINE;
        }


        @Override
        public void onConsume(@NotNull CustomItem customItem, @NotNull PlayerItemConsumeEvent event) {
            event.setCancelled(true);
            activate(customItem, event.getPlayer(), new BaseUniversalSlot(event.getHand()), event);
        }
    }
}
