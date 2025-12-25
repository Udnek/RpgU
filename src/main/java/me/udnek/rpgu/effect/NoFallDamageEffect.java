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


public class NoFallDamageEffect extends ConstructableCustomEffect {
    @Override
    public @NotNull PotionEffectTypeCategory getCategory() {return PotionEffectTypeCategory.BENEFICIAL;}

    @Override
    public @Nullable PotionEffectType getVanillaDisguise() {return null;}

    @Override
    public @NotNull String getRawId() {return "no_fall_damage";}

    @Override
    public void addAttributes(@NotNull AttributeConsumer consumer) {
        consumer.accept(Attribute.FALL_DAMAGE_MULTIPLIER, new NamespacedKey(RpgU.getInstance(), "fall_damage_multiplier_" + getRawId()), -1, AttributeModifier.Operation.ADD_NUMBER);
    }


}
