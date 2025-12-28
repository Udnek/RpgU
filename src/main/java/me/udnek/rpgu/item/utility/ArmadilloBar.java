package me.udnek.rpgu.item.utility;

import me.udnek.coreu.custom.component.CustomComponent;
import me.udnek.coreu.custom.component.CustomComponentType;
import me.udnek.coreu.custom.component.instance.AutoGeneratingFilesItem;
import me.udnek.coreu.custom.component.instance.TranslatableThing;
import me.udnek.coreu.custom.equipment.universal.BaseUniversalSlot;
import me.udnek.coreu.custom.equipment.universal.UniversalInventorySlot;
import me.udnek.coreu.custom.item.ConstructableCustomItem;
import me.udnek.coreu.custom.item.CustomItem;
import me.udnek.coreu.rpgu.component.RPGUActiveItem;
import me.udnek.coreu.rpgu.component.RPGUComponents;
import me.udnek.coreu.rpgu.component.ability.active.RPGUConstructableActiveAbility;
import me.udnek.coreu.rpgu.component.ability.property.AttributeBasedProperty;
import me.udnek.coreu.rpgu.component.ability.property.EffectsProperty;
import me.udnek.coreu.rpgu.component.ability.property.function.PropertyFunctions;
import me.udnek.rpgu.RpgU;
import me.udnek.rpgu.component.ability.Abilities;
import me.udnek.rpgu.component.ability.RPGUActiveTriggerableAbility;
import me.udnek.rpgu.effect.Effects;
import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemRarity;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

public class ArmadilloBar extends ConstructableCustomItem {

    @Override
    public @NotNull String getRawId() {
        return "armadillo_bar";
    }

    @Override
    public @Nullable TranslatableThing getTranslations() {
        return TranslatableThing.ofEngAndRu("Armadillo Bar", "Посох броненосца");
    }

    @Override
    public @Nullable DataSupplier<Integer> getMaxStackSize() {return DataSupplier.of(1);}

    @Override
    protected void generateRecipes(@NotNull Consumer<@NotNull Recipe> consumer) {
        ShapedRecipe recipe = new ShapedRecipe(getNewRecipeKey(), this.getItem());
        recipe.shape(
                " AA",
                " NA",
                "A  ");

        recipe.setIngredient('A', new RecipeChoice.MaterialChoice(Material.ARMADILLO_SCUTE));
        recipe.setIngredient('N', new RecipeChoice.MaterialChoice(Material.NETHER_STAR));

        consumer.accept(recipe);
    }

    @Override
    public @Nullable DataSupplier<ItemRarity> getRarity() {return DataSupplier.of(ItemRarity.RARE);}

    @Override
    public void initializeComponents() {
        super.initializeComponents();
        getComponents().set(AutoGeneratingFilesItem.HANDHELD);
        getComponents().getOrCreateDefault(RPGUComponents.ACTIVE_ABILITY_ITEM).getComponents().set(Ability.DEFAULT);
    }

    public static class Ability extends RPGUConstructableActiveAbility<PlayerInteractEvent> implements RPGUActiveTriggerableAbility<PlayerInteractEvent> {

        public static final Ability DEFAULT = new Ability();
        private static final int COOLDOWN = 25*20;
        private static final int DURATION = 10*20;

        public Ability(){
            getComponents().set(new AttributeBasedProperty(COOLDOWN, RPGUComponents.ABILITY_COOLDOWN_TIME));
            getComponents().set(new AttributeBasedProperty(DURATION, RPGUComponents.ABILITY_DURATION));
            getComponents().set(new EffectsProperty(new EffectsProperty.PotionData(
                    Effects.MAGIC_RESISTANCE.getBukkitType(),
                    PropertyFunctions.CONSTANT(0),
                    PropertyFunctions.CONSTANT(6)
            )));
        }

        @Override
        protected @NotNull ActionResult action(@NotNull CustomItem customItem, @NotNull LivingEntity livingEntity, @NotNull UniversalInventorySlot universalInventorySlot, @NotNull PlayerInteractEvent event) {
            final double duration = getComponents().getOrException(RPGUComponents.ABILITY_DURATION).get(livingEntity);
            final int PERIOD = 10;
            new BukkitRunnable() {
                int count = 0;
                @Override
                public void run() {
                    Collection<PotionEffect> activePotionEffects = livingEntity.getActivePotionEffects();
                    for (PotionEffect activePotionEffect : activePotionEffects) {
                        if (activePotionEffect.getType().getEffectCategory() == PotionEffectType.Category.HARMFUL){
                            livingEntity.removePotionEffect(activePotionEffect.getType());
                        }
                    }

                    count++;
                    if (count == duration/PERIOD) cancel();
                }
            }.runTaskTimer(RpgU.getInstance(), 0, PERIOD);

            List<PotionEffect> potionEffects = getComponents().getOrException(RPGUComponents.ABILITY_EFFECTS).get(livingEntity);
            for (PotionEffect effect : potionEffects) {
                livingEntity.addPotionEffect(effect.withDuration((int) duration));
            }

            return ActionResult.FULL_COOLDOWN;
        }

        @Override
        public @Nullable Pair<List<String>, List<String>> getEngAndRuDescription() {
            return Pair.of(List.of("Dispels negative effects while active"), List.of("Снимает все негативные эффекты во время действия"));
        }


        @Override
        public void onRightClick(@NotNull CustomItem customItem, @NotNull PlayerInteractEvent event) {
            activate(customItem, event.getPlayer(), new BaseUniversalSlot(Objects.requireNonNull(event.getHand())), event);
        }

        @Override
        public @NotNull CustomComponentType<? super RPGUActiveItem, ? extends CustomComponent<? super RPGUActiveItem>> getType() {
            return Abilities.ARMADILLO_BAR;
        }
    }
}