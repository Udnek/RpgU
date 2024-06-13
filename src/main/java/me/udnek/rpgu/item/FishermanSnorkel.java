package me.udnek.rpgu.item;

import com.destroystokyo.paper.ParticleBuilder;
import me.udnek.itemscoreu.customattribute.equipmentslot.CustomEquipmentSlot;
import me.udnek.rpgu.attribute.equipmentslot.EquipmentSlots;
import me.udnek.rpgu.item.abstraction.ArtifactItem;
import me.udnek.rpgu.item.abstraction.ExtraDescriptionItem;
import me.udnek.rpgu.lore.LoreUtils;
import org.bukkit.Material;
import org.bukkit.MusicInstrument;
import org.bukkit.Particle;
import org.bukkit.entity.FishHook;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import oshi.util.tuples.Pair;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class FishermanSnorkel extends ArtifactItem implements ExtraDescriptionItem {
    @Override
    public Integer getCustomModelData() {
        return 3106;
    }

    @Override
    public Material getMaterial() {
        return Material.GOAT_HORN;
    }

    @Override
    public String getRawId() {
        return "fisherman_snorkel";
    }

    @Override
    public MusicInstrument getMusicInstrument() {
        return MusicInstrument.DREAM_GOAT_HORN;
    }

    @Override
    protected void modifyFinalItemStack(ItemStack itemStack) {
        super.modifyFinalItemStack(itemStack);
        LoreUtils.generateFullLoreAndApply(itemStack);
    }

    @Override
    public Map<CustomEquipmentSlot, Pair<Integer, Integer>> getExtraDescription() {
        return ExtraDescriptionItem.getSimple(EquipmentSlots.ARTIFACT, 2);
    }

    @Override
    public void tickBeingEquipped(Player player) {

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
        ShapedRecipe recipe = new ShapedRecipe(getRecipeNamespace(0), getItem());
        recipe.shape(
                "AB",
                "BA");

        recipe.setIngredient('A', Material.SEAGRASS);
        recipe.setIngredient('B', Material.WHEAT);

        return Collections.singletonList(recipe);
    }
}























