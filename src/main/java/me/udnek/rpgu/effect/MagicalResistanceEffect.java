package me.udnek.rpgu.effect;

import me.udnek.itemscoreu.customattribute.CustomAttributeConsumer;
import me.udnek.itemscoreu.customattribute.CustomAttributeModifier;
import me.udnek.itemscoreu.customeffect.ConstructableCustomEffect;
import me.udnek.rpgu.attribute.Attributes;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Particle;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionEffectTypeCategory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MagicalResistanceEffect extends ConstructableCustomEffect {
    @Override
    public @NotNull PotionEffectTypeCategory getCategory() {return PotionEffectTypeCategory.BENEFICIAL;}

    @Override
    public @Nullable PotionEffectType getVanillaDisguise() {return PotionEffectType.LUCK;}

    @Override
    public @NotNull String getRawId() {return "magical_resistance";}

    @Override
    public @Nullable Particle getParticle() {
        return Particle.DUST_COLOR_TRANSITION;
    }

    @Override
    public @NotNull String translationKey() {
        return "effect.minecraft.luck";
    }

    @Override
    public void modifyParticleIfNotDefault(@NotNull ModifyParticleConsumer consumer) {
        consumer.dustTransition(TextColor.fromHexString("#fac2e4").value(), TextColor.fromHexString("#7d4a89").value(), 10);
    }

    @Override
    public void getCustomAttributes(@NotNull PotionEffect context, @NotNull CustomAttributeConsumer consumer) {
        consumer.accept(Attributes.MAGICAL_RESISTANCE, new CustomAttributeModifier((context.getAmplifier()+1)*0.1, AttributeModifier.Operation.MULTIPLY_SCALAR_1));
    }
}
