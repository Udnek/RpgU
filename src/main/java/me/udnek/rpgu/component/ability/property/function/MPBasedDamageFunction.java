package me.udnek.rpgu.component.ability.property.function;

import com.fasterxml.jackson.databind.type.LogicalType;
import me.udnek.rpgu.mechanic.damaging.Damage;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Function;

public class MPBasedDamageFunction implements DamageFunction<Double> {

    protected MagicPotentialBasedFunction physical;
    protected MagicPotentialBasedFunction magical;


    public static @NotNull MPBasedDamageFunction linear(double physBase, double physPerMP, double mageBase, double magePerMP){
        return new MPBasedDamageFunction(new LinearMPFunction(physBase, physPerMP), new LinearMPFunction(mageBase, magePerMP));
    }

    public static @NotNull MPBasedDamageFunction linearMageOnly(double mageBase, double magePerMP){
        return new MPBasedDamageFunction(null, new LinearMPFunction(mageBase, magePerMP));
    }

    public MPBasedDamageFunction(@Nullable MagicPotentialBasedFunction physical, @Nullable MagicPotentialBasedFunction magical){
        if (physical == null) physical = MagicPotentialBasedFunction.ZERO;
        if (magical == null) magical = MagicPotentialBasedFunction.ZERO;
        this.physical = physical;
        this.magical = magical;
    }

    @Override
    public boolean isConstant() {
        return physical.isConstant() && magical.isConstant();
    }

    @Override
    public boolean isZeroConstant() {
        return physical.isZeroConstant() && magical.isZeroConstant();
    }

    @Override
    public @NotNull Damage getBase() {
        return apply(0d);
    }

    @Override
    public @NotNull Damage apply(@NotNull Double mp) {
        return new Damage(physical.apply(mp), magical.apply(mp));
    }

    @Override
    public @NotNull MultiLineDescription describeWithModifier(@NotNull Function<Double, Double> modifier) {
        MultiLineDescription description = new MultiLineDescription();
        if (!physical.isZeroConstant()){
            description.addLine(Component.translatable("damage_formula.rpgu.physical", List.of(physical.describeWithModifier(modifier).join())));
        }
        if (!magical.isZeroConstant()) {
            description.addLine(Component.translatable("damage_formula.rpgu.magical", List.of(magical.describeWithModifier(modifier).join())));
        }
        return description;
    }
}










