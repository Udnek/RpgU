package me.udnek.rpgu.mechanic.damaging.formula;

import me.udnek.itemscoreu.util.Utils;
import me.udnek.rpgu.mechanic.damaging.Damage;
import me.udnek.rpgu.mechanic.magicpotential.LinearMPFormula;
import me.udnek.rpgu.mechanic.magicpotential.MagicPotentialBasedFormula;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Consumer;

public class MPBasedDamageFormula implements DamageFormula<Double> {

    protected MagicPotentialBasedFormula physical;
    protected MagicPotentialBasedFormula magical;


    public static @NotNull MPBasedDamageFormula linear(double physBase, double physPerMP, double mageBase, double magePerMP){
        return new MPBasedDamageFormula(new LinearMPFormula(physBase, physPerMP), new LinearMPFormula(mageBase, magePerMP));
    }

    public static @NotNull MPBasedDamageFormula linearMageOnly(double mageBase, double magePerMP){
        return new MPBasedDamageFormula(null, new LinearMPFormula(mageBase, magePerMP));
    }

    public MPBasedDamageFormula(@Nullable MagicPotentialBasedFormula physical, @Nullable MagicPotentialBasedFormula magical){
        if (physical == null) physical = MagicPotentialBasedFormula.ZERO;
        if (magical == null) magical = MagicPotentialBasedFormula.ZERO;
        this.physical = physical;
        this.magical = magical;
    }


    @Override
    public @NotNull Damage calculate(@NotNull Double mp) {
        return new Damage(physical.apply(mp), magical.apply(mp));
    }

    @Override
    public void description(@NotNull Consumer<@NotNull Component> consumer) {
        if (!physical.isAlwaysZero()){
            consumer.accept(Component.translatable("damage_formula.rpgu.physical", List.of(physical.getDescription())));
        }
        if (!magical.isAlwaysZero()) {
            consumer.accept(Component.translatable("damage_formula.rpgu.magical", List.of(magical.getDescription())));
        }
    }
}










