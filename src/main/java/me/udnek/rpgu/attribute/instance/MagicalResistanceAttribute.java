package me.udnek.rpgu.attribute.instance;

import me.udnek.itemscoreu.customattribute.ConstructableCustomAttribute;
import me.udnek.rpgu.attribute.Attributes;
import me.udnek.rpgu.effect.Effects;
import org.bukkit.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

public class MagicalResistanceAttribute extends ConstructableCustomAttribute {

    public MagicalResistanceAttribute(@NotNull String rawId, double defaultValue, double min, double max) {
        super(rawId, defaultValue, min, max);
    }

    @Override
    public double calculateWithBase(@NotNull LivingEntity entity, double base) {
        double magicalDefenseMul = Attributes.MAGICAL_DEFENSE_MULTIPLIER.calculate(entity);
        double potential = Attributes.MAGICAL_POTENTIAL.calculate(entity);
        base += MagicalDefenseMultiplierAttribute.calculateAbsorption(magicalDefenseMul*potential);
        return super.calculateWithBase(entity, base);
    }
}
