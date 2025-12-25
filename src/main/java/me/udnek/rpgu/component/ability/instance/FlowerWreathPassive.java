package me.udnek.rpgu.component.ability.instance;

import com.destroystokyo.paper.ParticleBuilder;
import me.udnek.coreu.custom.component.CustomComponent;
import me.udnek.coreu.custom.component.CustomComponentType;
import me.udnek.coreu.custom.equipmentslot.slot.CustomEquipmentSlot;
import me.udnek.coreu.custom.equipmentslot.universal.BaseUniversalSlot;
import me.udnek.coreu.custom.equipmentslot.universal.UniversalInventorySlot;
import me.udnek.coreu.custom.item.CustomItem;
import me.udnek.coreu.rpgu.component.RPGUComponents;
import me.udnek.coreu.rpgu.component.RPGUPassiveItem;
import me.udnek.coreu.rpgu.component.ability.passive.RPGUConstructablePassiveAbility;
import me.udnek.coreu.rpgu.component.ability.property.AttributeBasedProperty;
import me.udnek.coreu.rpgu.component.ability.property.EffectsProperty;
import me.udnek.coreu.rpgu.component.ability.property.function.PropertyFunctions;
import me.udnek.rpgu.attribute.Attributes;
import me.udnek.rpgu.component.ability.Abilities;
import me.udnek.rpgu.equipment.slot.EquipmentSlots;
import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.*;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Random;

public class FlowerWreathPassive extends RPGUConstructablePassiveAbility<Integer> {

    public static final FlowerWreathPassive DEFAULT = new FlowerWreathPassive();

    public static final float yOffset = 4;
    public static final int DURATION = 20*20;

    public FlowerWreathPassive(){
        getComponents().set(new AttributeBasedProperty(20, RPGUComponents.ABILITY_COOLDOWN_TIME));
        getComponents().set(new AttributeBasedProperty(6, RPGUComponents.ABILITY_CAST_RANGE));
        getComponents().set(new EffectsProperty(new EffectsProperty.PotionData(
                PotionEffectType.REGENERATION,
                PropertyFunctions.CEIL(PropertyFunctions.ATTRIBUTE_WITH_BASE(Attributes.ABILITY_DURATION, DURATION)),
                PropertyFunctions.CONSTANT(0),
                true, true, true
        )));
    }

    @Override
    protected @NotNull ActionResult action(@NotNull CustomItem customItem, @NotNull LivingEntity livingEntity, @NotNull UniversalInventorySlot universalInventorySlot, @NotNull Integer integer) {
        Location location = randomOffset(livingEntity);
        boolean isInForest = isForestMaterial(location.getBlock().getType());
        if (!isInForest) return ActionResult.NO_COOLDOWN;

        Location from = livingEntity.getEyeLocation().add(0, -0.8, 0);
        Location to =location.toCenterLocation();

        ParticleBuilder particle = Particle.TRAIL.builder().location(from).count(10).offset(0.2, 0.2, 0.2);
        particle.data(new Particle.Trail(to, Color.fromRGB(92, 169, 4), 50)).spawn();
        particle.data(new Particle.Trail(to, Color.fromRGB(139,69,19),50)).spawn();
        getComponents().getOrException(RPGUComponents.ABILITY_EFFECTS).applyOn(livingEntity, livingEntity);
        return ActionResult.FULL_COOLDOWN;
    }

    public boolean isForestMaterial(@NotNull Material material) {
        return Tag.LOGS.isTagged(material) || Tag.LEAVES.isTagged(material);
    }

    @Override
    public @Nullable Pair<List<String>, List<String>> getEngAndRuDescription() {
        return null;
    }

    public @NotNull Location randomOffset(@NotNull LivingEntity livingEntity) {
        Random random = new Random();
        double castRange = getComponents().getOrException(RPGUComponents.ABILITY_CAST_RANGE).get(livingEntity);
        Location location = livingEntity.getLocation();
        location.add((random.nextFloat() - 0.5f) * 2 * castRange, (random.nextFloat() - 0.5f) * 2 * castRange + yOffset, (random.nextFloat() - 0.5f) * 2 * castRange);
        return location;
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
        return Abilities.FLOWER_WREATH;
    }
}
