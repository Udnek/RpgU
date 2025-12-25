package me.udnek.rpgu.component.ability.property;

import me.udnek.coreu.rpgu.component.ability.property.function.MultiLineDescription;
import me.udnek.coreu.rpgu.component.ability.property.function.RPGUPropertyFunction;
import me.udnek.rpgu.attribute.Attributes;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

public class Functions {

    public static @NotNull RPGUPropertyFunction<LivingEntity, Double> ENTITY_TO_MP() {
        return new RPGUPropertyFunction<>() {
            @Override
            public boolean isConstant() {
                return false;
            }

            @Override
            public boolean isZeroConstant() {
                return false;
            }

            @Override
            public @NotNull Double getBase() {
                return Attributes.MAGICAL_POTENTIAL.getDefault();
            }

            @Override
            public @NotNull Double apply(@NotNull LivingEntity livingEntity) {
                return Attributes.MAGICAL_POTENTIAL.calculate(livingEntity);
            }

            @Override
            public @NotNull MultiLineDescription describeWithModifier(@NotNull Function<Double, Double> modifier) {
                return MultiLineDescription.of(Component.translatable("ability_property_description.rpgu.magic_potential"));
            }
        };
    }
}
