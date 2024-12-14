package me.udnek.rpgu.component.ability.property.function;

import me.udnek.rpgu.attribute.Attributes;
import org.bukkit.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

public interface MagicPotentialBasedFunction extends PropertyFunction<Double, Double> {

    MagicPotentialBasedFunction ZERO = new LinearMPFunction(0, 0);


    default double apply(@NotNull LivingEntity entity){
        return apply(Attributes.MAGICAL_POTENTIAL.calculate(entity));
    }

    @Override
    @NotNull Double apply(@NotNull Double mp);
}
