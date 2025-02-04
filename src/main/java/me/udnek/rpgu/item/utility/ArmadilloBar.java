package me.udnek.rpgu.item.utility;

import me.udnek.itemscoreu.customitem.ConstructableCustomItem;
import me.udnek.itemscoreu.customitem.CustomItem;
import me.udnek.rpgu.RpgU;
import me.udnek.rpgu.component.ComponentTypes;
import me.udnek.rpgu.component.ability.active.ConstructableActiveAbilityComponent;
import me.udnek.rpgu.component.ability.property.AttributeBasedProperty;
import me.udnek.rpgu.component.ability.property.EffectsProperty;
import me.udnek.rpgu.component.ability.property.function.Functions;
import me.udnek.rpgu.effect.Effects;
import me.udnek.rpgu.lore.ability.ActiveAbilityLorePart;
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
import java.util.function.Consumer;

public class ArmadilloBar extends ConstructableCustomItem {

    @Override
    public @NotNull String getRawId() {
        return "armadillo_bar";
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

        getComponents().set(new ArmadilloBarComponent());
    }

    public class ArmadilloBarComponent extends ConstructableActiveAbilityComponent<PlayerInteractEvent> {

        private static final int COOLDOWN = 25*20;
        private static final int DURATION = 10*20;

        public ArmadilloBarComponent(){
            getComponents().set(new AttributeBasedProperty(COOLDOWN, ComponentTypes.ABILITY_COOLDOWN));
            getComponents().set(new AttributeBasedProperty(DURATION, ComponentTypes.ABILITY_DURATION));
            getComponents().set(new EffectsProperty(new EffectsProperty.PotionData(
                    Effects.MAGICAL_RESISTANCE.getBukkitType(),
                    Functions.CONSTANT(0),
                    Functions.CONSTANT(6)
            )));
        }

        @Override
        public @NotNull ActionResult action(@NotNull CustomItem customItem, @NotNull LivingEntity livingEntity, @NotNull PlayerInteractEvent event) {
            final double duration = getComponents().getOrException(ComponentTypes.ABILITY_DURATION).get(livingEntity);
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

            List<PotionEffect> potionEffects = getComponents().getOrException(ComponentTypes.ABILITY_EFFECTS).get(livingEntity);
            for (PotionEffect effect : potionEffects) {
                livingEntity.addPotionEffect(effect.withDuration((int) duration));
            }

            return ActionResult.FULL_COOLDOWN;
        }

        @Override
        public void addLoreLines(@NotNull ActiveAbilityLorePart componentable) {
            componentable.addFullAbilityDescription(ArmadilloBar.this, 1);
            super.addLoreLines(componentable);
        }

        @Override
        public void onRightClick(@NotNull CustomItem customItem, @NotNull PlayerInteractEvent event) {
            activate(customItem, event.getPlayer(), event);
        }
    }
}