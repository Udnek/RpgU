package me.udnek.rpgu.item.utility;

import me.udnek.itemscoreu.customitem.ConstructableCustomItem;
import me.udnek.itemscoreu.customitem.CustomItem;
import me.udnek.rpgu.RpgU;
import me.udnek.rpgu.component.ConstructableActiveAbilityComponent;
import me.udnek.rpgu.effect.Effects;
import me.udnek.rpgu.lore.ActiveAbilityLorePart;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.function.Consumer;

public class ArmadilloBar extends ConstructableCustomItem {

    @Override
    public @NotNull String getRawId() {
        return "armadillo_bar";
    }

    @Override
    public @NotNull Material getMaterial() {
        return Material.GUNPOWDER;
    }

    @Override
    protected void generateRecipes(@NotNull Consumer<@NotNull Recipe> consumer) {
        ShapedRecipe recipe = new ShapedRecipe(getNewRecipeKey(), this.getItem());
        recipe.shape(
                " A ",
                "ANA",
                " A ");

        recipe.setIngredient('A', new RecipeChoice.MaterialChoice(Material.ARMADILLO_SCUTE));
        recipe.setIngredient('N', new RecipeChoice.MaterialChoice(Material.NETHER_STAR));

        consumer.accept(recipe);
    }


    @Override
    public void initializeComponents() {
        super.initializeComponents();

        setComponent(new ArmadilloBarComponent());
    }

    public class ArmadilloBarComponent implements ConstructableActiveAbilityComponent<PlayerInteractEvent> {
        @Override
        public int getBaseCooldown() {return 200;}

        @Override
        public int getBaseCastRange() {return 0;}

        @Override
        public int getBaseCastTime() {return 0;}

        @Override
        public @NotNull ActionResult action(@NotNull CustomItem customItem, @NotNull Player player, @NotNull PlayerInteractEvent event) {
            new BukkitRunnable() {
                int count = 0;
                @Override
                public void run() {
                    Collection<PotionEffect> activePotionEffects = player.getActivePotionEffects();
                    for (PotionEffect activePotionEffect : activePotionEffects) {
                        if (activePotionEffect.getType().getEffectCategory() == PotionEffectType.Category.HARMFUL){
                            player.removePotionEffect(activePotionEffect.getType());
                        }
                    }
                    count++;
                    if (count == 8) cancel();
                }
            }.runTaskTimer(RpgU.getInstance(), 0, 10);

            Effects.MAGICAL_RESISTANCE.apply(player, 160, 8, false, true, true);

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