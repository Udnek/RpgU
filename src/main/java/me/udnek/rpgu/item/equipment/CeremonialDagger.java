package me.udnek.rpgu.item.equipment;

import me.udnek.itemscoreu.customequipmentslot.CustomEquipmentSlot;
import me.udnek.itemscoreu.customitem.ConstructableCustomItem;
import me.udnek.itemscoreu.customitem.CustomItem;
import me.udnek.itemscoreu.util.LoreBuilder;
import me.udnek.rpgu.attribute.RpgUAttributeUtils;
import me.udnek.rpgu.component.ArtifactComponent;
import me.udnek.rpgu.equipment.slot.EquipmentSlots;
import me.udnek.rpgu.item.RpgUCustomItem;
import me.udnek.rpgu.lore.AttributesLorePart;
import me.udnek.rpgu.mechanic.damaging.DamageEvent;
import me.udnek.rpgu.particle.BackstabParticle;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.*;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Random;
import java.util.function.Consumer;

public class CeremonialDagger extends ConstructableCustomItem implements RpgUCustomItem {

    public static final float BACKSTAB_MAIN_HAND_MULTIPLIER = 2.5f;
    public static final float BACKSTAB_ARTIFACT_MULTIPLIER = 1.5f;

    @Override
    public @NotNull Material getMaterial() {
        return Material.DIAMOND_SWORD;
    }
    @Override
    public @NotNull String getRawId() {
        return "ceremonial_dagger";
    }
    @Override
    public ItemFlag[] getTooltipHides() {
        return new ItemFlag[]{ItemFlag.HIDE_ATTRIBUTES};
    }
    @Override
    public boolean getAddDefaultAttributes() {return true;}
    @Override
    protected void modifyFinalItemStack(ItemStack itemStack) {
        super.modifyFinalItemStack(itemStack);
        RpgUAttributeUtils.addSuitableAttribute(itemStack, Attribute.GENERIC_ATTACK_DAMAGE, null, -2);
    }

    @Override
    public @Nullable LoreBuilder getLoreBuilder() {
        LoreBuilder loreBuilder = new LoreBuilder();
        AttributesLorePart attributesLorePart = new AttributesLorePart();
        loreBuilder.set(LoreBuilder.Position.ATTRIBUTES, attributesLorePart);
        attributesLorePart.addAttribute(CustomEquipmentSlot.MAIN_HAND, Component.text("BACKSTAB x2.5"));
        attributesLorePart.addAttribute(EquipmentSlots.ARTIFACTS, Component.text("BACKSTAB x1.5"));


        loreBuilder.add(LoreBuilder.Position.BACKSTORY,
                Component
                        .text("Started to be used for even more dirty needs")
                        .appendNewline()
                        .append(Component.text("ASDASDDA"))
                        .decoration(TextDecoration.ITALIC, true)
                        .color(NamedTextColor.DARK_PURPLE));

        return loreBuilder;
    }
    @Override
    public void initializeComponents() {
        super.initializeComponents();
        setComponent(new CeremoniousDaggerComponent());
    }

    @Override
    protected void generateRecipes(@NotNull Consumer<@NotNull Recipe> consumer) {
        ShapedRecipe recipe = new ShapedRecipe(getNewRecipeKey(), this.getItem());
        recipe.shape(
                "DSG",
                "GSD",
                " T ");

        RecipeChoice.MaterialChoice diamond = new RecipeChoice.MaterialChoice(Material.DIAMOND);
        RecipeChoice.MaterialChoice gold = new RecipeChoice.MaterialChoice(Material.GOLD_INGOT);
        RecipeChoice.MaterialChoice stone = new RecipeChoice.MaterialChoice(Material.COBBLESTONE);
        RecipeChoice.MaterialChoice stick = new RecipeChoice.MaterialChoice(Material.STICK);
        recipe.setIngredient('D', diamond);
        recipe.setIngredient('G', gold);
        recipe.setIngredient('S', stone);
        recipe.setIngredient('T', stick);

        consumer.accept(recipe);
    }


    public static class CeremoniousDaggerComponent implements ArtifactComponent {

        @Override
        public boolean isAppropriateSlot(@NotNull CustomEquipmentSlot slot){
            return ArtifactComponent.super.isAppropriateSlot(slot) || CustomEquipmentSlot.MAIN_HAND.test(slot);
        }

        @Override
        public void onPlayerAttacksWhenEquipped(@NotNull CustomItem item, @NotNull Player player, @NotNull CustomEquipmentSlot slot, @NotNull DamageEvent event) {
            if (EquipmentSlots.ARTIFACTS.test(slot)) runIfBackstab(event, BACKSTAB_ARTIFACT_MULTIPLIER);
            else runIfBackstab(event, BACKSTAB_MAIN_HAND_MULTIPLIER);
        }

        protected void runIfBackstab(DamageEvent event, double damageMultiplayer){
            Entity victim = event.getVictim();
            Entity damager = event.getDamager();

            if (damager instanceof Player player){
                if (player.getAttackCooldown() < 0.848) return;
            }
            if (!isBackstab(damager, victim)) return;
            event.getDamage().multiplyPhysical(damageMultiplayer);

            if (victim instanceof LivingEntity livingVictim){
                livingVictim.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20*5, 0));
                backstabParticles(livingVictim.getEyeLocation().add((victim.getLocation())).multiply(0.5));
            }
        }


        protected boolean isBackstab(Entity damager, Entity victim){
            Vector damagerDirection = damager.getLocation().getDirection();
            Vector victimDir = victim.getLocation().getDirection();
            return damagerDirection.angle(victimDir) <= Math.toRadians(45);
        }

        protected void backstabParticles(Location location){
            Random random = new Random();
            BackstabParticle particle = new BackstabParticle(location.add(random.nextFloat()-0.5, random.nextFloat()-0.5, random.nextFloat()-0.5));
            particle.play();
        }
    }
}




























