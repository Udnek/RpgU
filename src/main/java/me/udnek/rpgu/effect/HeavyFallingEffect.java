package me.udnek.rpgu.effect;

import me.udnek.itemscoreu.customeffect.ConstructableCustomEffect;
import me.udnek.rpgu.RpgU;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionEffectTypeCategory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public class HeavyFallingEffect extends ConstructableCustomEffect {
    @Override
    public @NotNull PotionEffectTypeCategory getCategory() {return PotionEffectTypeCategory.HARMFUL;}

    @Override
    public @Nullable PotionEffectType getVanillaDisguise() {return null;}

    @Override
    public @NotNull String getRawId() {return "heavy_falling";}

    @Override
    public void addAttributes(@NotNull AttributeConsumer consumer) {
        consumer.accept(Attribute.FALL_DAMAGE_MULTIPLIER, new NamespacedKey(RpgU.getInstance(), "fall_damage_multiplier_" + getRawId()), 0.1, AttributeModifier.Operation.ADD_SCALAR);
        consumer.accept(Attribute.GRAVITY, new NamespacedKey(RpgU.getInstance(), "gravity_" + getRawId()), 0.5, AttributeModifier.Operation.ADD_SCALAR);
    }


}
