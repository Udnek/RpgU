package me.udnek.rpgu.item;

import me.udnek.itemscoreu.customitem.CustomModelDataItem;
import me.udnek.itemscoreu.utils.TranslateUtils;
import me.udnek.rpgu.damaging.DamageEvent;
import me.udnek.rpgu.item.abstracts.ArtifactItem;
import me.udnek.rpgu.lore.LoreConstructor;
import me.udnek.rpgu.lore.TranslationKeys;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Collections;
import java.util.List;

public class WitherWreath extends CustomModelDataItem implements ArtifactItem {
    @Override
    public int getCustomModelData() {
        return 3103;
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
        return "wither_wreath";
    }

    @Override
    protected void modifyFinalItemMeta(ItemMeta itemMeta) {
        super.modifyFinalItemMeta(itemMeta);
        LoreConstructor loreConstructor = new LoreConstructor();
        loreConstructor.setArtifactInformation(TranslateUtils.getTranslated("item.rpgu.wither_wreath.description.0", "item.rpgu.wither_wreath.description.1"));
        loreConstructor.apply(itemMeta);
    }

    @Override
    protected List<Recipe> generateRecipes() {
        ShapedRecipe recipe = new ShapedRecipe(this.getRecipeNamespace(), this.getItem());
        recipe.shape("AAA","A A","AAA");

        recipe.setIngredient('A', Material.WITHER_ROSE);

        return Collections.singletonList(recipe);
    }


    @Override
    public void onPlayerAttacksWhenEquipped(Player player, DamageEvent event) {
        Entity entity = event.getEvent().getEntity();
        event.getDamage().multiplyMagicalDamage(1.2);
        if (entity instanceof LivingEntity){
            ((LivingEntity) entity).addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 20*4, 1, false, true));
        }
    }

}
