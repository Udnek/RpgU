package me.udnek.rpgu.item;

import me.udnek.itemscoreu.customitem.InteractableItem;
import me.udnek.rpgu.item.abstraction.RpgUCustomItem;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.type.Chest;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Wrench extends RpgUCustomItem implements InteractableItem {
    @Override
    public Integer getCustomModelData() {
        return 3200;
    }
    @Override
    public Material getMaterial() {
        return Material.FISHING_ROD;
    }
    @Override
    public String getRawId() {
        return "wrench";
    }


    @Override
    public void onRightClicks(PlayerInteractEvent event) {
        event.setCancelled(true);
        if (event.getClickedBlock() == null) return;

        Block block = event.getClickedBlock();

        if (!(block.getBlockData() instanceof Directional)) return;

        if (!isBlockAcceptable(block)) return;

        Directional blockData = (Directional) block.getBlockData();

        BlockFace[] faces = Arrays.copyOf(blockData.getFaces().toArray(), blockData.getFaces().size(), BlockFace[].class);
        BlockFace currentFace = blockData.getFacing();
        BlockFace finalFace = currentFace;

        for (int i = 0; i < faces.length; i++) {
            if (currentFace == faces[i]){
                if (i < faces.length - 1){
                    finalFace = faces[i+1];
                    break;
                }
                else {
                    finalFace = faces[0];
                }
            }
        }


        blockData.setFacing(finalFace);
        block.setBlockData(blockData, true);
        event.getPlayer().damageItemStack(event.getHand(), 1);

    }


    public boolean isBlockAcceptable(Block block){
        Material material = block.getType();
        if (Tag.BEDS.isTagged(material)) return false;
        switch (material){
            case CHEST:
            case TRAPPED_CHEST:
                Chest blockData = (Chest) block.getBlockData();
                return blockData.getType() == Chest.Type.SINGLE;
            case SMALL_AMETHYST_BUD:
            case MEDIUM_AMETHYST_BUD:
            case LARGE_AMETHYST_BUD:
            case AMETHYST_CLUSTER:
                return false;
        }
        return true;
    }


    @Override
    public List<Recipe> generateRecipes() {
        ShapedRecipe recipe = new ShapedRecipe(getRecipeNamespace(0), getItem());
        recipe.shape(
                " A ",
                " AA",
                "A  ");

        recipe.setIngredient('A', Material.COPPER_INGOT);

        return Collections.singletonList(recipe);
    }
}

























