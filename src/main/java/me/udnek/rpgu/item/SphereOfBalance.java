package me.udnek.rpgu.item;

import me.udnek.itemscoreu.customitem.CustomModelDataItem;
import me.udnek.itemscoreu.utils.TranslateUtils;
import me.udnek.rpgu.damaging.Damage;
import me.udnek.rpgu.damaging.DamageEvent;
import me.udnek.rpgu.item.abstracts.ArtifactItem;
import me.udnek.rpgu.lore.LoreConstructor;
import me.udnek.rpgu.lore.TranslationKeys;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;
import java.util.List;

public class SphereOfBalance extends CustomModelDataItem implements ArtifactItem {

    public static final double rebalanceDamage = 5;

    @Override
    public int getCustomModelData() {
        return 3105;
    }

    @Override
    public Material getMaterial() {
        return Material.GUNPOWDER;
    }

    @Override
    protected String getRawDisplayName() {
        return TranslationKeys.itemPrefix + getItemName();
    }

    @Override
    protected String getItemName() {
        return "sphere_of_balance";
    }


    @Override
    protected void modifyFinalItemMeta(ItemMeta itemMeta) {
        super.modifyFinalItemMeta(itemMeta);
        LoreConstructor loreConstructor = new LoreConstructor();
        loreConstructor.setArtifactInformation(TranslateUtils.getTranslated(
                "item.rpgu.sphere_of_balance.description.0",
                "item.rpgu.sphere_of_balance.description.1",
                "item.rpgu.sphere_of_balance.description.2"));
        loreConstructor.apply(itemMeta);
    }

    @Override
    protected List<Recipe> generateRecipes() {
        ShapedRecipe recipe = new ShapedRecipe(this.getRecipeNamespace(), this.getItem());
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
