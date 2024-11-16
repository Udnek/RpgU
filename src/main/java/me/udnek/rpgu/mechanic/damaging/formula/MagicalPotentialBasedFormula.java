package me.udnek.rpgu.mechanic.damaging.formula;

import me.udnek.itemscoreu.util.Utils;
import me.udnek.rpgu.mechanic.damaging.Damage;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Consumer;

public class MagicalPotentialBasedFormula implements DamageFormula<Double> {

    protected double physBase;
    protected double physPerMP;
    protected double mageBase;
    protected double magePerMP;

    public MagicalPotentialBasedFormula(double physBase, double physPerMP, double mageBase, double magePerMP){
        this.physBase = physBase;
        this.physPerMP = physPerMP;
        this.mageBase = mageBase;
        this.magePerMP = magePerMP;
    }
    public MagicalPotentialBasedFormula(double mageBase, double magePerMP){
        this(0, 0, mageBase, magePerMP);
    }

    @Override
    public @NotNull Damage calculate(@NotNull Double mp) {
        return new Damage(physBase + physPerMP*mp, mageBase + magePerMP*mp);
    }

    @Override
    public void description(@NotNull Consumer<@NotNull Component> consumer) {
        if (physBase != 0 || physPerMP != 0){
            consumer.accept(
                    Component.translatable("damage_formula.rpgu.physical",
                            Component.translatable("damage_formula.rpgu.base_plus_per_magical_potential",
                                    List.of(Component.text(Utils.roundToTwoDigits(physBase)), Component.text(Utils.roundToTwoDigits(physPerMP)))
                            )
                    )
            );
        }
        if (mageBase != 0 || magePerMP != 0) {
            consumer.accept(
                    Component.translatable("damage_formula.rpgu.magical",
                            Component.translatable("damage_formula.rpgu.base_plus_per_magical_potential",
                                    List.of(Component.text(Utils.roundToTwoDigits(mageBase)), Component.text(Utils.roundToTwoDigits(magePerMP)))
                            )
                    )
            );
        }
    }
}










