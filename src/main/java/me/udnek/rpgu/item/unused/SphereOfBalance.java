/*
package me.udnek.rpgu.item.artifact;

import me.udnek.itemscoreu.customattribute.equipmentslot.CustomEquipmentSlot;
import me.udnek.itemscoreu.customitem.ConstructableCustomItem;
import me.udnek.rpgu.equipment.slot.EquipmentSlots;
import me.udnek.rpgu.item.abstraction.ArtifactItem;
import me.udnek.rpgu.lore.LoreUtils;
import me.udnek.rpgu.mechanic.damaging.Damage;
import me.udnek.rpgu.mechanic.damaging.DamageEvent;
import me.udnek.rpgu.util.ExtraDescribed;
import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class SphereOfBalance extends ConstructableCustomItem implements ExtraDescribed, ArtifactItem {

    public static final double rebalanceDamage = 5;


    @Override
    public Material getMaterial() {
        return Material.GUNPOWDER;
    }
    @Override
    public String getRawId() {
        return "sphere_of_balance";
    }


    @Override
    protected void modifyFinalItemStack(ItemStack itemStack) {
        super.modifyFinalItemStack(itemStack);
        LoreUtils.generateFullLoreAndApply(itemStack);
    }

    @Override
    public Map<CustomEquipmentSlot, Pair<Integer, Integer>> getExtraDescription() {
        return ExtraDescribed.getSimple(EquipmentSlots.ARTIFACT, 1);
    }

    @Override
    protected List<Recipe> generateRecipes() {
        ShapedRecipe recipe = new ShapedRecipe(getNewRecipeKey(), this.getItem());
        recipe.shape(
                "ABB",
                "ACB",
                "AAB");

        recipe.setIngredient('A', Material.SNOWBALL);
        recipe.setIngredient('B', Material.FIRE_CHARGE);
        recipe.setIngredient('C', Material.STONE);

        return Collections.singletonList(recipe);
    }

    @Override
    public void onPlayerAttacksWhenEquipped(Player player, CustomEquipmentSlot slot, DamageEvent event) {
        Damage damage = event.getDamage();
        double physicalDamage = damage.getPhysicalDamage();
        double magicalDamage = damage.getMagicalDamage();
        double damageToRebalance = Math.min(physicalDamage, rebalanceDamage);

        damage.addPhysicalDamage(-damageToRebalance);
        damage.addMagicalDamage(damageToRebalance);
    }
}
*/
