package me.udnek.rpgu.item;

import me.udnek.itemscoreu.customattribute.equipmentslot.CustomEquipmentSlot;
import me.udnek.rpgu.attribute.equipmentslot.EquipmentSlots;
import me.udnek.rpgu.damaging.Damage;
import me.udnek.rpgu.damaging.DamageEvent;
import me.udnek.rpgu.item.abstraction.ArtifactItem;
import me.udnek.rpgu.item.abstraction.ExtraDescriptionItem;
import me.udnek.rpgu.item.abstraction.RpgUCustomItem;
import me.udnek.rpgu.lore.LoreUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import oshi.util.tuples.Pair;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SphereOfBalance extends ArtifactItem implements ExtraDescriptionItem {

    public static final double rebalanceDamage = 5;

    @Override
    public Integer getCustomModelData() {
        return 3105;
    }
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
        return ExtraDescriptionItem.getSimple(EquipmentSlots.ARTIFACT, 1);
    }

    @Override
    protected List<Recipe> generateRecipes() {
        ShapedRecipe recipe = new ShapedRecipe(this.getRecipeNamespace(0), this.getItem());
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
    public void onPlayerAttacksWhenEquipped(Player player, DamageEvent event) {
        Damage damage = event.getDamage();
        double physicalDamage = damage.getPhysicalDamage();
        double magicalDamage = damage.getMagicalDamage();
        double damageToRebalance = Math.min(physicalDamage, rebalanceDamage);

        damage.addPhysicalDamage(-damageToRebalance);
        damage.addMagicalDamage(damageToRebalance);
    }
}
