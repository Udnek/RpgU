package me.udnek.rpgu.component.ability.property;

import me.udnek.coreu.custom.component.CustomComponentType;
import me.udnek.rpgu.component.ComponentTypes;
import me.udnek.rpgu.component.ability.AbilityComponent;
import me.udnek.rpgu.component.ability.property.function.DamageFunction;
import me.udnek.rpgu.component.ability.property.function.MultiLineDescription;
import me.udnek.rpgu.lore.ability.AbilityLorePart;
import me.udnek.rpgu.mechanic.damaging.Damage;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

public class DamageProperty implements AbilityProperty<Double, Damage> {

    protected DamageFunction<Double> formula;

    public DamageProperty(@NotNull DamageFunction<Double> damageFunction){
        this.formula = damageFunction;
    }

    @Override
    public @NotNull Damage getBase() {
        return formula.apply(0d);
    }

    @Override
    public @NotNull Damage get(@NotNull Double mp) {
        return formula.apply(mp);
    }

    @Override
    public @NotNull CustomComponentType<AbilityComponent<?>, ?> getType() {
        return ComponentTypes.ABILITY_DAMAGE;
    }

    @Override
    public void describe(@NotNull AbilityLorePart componentable) {
        MultiLineDescription description = formula.describe();
        if (description.getComponents().size() > 1){
            componentable.addAbilityStat(Component.translatable("ability.rpgu.damage", Component.empty()));
            for (Component component : description.getComponents()) {
                componentable.addAbilityStatDoubleTab(component);
            }
        } else {
            componentable.addAbilityStat(Component.translatable("ability.rpgu.damage", description.join()));
        }

    }
}
