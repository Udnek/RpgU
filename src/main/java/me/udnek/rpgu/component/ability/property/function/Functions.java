package me.udnek.rpgu.component.ability.property.function;

import me.udnek.itemscoreu.customattribute.CustomAttribute;
import me.udnek.itemscoreu.util.Utils;
import me.udnek.rpgu.attribute.Attributes;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

public class Functions {

    public static final boolean IS_DEBUG = false;

    public static @NotNull PropertyFunction<Player, Double> APPLY_MP(@NotNull PropertyFunction<Double, Double> function){
        return new PropertyFunction<>() {
            @Override
            public boolean isConstant() {return function.isConstant();}

            @Override
            public boolean isZeroConstant() {return function.isZeroConstant();}

            @Override
            public @NotNull Double getBase() {
                return function.getBase();
            }

            @Override
            public @NotNull Double apply(@NotNull Player player) {
                return function.apply(Attributes.MAGICAL_POTENTIAL.calculate(player));
            }

            @Override
            public @NotNull Component describeWithModifier(@NotNull Function<Double, Double> modifier) {
                if (IS_DEBUG) return Component.text("wrapMP(").append(function.describeWithModifier(modifier)).append(Component.text(")"));
                return function.describeWithModifier(modifier);
            }
        };
    }
    public static @NotNull AttributeFunction ATTRIBUTE(@NotNull CustomAttribute attribute, @NotNull PropertyFunction<Player, Double> base){
        return new AttributeFunction(attribute, base);
    }
    public static @NotNull AttributeFunction ATTRIBUTE(@NotNull CustomAttribute attribute,double base){
        return new AttributeFunction(attribute, base);
    }

    public static <Context, Value extends Number> @NotNull PropertyFunction<Context, Value> CONSTANT(@NotNull Value value){
        return new PropertyFunction<>() {

            @Override
            public boolean isZeroConstant() {return value.doubleValue() == 0;}

            @Override
            public boolean isConstant() {return true;}

            @Override
            public @NotNull Value getBase() {
                return value;
            }

            @Override
            public @NotNull Value apply(@NotNull Context context) {
                return value;
            }

            @Override
            public @NotNull Component describeWithModifier(@NotNull Function<Double, Double> modifier) {
                if(IS_DEBUG) return Component.text("const(" + modifier.apply(modifier.apply(value.doubleValue())) + ")");
                return Component.text(Utils.roundToTwoDigits(modifier.apply(value.doubleValue())));
            }

        };
    }
    public static <Context> @NotNull PropertyFunction<Context, Integer> CEIL(PropertyFunction<Context, Double> function){
        return new PropertyFunction<>() {
            @Override
            public boolean isConstant() {return function.isConstant();}

            @Override
            public boolean isZeroConstant() {return function.isZeroConstant();}

            @Override
            public @NotNull Integer getBase() {
                return (int) Math.ceil(function.getBase());
            }

            @Override
            public @NotNull Integer apply(@NotNull Context context) {
                return (int) Math.ceil(function.apply(context));
            }

            @Override
            public @NotNull Component describeWithModifier(@NotNull Function<Double, Double> modifier) {
                if (IS_DEBUG) return Component.text("ceil(").append(function.describeWithModifier(modifier)).append(Component.text(")"));
                return function.describeWithModifier(modifier);
            }
        };
    }
    public static <Context> @NotNull PropertyFunction<Context, Integer> FLOOR(PropertyFunction<Context, Double> function){
        return new PropertyFunction<>() {

            @Override
            public boolean isZeroConstant() {return function.isZeroConstant();}

            @Override
            public boolean isConstant() {return function.isConstant();}

            @Override
            public @NotNull Integer getBase() {
                return (int) Math.floor(function.getBase());
            }

            @Override
            public @NotNull Integer apply(@NotNull Context context) {
                return (int) Math.floor(function.apply(context));
            }

            @Override
            public @NotNull Component describeWithModifier(@NotNull Function<Double, Double> modifier) {
                if (IS_DEBUG) return Component.text("floor(").append(function.describeWithModifier(modifier)).append(Component.text(")"));
                return function.describeWithModifier(modifier);
            }
        };
    }
}
