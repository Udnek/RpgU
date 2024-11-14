package me.udnek.rpgu.effect;

import me.udnek.itemscoreu.customeffect.ConstructableCustomEffect;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Particle;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionEffectTypeCategory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MagicalResistance extends ConstructableCustomEffect {
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
    public void modifyParticleIfNotDefault(@NotNull ModifyParticleConsumer consumer) {
        consumer.dustTransition(TextColor.fromHexString("#fac2e4").value(), TextColor.fromHexString("#7d4a89").value(), 10);
    }
}
