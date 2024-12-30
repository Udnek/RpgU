package me.udnek.rpgu.effect;

import me.udnek.itemscoreu.customeffect.ConstructableCustomEffect;
import me.udnek.rpgu.RpgU;
import me.udnek.rpgu.particle.RootParticle;
import org.bukkit.NamespacedKey;
import org.bukkit.Particle;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionEffectTypeCategory;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class RootEffect extends ConstructableCustomEffect {
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
        return "root";
    }

    @Override
    public void apply(@NotNull LivingEntity bukkit, int duration, int amplifier, boolean ambient, boolean showParticles, boolean showIcon) {
        super.apply(bukkit, duration, amplifier, ambient, showParticles, showIcon);
        bukkit.setVelocity(new Vector());
        new RootParticle(bukkit).play();
    }

    @Override
    public @Nullable Particle getParticle() {
        return Particle.FALLING_SPORE_BLOSSOM;
    }

    @Override
    public void addAttributes(@NotNull AttributeConsumer consumer) {
        consumer.accept(Attribute.MOVEMENT_SPEED,  new NamespacedKey(RpgU.getInstance(), "movement_speed_" + getRawId()),-100, AttributeModifier.Operation.ADD_NUMBER);
        consumer.accept(Attribute.JUMP_STRENGTH,  new NamespacedKey(RpgU.getInstance(), "jump_strength_" + getRawId()),-100, AttributeModifier.Operation.ADD_NUMBER);
        consumer.accept(Attribute.KNOCKBACK_RESISTANCE,  new NamespacedKey(RpgU.getInstance(), "knockback_resistance_" + getRawId()),100, AttributeModifier.Operation.ADD_NUMBER);
    }
}
