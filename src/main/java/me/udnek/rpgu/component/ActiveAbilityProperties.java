package me.udnek.rpgu.component;

import me.udnek.rpgu.mechanic.damaging.formula.DamageFormula;
import org.jetbrains.annotations.Nullable;

interface ActiveAbilityProperties<DamageContext> {

    @Nullable DamageFormula<DamageContext> getDamage();
    int getBaseCooldown();
    double getBaseCastRange();
    double getBaseAreaOfEffect();
    int getBaseCastTime();

}
