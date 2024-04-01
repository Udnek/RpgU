package me.udnek.rpgu.item;

import com.destroystokyo.paper.ParticleBuilder;
import me.udnek.itemscoreu.customitem.CustomModelDataItem;
import me.udnek.rpgu.item.abstracts.ArtifactItem;
import me.udnek.rpgu.lore.TranslationKeys;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.FishHook;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;

import java.util.Collections;
import java.util.List;

public class FishermanSnorkel extends CustomModelDataItem implements ArtifactItem {
    @Override
    public int getCustomModelData() {
        return 3106;
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
        return "fisherman_snorkel";
    }

    @Override
    public void onWhileBeingEquipped(Player player) {

        if (player.getMaximumAir() == player.getRemainingAir()) return;

        FishHook fishHook = player.getFishHook();
        if (fishHook == null) return;

        Material material = fishHook.getLocation().add(0, 0.3, 0).getBlock().getBlockData().getMaterial();
        if (!material.isAir() && material != Material.BUBBLE_COLUMN) return;

        player.setRemainingAir(Math.min(player.getRemainingAir() + 20*2, player.getMaximumAir()));

        ParticleBuilder particleBuilder = new ParticleBuilder(Particle.BUBBLE_COLUMN_UP);
        particleBuilder.location(player.getEyeLocation());
        particleBuilder.offset(0.2, 0.2, 0.2);
        particleBuilder.extra(0);
        particleBuilder.count(3);
        particleBuilder.spawn();

    }

    @Override
    protected List<Recipe> generateRecipes() {
        ShapedRecipe recipe = new ShapedRecipe(getRecipeNamespace(), getItem());
        recipe.shape(
                "AB",
                "BA");

        recipe.setIngredient('A', Material.SEAGRASS);
        recipe.setIngredient('B', Material.WHEAT);

        return Collections.singletonList(recipe);
    }
}























