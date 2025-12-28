package me.udnek.rpgu.attribute.instance;

import me.udnek.coreu.custom.attribute.ConstructableReversedCustomAttribute;
import me.udnek.rpgu.attribute.Attributes;
import org.bukkit.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

public class MagicResistanceAttribute extends ConstructableReversedCustomAttribute {

    public static final double MAX_DEFENSE_ABSORPTION = 0.8;

    public MagicResistanceAttribute(@NotNull String rawId, double defaultValue, double min, double max) {
        super(rawId, defaultValue, min, max);
    }

    @Override
    public double calculateWithBase(@NotNull LivingEntity entity, double base) {
        double magicalDefenseMul = Attributes.MAGICAL_DEFENSE_MULTIPLIER.calculate(entity);
        double potential = Attributes.MAGICAL_POTENTIAL.calculate(entity);

        base += (magicalDefenseMul*potential)/Attributes.MAGICAL_DEFENSE_MULTIPLIER.getMax() * MAX_DEFENSE_ABSORPTION;

        return super.calculateWithBase(entity, base);
    }
}
