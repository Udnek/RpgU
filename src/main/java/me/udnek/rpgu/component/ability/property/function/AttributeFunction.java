package me.udnek.rpgu.component.ability.property.function;

import me.udnek.itemscoreu.customattribute.CustomAttribute;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

public class AttributeFunction implements PropertyFunction<LivingEntity, Double> {

    protected CustomAttribute attribute;
    protected PropertyFunction<LivingEntity, Double> base;

    public AttributeFunction(@NotNull CustomAttribute attribute, @NotNull PropertyFunction<LivingEntity, Double> base){
        this.attribute = attribute;
        this.base = base;
    }

    public AttributeFunction(@NotNull CustomAttribute attribute, double base){
        this(attribute, Functions.CONSTANT(base));
    }

    @Override
    public boolean isConstant() {
        return isZeroConstant();
    }

    @Override
    public boolean isZeroConstant() {
        return base.isZeroConstant();
    }

    @Override
    public @NotNull Double getBase() {
        return base.getBase();
    }

    @Override
    public @NotNull Double apply(@NotNull LivingEntity livingEntity) {
        return attribute.calculateWithBase(livingEntity, base.apply(livingEntity));
    }

    @Override
    public @NotNull Component describeWithModifier(@NotNull Function<Double, Double> modifier) {
        if (Functions.IS_DEBUG) return Component.text("attr(").append(base.describeWithModifier(modifier)).append(Component.text(")"));
        return base.describeWithModifier(modifier);
    }
}
