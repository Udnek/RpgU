package me.udnek.rpgu.mechanic.magicpotential;

import me.udnek.itemscoreu.util.Utils;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class LinearMPFormula implements MagicPotentialBasedFormula {

    protected double base;
    protected double perMp;

    public LinearMPFormula(double base, double perMp){
        this.base = base;
        this.perMp = perMp;
    }

    @Override
    public @NotNull Component getDescription() {
        return Component.translatable("magical_potential_formula.rpgu.linear",
                List.of(Component.text(Utils.roundToTwoDigits(base)), Component.text(Utils.roundToTwoDigits(perMp))));
    }

    @Override
    public boolean isAlwaysZero() {
        return base == 0 && perMp == 0;
    }

    @Override
    public @NotNull Double apply(@NotNull Double mp) {
        return base + mp*perMp;
    }
}
