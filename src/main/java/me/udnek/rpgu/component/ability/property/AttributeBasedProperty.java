package me.udnek.rpgu.component.ability.property;

import me.udnek.itemscoreu.customattribute.CustomAttribute;
import me.udnek.rpgu.component.ability.property.function.AttributeFunction;
import me.udnek.rpgu.component.ability.property.function.PropertyFunction;
import me.udnek.rpgu.component.ability.property.type.AttributeBasedPropertyType;
import me.udnek.rpgu.lore.ability.AbilityLorePart;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class AttributeBasedProperty extends AbstractAbilityProperty<Player, Double> {

    protected @NotNull AttributeBasedPropertyType type;

    public AttributeBasedProperty(@NotNull AttributeFunction function, @NotNull AttributeBasedPropertyType type) {
        super(function);
        this.type = type;
    }

    public AttributeBasedProperty(double base, @NotNull AttributeBasedPropertyType type) {
        this(new AttributeFunction(type.getAttribute(), base), type);
    }

    @Override
    public @NotNull AttributeBasedPropertyType getType() {
        return type;
    }

    @Override
    public @NotNull Double get(@NotNull Player player) {
        if (getFunction().getBase() < getType().getAttribute().getMinimum()) return getType().getAttribute().getMinimum();
        return getFunction().apply(player);
    }

    @Override
    public void describe(@NotNull AbilityLorePart componentable) {
        getType().describe(this, componentable);
    }
}
