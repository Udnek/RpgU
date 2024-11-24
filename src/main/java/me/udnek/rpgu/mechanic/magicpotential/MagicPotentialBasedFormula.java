package me.udnek.rpgu.mechanic.magicpotential;

import me.udnek.rpgu.attribute.Attributes;
import me.udnek.rpgu.component.ability.AbilityDescribable;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;

public interface MagicPotentialBasedFormula extends Function<Double, Double>{

    MagicPotentialBasedFormula ZERO = new MagicPotentialBasedFormula() {
        @Override
        public @NotNull Component getDescription() {return Component.text(0);}
        @Override
        public boolean isAlwaysZero() {return true;}
        @Override
        public @NotNull Double apply(@NotNull Double mp) {return 0d;}
    };

    @NotNull Component getDescription();

    boolean isAlwaysZero();

    default double apply(@NotNull LivingEntity entity){
        return apply(Attributes.MAGICAL_POTENTIAL.calculate(entity));
    }

    @Override
    @NotNull Double apply(@NotNull Double mp);
}
