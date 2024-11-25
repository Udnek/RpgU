package me.udnek.rpgu.item.artifact;

import com.destroystokyo.paper.ParticleBuilder;
import me.udnek.itemscoreu.customattribute.CustomAttribute;
import me.udnek.itemscoreu.customitem.ConstructableCustomItem;
import me.udnek.itemscoreu.customitem.CustomItem;
import me.udnek.itemscoreu.util.LoreBuilder;
import me.udnek.rpgu.component.ArtifactComponent;
import me.udnek.rpgu.equipment.slot.EquipmentSlots;
import me.udnek.rpgu.lore.AttributesLorePart;
import net.kyori.adventure.text.Component;
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
    public MusicInstrument getMusicInstrument() {return MusicInstrument.DREAM_GOAT_HORN;}
    @Override
    public @Nullable ItemRarity getRarity() {return ItemRarity.COMMON;}

    @Override
    public @Nullable LoreBuilder getLoreBuilder() {
        LoreBuilder loreBuilder = new LoreBuilder();
        AttributesLorePart attributesLorePart = new AttributesLorePart();
        loreBuilder.set(LoreBuilder.Position.ATTRIBUTES, attributesLorePart);
        attributesLorePart.addAttribute(EquipmentSlots.ARTIFACTS, Component.translatable(getRawItemName()+".description.0").color(CustomAttribute.PLUS_COLOR));
        attributesLorePart.addAttribute(EquipmentSlots.ARTIFACTS, Component.translatable(getRawItemName()+".description.1").color(CustomAttribute.PLUS_COLOR));
        return loreBuilder;
    }

    @Override
    public void initializeComponents() {
        super.initializeComponents();
        getComponents().set(new ArtifactComponent() {
            static final ParticleBuilder particleBuilder = new ParticleBuilder(Particle.BUBBLE_COLUMN_UP);
            static {
                particleBuilder.offset(0.2, 0.2, 0.2);
                particleBuilder.extra(0);
                particleBuilder.count(3);
            }
            @Override
            public void tickBeingEquipped(@NotNull CustomItem item, @NotNull Player player, me.udnek.itemscoreu.customequipmentslot.@NotNull CustomEquipmentSlot slot) {
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























