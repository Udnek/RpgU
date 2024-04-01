package me.udnek.rpgu.item;

import me.udnek.itemscoreu.customitem.CustomModelDataItem;
import me.udnek.itemscoreu.utils.TranslateUtils;
import me.udnek.rpgu.damaging.AttributeUtils;
import me.udnek.rpgu.damaging.DamageEvent;
import me.udnek.rpgu.item.abstracts.ArtifactItem;
import me.udnek.rpgu.item.abstracts.WeaponItem;
import me.udnek.rpgu.lore.LoreConstructor;
import me.udnek.rpgu.lore.TranslationKeys;
import me.udnek.rpgu.particle.BackstabParticle;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.Collections;
import java.util.List;

public class CeremoniousDagger extends CustomModelDataItem implements WeaponItem, ArtifactItem {
    @Override
    public int getCustomModelData() {
        return 3100;
    }

    @Override
    public Material getMaterial() {
        return Material.DIAMOND_SWORD;
    }

    @Override
    protected String getRawDisplayName() {
        return TranslationKeys.itemPrefix + getItemName();
    }

    @Override
    protected String getItemName() {
        return "ceremonious_dagger";
    }


    @Override
    protected void modifyFinalItemMeta(ItemMeta itemMeta) {
        super.modifyFinalItemMeta(itemMeta);
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
    }

    @Override
    protected void modifyFinalItemStack(ItemStack itemStack) {
        super.modifyFinalItemStack(itemStack);
        AttributeUtils.addDefaultAttributes(itemStack);
        AttributeUtils.addSuitableAttribute(itemStack, Attribute.GENERIC_ATTACK_DAMAGE, -2);
        LoreConstructor loreConstructor = new LoreConstructor();
        loreConstructor.addMeeleLore(itemStack);
        loreConstructor.addExtraMeeleInformation(TranslateUtils.getTranslated("item.rpgu.ceremonious_dagger.description.0"));
        loreConstructor.setArtifactInformation(TranslateUtils.getTranslated("item.rpgu.ceremonious_dagger.description.1"));
        loreConstructor.apply(itemStack);
    }

    @Override
    protected List<Recipe> generateRecipes() {
        ShapedRecipe recipe = new ShapedRecipe(this.getRecipeNamespace(), this.getItem());
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

        return Collections.singletonList(recipe);
    }

    @Override
    public void onEntityAttacks(LivingEntity entity, DamageEvent event) {
        runIfBackstab(entity, event, 2.5);
    }

    @Override
    public void onPlayerAttacksWhenEquipped(Player player, DamageEvent event) {
        runIfBackstab(player, event, 1.5);
    }

    private static void runIfBackstab(LivingEntity damager, DamageEvent event, double damageMultiplayer){
        EntityDamageByEntityEvent entityDamageByEntityEvent = event.getEvent();
        Entity victim = entityDamageByEntityEvent.getEntity();

        if (damager instanceof Player){
            if (((Player) damager).getAttackCooldown() != 1) return;
        }
        if (!isBackstab(damager, victim)) return;
        event.getDamage().multiplyPhysicalDamage(damageMultiplayer);

        if (victim instanceof LivingEntity){
            ((LivingEntity) victim).addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20*5, 0));
            backstabParticles(((LivingEntity) victim).getEyeLocation().add((victim.getLocation())).multiply(0.5));
        }


    }

    private static boolean isBackstab(LivingEntity damager, Entity victim){
        Vector damagerDir = damager.getLocation().getDirection();
        Vector victimDir = victim.getLocation().getDirection();
        return damagerDir.angle(victimDir) <= Math.toRadians(45);
    }

    private static void backstabParticles(Location location){
        BackstabParticle particle = new BackstabParticle(location);
        particle.play();
    }
}




























