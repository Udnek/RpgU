package me.udnek.rpgu.component.ability.property.function;

import me.udnek.coreu.util.Utils;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Function;

public class LinearMPFunction implements MagicPotentialBasedFunction {

    protected double base;
    protected double perMp;

    public LinearMPFunction(double base, double perMp){
        this.base = base;
        this.perMp = perMp;
    }

    @Override
    public @NotNull MultiLineDescription describeWithModifier(@NotNull Function<Double, Double> modifier) {
        return new MultiLineDescription().add(
                Component.translatable("magical_potential_formula.rpgu.linear",
                        List.of(Component.text(Utils.roundToTwoDigits(modifier.apply(base))),
                                Component.text(Utils.roundToTwoDigits(modifier.apply(perMp)))
                        )
                )
        );
    }

    @Override
    public boolean isZeroConstant() {
        return base == 0 && perMp == 0;
    }

    @Override
    public boolean isConstant() {
        return perMp == 0;
    }

    @Override
    public @NotNull Double getBase() {return base;}

    @Override
    public @NotNull Double apply(@NotNull Double mp) {
        return base + mp*perMp;
    }

}
