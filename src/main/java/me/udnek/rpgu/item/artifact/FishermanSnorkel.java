package me.udnek.rpgu.item.artifact;

import com.destroystokyo.paper.ParticleBuilder;
import me.udnek.itemscoreu.customequipmentslot.CustomEquipmentSlot;
import me.udnek.itemscoreu.customequipmentslot.SingleSlot;
import me.udnek.itemscoreu.customitem.ConstructableCustomItem;
import me.udnek.itemscoreu.customitem.CustomItem;
import me.udnek.itemscoreu.util.LoreBuilder;
import me.udnek.rpgu.component.ConstructableEquippableItemComponent;
import me.udnek.rpgu.equipment.slot.EquipmentSlots;
import me.udnek.rpgu.lore.AttributesLorePart;
import org.bukkit.Material;
import org.bukkit.MusicInstrument;
import org.bukkit.Particle;
import org.bukkit.entity.FishHook;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemRarity;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class FishermanSnorkel extends ConstructableCustomItem {
    @Override
    public @NotNull Material getMaterial() {return Material.GOAT_HORN;}
    @Override
    public @NotNull String getRawId() {return "fisherman_snorkel";}
    @Override
    public @Nullable DataSupplier<MusicInstrument> getMusicInstrument() {return DataSupplier.of(MusicInstrument.DREAM_GOAT_HORN);}
    @Override
    public @Nullable DataSupplier<ItemRarity> getRarity() {return DataSupplier.of(ItemRarity.COMMON);}

    @Override
    public @Nullable LoreBuilder getLoreBuilder() {
        LoreBuilder loreBuilder = new LoreBuilder();
        AttributesLorePart attributesLorePart = new AttributesLorePart();
        loreBuilder.set(LoreBuilder.Position.ATTRIBUTES, attributesLorePart);
        attributesLorePart.addFullDescription(EquipmentSlots.ARTIFACTS, this, 2);
        return loreBuilder;
    }

    @Override
    public void initializeComponents() {
        super.initializeComponents();
        getComponents().set(new ConstructableEquippableItemComponent() {
            static final ParticleBuilder particleBuilder = new ParticleBuilder(Particle.BUBBLE_COLUMN_UP);
            static {
                particleBuilder.offset(0.2, 0.2, 0.2);
                particleBuilder.extra(0);
                particleBuilder.count(1);
            }

            @Override
            public boolean isAppropriateSlot(@NotNull CustomEquipmentSlot slot) {
                return EquipmentSlots.ARTIFACTS.test(slot);
            }

            @Override
            public void tickBeingEquipped(@NotNull CustomItem item, @NotNull Player player, @NotNull SingleSlot slot) {
                if (player.getMaximumAir() == player.getRemainingAir()) return;

                FishHook fishHook = player.getFishHook();
                if (fishHook == null) return;

                Material material = fishHook.getLocation().add(0, 0.3, 0).getBlock().getBlockData().getMaterial();
                if (!material.isAir() && material != Material.BUBBLE_COLUMN) return;

                player.setRemainingAir(Math.min(player.getRemainingAir() + 20*2, player.getMaximumAir()));

                particleBuilder.location(player.getEyeLocation());
                particleBuilder.spawn();
            }
        });
    }

    @Override
    protected void generateRecipes(@NotNull Consumer<@NotNull Recipe> consumer) {
        ShapedRecipe recipe = new ShapedRecipe(getNewRecipeKey(), getItem());
        recipe.shape(
                "AB",
                "BA");

        recipe.setIngredient('A', Material.SEAGRASS);
        recipe.setIngredient('B', Material.WHEAT);

        consumer.accept(recipe);
    }

}























