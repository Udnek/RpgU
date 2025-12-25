package me.udnek.rpgu.effect;

import me.udnek.coreu.custom.effect.ConstructableCustomEffect;
import me.udnek.rpgu.RpgU;
import me.udnek.rpgu.particle.StunnedParticle;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Listener;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionEffectTypeCategory;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class StunEffect extends ConstructableCustomEffect implements Listener {
    @Override
    public @NotNull PotionEffectTypeCategory getCategory() {
        return PotionEffectTypeCategory.HARMFUL;
    }

    @Override
    public @Nullable PotionEffectType getVanillaDisguise() {
        return PotionEffectType.SLOWNESS;
    }

    @Override
    public @NotNull String getRawId() {
        return "stun";
    }

    @Override
    public void apply(@NotNull LivingEntity livingEntity, int duration, int amplifier, boolean ambient, boolean showParticles, boolean showIcon) {
        super.apply(livingEntity, duration, amplifier, ambient, showParticles, showIcon);
        new StunnedParticle(livingEntity, duration).play(livingEntity.getLocation());
    }

    @Override
    public void addAttributes(@NotNull AttributeConsumer consumer) {
        consumer.accept(Attribute.MOVEMENT_SPEED,  new NamespacedKey(RpgU.getInstance(), "movement_speed_" + getRawId()),-100, AttributeModifier.Operation.ADD_NUMBER);
        consumer.accept(Attribute.JUMP_STRENGTH,  new NamespacedKey(RpgU.getInstance(), "jump_strength_" + getRawId()),-100, AttributeModifier.Operation.ADD_NUMBER);
        consumer.accept(Attribute.BLOCK_INTERACTION_RANGE, new NamespacedKey(RpgU.getInstance(), "block_interaction_range_" + getRawId()),-100, AttributeModifier.Operation.ADD_NUMBER);
        consumer.accept(Attribute.ATTACK_SPEED, new NamespacedKey(RpgU.getInstance(), "attack_speed_" + getRawId()), -100, AttributeModifier.Operation.ADD_NUMBER);
        consumer.accept(Attribute.ENTITY_INTERACTION_RANGE, new NamespacedKey(RpgU.getInstance(), "entity_interaction_range_" + getRawId()), -100, AttributeModifier.Operation.ADD_NUMBER);
    }
}
