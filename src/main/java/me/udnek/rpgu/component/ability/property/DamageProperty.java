package me.udnek.rpgu.component.ability.property;

import me.udnek.coreu.custom.component.CustomComponent;
import me.udnek.coreu.custom.component.CustomComponentType;
import me.udnek.coreu.rpgu.component.ability.RPGUItemAbility;
import me.udnek.coreu.rpgu.component.ability.property.RPGUAbilityProperty;
import me.udnek.coreu.rpgu.component.ability.property.function.MultiLineDescription;
import me.udnek.coreu.rpgu.component.ability.property.function.RPGUPropertyFunction;
import me.udnek.coreu.rpgu.lore.ability.AbilityLorePart;
import me.udnek.rpgu.component.Components;
import me.udnek.rpgu.mechanic.damaging.Damage;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class DamageProperty implements RPGUAbilityProperty<LivingEntity, Damage> {

    protected RPGUPropertyFunction<LivingEntity, Double> formula;
    protected Damage.Type type;

    public DamageProperty(@NotNull Damage.Type type, @NotNull RPGUPropertyFunction<LivingEntity, Double> damageFunction){
        this.type = type;
        this.formula = damageFunction;
    }

    @Override
    public @NotNull Damage getBase() {
        return new Damage(type, formula.getBase());
    }

    @Override
    public @NotNull Damage get(@NotNull LivingEntity entity) {
        return new Damage(type, formula.apply(entity));
    }

    @Override
    public void describe(@NotNull AbilityLorePart componentable) {
        MultiLineDescription description = formula.describe();
        if (description.getLines().size() > 1){
            componentable.addAbilityProperty(Component.translatable(
                    "rpgu_ability_property.rpgu.damage",
                    List.of(Component.translatable(type.translationKey()), Component.empty())
            ));
            for (Component component : description.getLines()) {
                componentable.addAbilityPropertyDoubleTab(component);
            }
        } else {
            componentable.addAbilityProperty(Component.translatable(
                    "rpgu_ability_property.rpgu.damage",
                    List.of(Component.translatable(type.translationKey()), description.getLines().getFirst())
            ));
        }
    }

    @Override
    public @NotNull CustomComponentType<? super RPGUItemAbility<?>, ? extends CustomComponent<? super RPGUItemAbility<?>>> getType() {
        return Components.ABILITY_DAMAGE;
    }
}
