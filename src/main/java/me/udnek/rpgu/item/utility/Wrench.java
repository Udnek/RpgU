package me.udnek.rpgu.item.utility;

import me.udnek.itemscoreu.customcomponent.instance.RightClickableItem;
import me.udnek.itemscoreu.customitem.ConstructableCustomItem;
import me.udnek.itemscoreu.customitem.CustomItem;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.type.Chest;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.function.Consumer;

public class Wrench extends ConstructableCustomItem {
    @Override
    public @NotNull Material getMaterial() {
        return Material.FISHING_ROD;
    }
    @Override
    public @NotNull String getRawId() {
        return "wrench";
    }

    @Override
    public void initializeComponents() {
        super.initializeComponents();
        getComponents().set(new RightClickableItem() {
            @Override
            public void onRightClick(@NotNull CustomItem customItem, @NotNull PlayerInteractEvent event) {
                event.setCancelled(true);
                if (event.getClickedBlock() == null) return;

                Block block = event.getClickedBlock();

                if (!(block.getBlockData() instanceof Directional blockData)) return;

                if (!isBlockAcceptable(block)) return;

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
        });
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
    protected void generateRecipes(@NotNull Consumer<@NotNull Recipe> consumer) {
        ShapedRecipe recipe = new ShapedRecipe(getNewRecipeKey(), getItem());
        recipe.shape(
                " A ",
                " AA",
                "A  ");

        recipe.setIngredient('A', Material.COPPER_INGOT);

        consumer.accept(recipe);
    }

    @Override
    public void getLore(@NotNull Consumer<Component> consumer) {
        consumer.accept(Component.translatable(getRawItemName() + ".description.0").decoration(TextDecoration.ITALIC, false).color(NamedTextColor.GRAY));
    }
}

























