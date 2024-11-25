package me.udnek.rpgu.mechanic.magicpotential;

import me.udnek.rpgu.attribute.Attributes;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

public interface MagicPotentialBasedFormula extends Function<Double, Double>{

    MagicPotentialBasedFormula ZERO = new MagicPotentialBasedFormula() {
        @Override
        public @NotNull Component getDescriptionWithNumberModifier(@NotNull Function<@NotNull Double, @NotNull Double> function) {
            return Component.text(function.apply(0d));
        }
        @Override
        public boolean isAlwaysZero() {return true;}
        @Override
        public @NotNull Double apply(@NotNull Double mp) {return 0d;}
    };

    @NotNull Component getDescriptionWithNumberModifier(@NotNull Function<@NotNull Double, @NotNull Double> function);
    default @NotNull Component getDescription(){
        return getDescriptionWithNumberModifier(Function.identity());
    }

    boolean isAlwaysZero();

    default double apply(@NotNull LivingEntity entity){
        return apply(Attributes.MAGICAL_POTENTIAL.calculate(entity));
    }

    @Override
    @NotNull Double apply(@NotNull Double mp);
}
