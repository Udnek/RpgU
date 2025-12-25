package me.udnek.rpgu.effect;

import me.udnek.coreu.custom.effect.ConstructableCustomEffect;
import me.udnek.rpgu.RpgU;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionEffectTypeCategory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public class NoGravityEffect extends ConstructableCustomEffect {
    @Override
    public @NotNull PotionEffectTypeCategory getCategory() {return PotionEffectTypeCategory.HARMFUL;}

    @Override
    public @Nullable PotionEffectType getVanillaDisguise() {return null;}

    @Override
    public @NotNull String getRawId() {return "no_gravity";}

    @Override
    public void addAttributes(@NotNull AttributeConsumer consumer) {
        consumer.accept(Attribute.GRAVITY, new NamespacedKey(RpgU.getInstance(), "gravity_" + getRawId()), -1, AttributeModifier.Operation.MULTIPLY_SCALAR_1);
    }
}
