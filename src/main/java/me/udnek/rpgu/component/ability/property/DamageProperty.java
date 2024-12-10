package me.udnek.rpgu.component.ability.property;

import me.udnek.itemscoreu.customcomponent.CustomComponentType;
import me.udnek.rpgu.component.ComponentTypes;
import me.udnek.rpgu.component.ability.AbilityComponent;
import me.udnek.rpgu.lore.ability.AbilityLorePart;
import me.udnek.rpgu.mechanic.damaging.Damage;
import me.udnek.rpgu.mechanic.damaging.formula.DamageFormula;
import org.jetbrains.annotations.NotNull;

public class DamageProperty implements AbilityProperty<Double, Damage> {

    protected DamageFormula<Double> formula;

    public DamageProperty(@NotNull DamageFormula<Double> damageFormula){
        this.formula = damageFormula;
    }

    @Override
    public @NotNull Damage getBase() {
        return formula.calculate(0d);
    }

    @Override
    public @NotNull Damage get(@NotNull Double mp) {
        return formula.calculate(mp);
    }

    @Override
    public @NotNull CustomComponentType<AbilityComponent<?>, ?> getType() {
        return ComponentTypes.ABILITY_DAMAGE;
    }

    @Override
    public void describe(@NotNull AbilityLorePart componentable) {
        formula.description(componentable::addAbilityStat);
    }
}
