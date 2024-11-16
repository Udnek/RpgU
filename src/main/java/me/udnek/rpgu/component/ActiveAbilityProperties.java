package me.udnek.rpgu.component;

import me.udnek.rpgu.attribute.Attributes;
import me.udnek.rpgu.mechanic.damaging.Damage;
import me.udnek.rpgu.mechanic.damaging.formula.DamageFormula;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

interface ActiveAbilityProperties<DamageContext> {

    @Nullable DamageFormula<DamageContext> getDamage();
    int getBaseCooldown();
    double getBaseCastRange();
    double getBaseAreaOfEffect();
    int getBaseCastTime();

}
