package me.udnek.rpgu.item;

import me.udnek.itemscoreu.customitem.CustomModelDataItem;
import me.udnek.itemscoreu.utils.TranslateUtils;
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

public class NautilusCore extends CustomModelDataItem implements ArtifactItem {
    @Override
    public int getCustomModelData() {
        return 3104;
    }

    @Override
    public Material getMaterial() {
        return Material.GUNPOWDER;
    }


    @Override
    protected void modifyFinalItemMeta(ItemMeta itemMeta) {
        super.modifyFinalItemMeta(itemMeta);
        LoreConstructor loreConstructor = new LoreConstructor();
        loreConstructor.setArtifactInformation(TranslateUtils.getTranslated(
                "item.rpgu.nautilus_core.description.0",
                "item.rpgu.nautilus_core.description.1",
                "item.rpgu.nautilus_core.description.2"));
        loreConstructor.apply(itemMeta);
    }

    @Override
    protected String getRawDisplayName() {
        return TranslationKeys.itemPrefix + getItemName();
    }

    @Override
    protected String getItemName() {
        return "nautilus_core";
    }


    @Override
    protected List<Recipe> generateRecipes() {
        ShapedRecipe recipe = new ShapedRecipe(this.getRecipeNamespace(), this.getItem());
        recipe.shape("AAA","ABA","AAA");

        recipe.setIngredient('A', Material.PRISMARINE_SHARD);
        recipe.setIngredient('B', Material.NAUTILUS_SHELL);

        return Collections.singletonList(recipe);
    }

    @Override
    public void onPlayerAttacksWhenEquipped(Player player, DamageEvent event) {
        event.getDamage().multiplyMagicalDamage(1.1);
        if (event.getEvent().isCritical() && !event.containsExtraFlag(new isMagicalCriticalApplied())){
            event.getDamage().multiplyMagicalDamage(1.5);
            event.addExtraFlag(new isMagicalCriticalApplied());
        }
    }


    private static class isMagicalCriticalApplied extends DamageEvent.ExtraFlag{}
}


















