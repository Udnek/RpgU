package me.udnek.rpgu.item;

import me.udnek.itemscoreu.customattribute.equipmentslot.CustomEquipmentSlot;
import me.udnek.itemscoreu.customattribute.equipmentslot.CustomEquipmentSlots;
import me.udnek.itemscoreu.customitem.CustomItem;
import me.udnek.rpgu.attribute.RpgUAttributeUtils;
import me.udnek.rpgu.attribute.equipmentslot.EquipmentSlots;
import me.udnek.rpgu.damaging.DamageEvent;
import me.udnek.rpgu.equipment.PlayerEquipment;
import me.udnek.rpgu.equipment.PlayerEquipmentDatabase;
import me.udnek.rpgu.item.abstraction.ArtifactItem;
import me.udnek.rpgu.item.abstraction.ExtraDescribed;
import me.udnek.rpgu.item.abstraction.MainHandItem;
import me.udnek.rpgu.lore.LoreUtils;
import me.udnek.rpgu.particle.BackstabParticle;
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
import oshi.util.tuples.Pair;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CeremoniousDagger extends CustomItem implements ArtifactItem, MainHandItem, ExtraDescribed {
    @Override
    public Integer getCustomModelData() {
        return 3100;
    }

    @Override
    public Material getMaterial() {
        return Material.DIAMOND_SWORD;
    }

    @Override
    public String getRawId() {
        return "ceremonious_dagger";
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
        RpgUAttributeUtils.addSuitableAttribute(itemStack, Attribute.GENERIC_ATTACK_DAMAGE, "189dasijk d", -2);
        LoreUtils.generateFullLoreAndApply(itemStack);
    }

    @Override
    public Map<CustomEquipmentSlot, Pair<Integer, Integer>> getExtraDescription() {
        HashMap<CustomEquipmentSlot, Pair<Integer, Integer>> map = new HashMap<>();
        map.put(CustomEquipmentSlots.MAIN_HAND, new Pair<>(0, 0));
        map.put(EquipmentSlots.ARTIFACT, new Pair<>(1, 1));
        return map;
    }

    @Override
    protected List<Recipe> generateRecipes() {
        ShapedRecipe recipe = new ShapedRecipe(this.getRecipeNamespace(0), this.getItem());
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
    public boolean isAppropriateSlot(CustomEquipmentSlot slot) {
        return CustomEquipmentSlots.MAIN_HAND == slot || EquipmentSlots.ARTIFACT == slot;
    }

    @Override
    public boolean isEquippedInAppropriateSlot(Player player) {
        PlayerEquipment playerEquipment = PlayerEquipmentDatabase.get(player);
        return playerEquipment.isEquippedAsArtifact(this) || playerEquipment.getMainHand() == this;
    }

    @Override
    public void onPlayerAttacksWhenEquipped(Player player, CustomEquipmentSlot slot, DamageEvent event) {
        if (slot == EquipmentSlots.ARTIFACT) runIfBackstab(event, 1.5);
        else runIfBackstab(event, 2.5);

    }



    private static void runIfBackstab(DamageEvent event, double damageMultiplayer){
        Entity victim = event.getVictim();
        Entity damager = event.getDamager();

        if (damager instanceof Player player){
            if (player.getAttackCooldown() < 0.848) return;
        }
        if (!isBackstab(damager, victim)) return;
        event.getDamage().multiplyPhysicalDamage(damageMultiplayer);

        if (victim instanceof LivingEntity livingEntity){
            livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20*5, 0));
            backstabParticles(livingEntity.getEyeLocation().add((victim.getLocation())).multiply(0.5));
        }


    }

    private static boolean isBackstab(Entity damager, Entity victim){
        Vector damagerDir = damager.getLocation().getDirection();
        Vector victimDir = victim.getLocation().getDirection();
        return damagerDir.angle(victimDir) <= Math.toRadians(45);
    }

    private static void backstabParticles(Location location){
        BackstabParticle particle = new BackstabParticle(location);
        particle.play();
    }
}




























