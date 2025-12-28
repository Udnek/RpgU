package me.udnek.rpgu.item.artifact;

import com.destroystokyo.paper.ParticleBuilder;
import me.udnek.coreu.custom.component.CustomComponent;
import me.udnek.coreu.custom.component.CustomComponentType;
import me.udnek.coreu.custom.equipment.slot.CustomEquipmentSlot;
import me.udnek.coreu.custom.equipment.universal.BaseUniversalSlot;
import me.udnek.coreu.custom.equipment.universal.UniversalInventorySlot;
import me.udnek.coreu.custom.item.ConstructableCustomItem;
import me.udnek.coreu.custom.item.CustomItem;
import me.udnek.coreu.rpgu.component.RPGUComponents;
import me.udnek.coreu.rpgu.component.RPGUPassiveItem;
import me.udnek.coreu.rpgu.component.ability.passive.RPGUConstructablePassiveAbility;
import me.udnek.coreu.rpgu.component.ability.property.AttributeBasedProperty;
import me.udnek.rpgu.component.ability.Abilities;
import me.udnek.rpgu.equipment.slot.EquipmentSlots;
import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.Material;
import org.bukkit.MusicInstrument;
import org.bukkit.Particle;
import org.bukkit.entity.FishHook;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemRarity;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
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
    public void initializeComponents() {
        super.initializeComponents();
        getComponents().getOrCreateDefault(RPGUComponents.PASSIVE_ABILITY_ITEM).getComponents().set(Passive.DEFAULT);
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


    public static class Passive extends RPGUConstructablePassiveAbility<Integer>{

        public static final Passive DEFAULT = new Passive();

        public static final ParticleBuilder particleBuilder = new ParticleBuilder(Particle.BUBBLE_COLUMN_UP);
        static {
            particleBuilder.offset(0.2, 0.2, 0.2);
            particleBuilder.extra(0);
            particleBuilder.count(1);
        }

        public Passive(){
            getComponents().set(new AttributeBasedProperty(10, RPGUComponents.ABILITY_COOLDOWN_TIME));
        }

        @Override
        protected @NotNull ActionResult action(@NotNull CustomItem customItem, @NotNull LivingEntity livingEntity, @NotNull UniversalInventorySlot universalInventorySlot, @NotNull Integer integer) {
            Player player = (Player) livingEntity;
            if (player.getMaximumAir() == player.getRemainingAir()) return ActionResult.NO_COOLDOWN;

            FishHook fishHook = player.getFishHook();
            if (fishHook == null) return ActionResult.NO_COOLDOWN;

            Material material = fishHook.getLocation().add(0, 0.3, 0).getBlock().getBlockData().getMaterial();
            if (!material.isAir() && material != Material.BUBBLE_COLUMN) return ActionResult.NO_COOLDOWN;

            player.setRemainingAir(Math.min(player.getRemainingAir() + 20*2, player.getMaximumAir()));

            particleBuilder.location(player.getEyeLocation());
            particleBuilder.spawn();
            return ActionResult.FULL_COOLDOWN;
        }

        @Override
        public @Nullable Pair<List<String>, List<String>> getEngAndRuDescription() {
            return null;
        }

        @Override
        public @NotNull CustomEquipmentSlot getSlot() {
            return EquipmentSlots.ARTIFACTS;
        }

        @Override
        public void tick(@NotNull CustomItem customItem, @NotNull Player player, @NotNull BaseUniversalSlot baseUniversalSlot, int i) {
            activate(customItem, player, baseUniversalSlot, i);
        }

        @Override
        public @NotNull CustomComponentType<? super RPGUPassiveItem, ? extends CustomComponent<? super RPGUPassiveItem>> getType() {
            return Abilities.FISHERMAN_SNORKEL;
        }
    }

}























