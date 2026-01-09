package me.udnek.rpgu.item.utility;

import io.papermc.paper.datacomponent.item.ItemAttributeModifiers;
import io.papermc.paper.datacomponent.item.Tool;
import me.udnek.coreu.custom.component.instance.AutoGeneratingFilesItem;
import me.udnek.coreu.custom.component.instance.RightClickableItem;
import me.udnek.coreu.custom.component.instance.TranslatableThing;
import me.udnek.coreu.custom.item.ConstructableCustomItem;
import me.udnek.coreu.custom.item.CustomItem;
import me.udnek.coreu.custom.item.RepairData;
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
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.function.Consumer;

public class Wrench extends ConstructableCustomItem {

    @Override
    public @NotNull Material getMaterial() {
        return Material.STONE_PICKAXE;
    }

    @Override
    public @Nullable DataSupplier<Tool> getTool() {
        return DataSupplier.of(null);
    }

    @Override
    public @Nullable RepairData initializeRepairData() {
        return new RepairData(Material.COPPER_INGOT);
    }

    @Override
    public @Nullable DataSupplier<ItemAttributeModifiers> getAttributeModifiers() {
        return DataSupplier.of(null);
    }

    @Override
    public @NotNull String getRawId() {
        return "wrench";
    }

    @Override
    public @Nullable TranslatableThing getTranslations() {
        return TranslatableThing.ofEngAndRu("Wrench", "Гаечный ключ");
    }

    @Override
    public void initializeComponents() {
        super.initializeComponents();
        getComponents().set(AutoGeneratingFilesItem.HANDHELD);
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

            public boolean isBlockAcceptable(Block block){
                Material material = block.getType();
                if (Tag.BEDS.isTagged(material)) return false;
                return switch (material) {
                    case CHEST, TRAPPED_CHEST -> {
                        Chest blockData = (Chest) block.getBlockData();
                        yield blockData.getType() == Chest.Type.SINGLE;
                    }
                    case SMALL_AMETHYST_BUD, MEDIUM_AMETHYST_BUD, LARGE_AMETHYST_BUD, AMETHYST_CLUSTER -> false;
                    default -> true;
                };
            }
        });
    }

    @Override
    public @Nullable DataSupplier<Integer> getMaxDamage() {
        return DataSupplier.of(128);
    }

    @Override
    protected void generateRecipes(@NotNull Consumer<Recipe> consumer) {
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
        consumer.accept(Component.translatable(translationKey() + ".description.0").decoration(TextDecoration.ITALIC, false).color(NamedTextColor.GRAY));
    }
}

























