package me.udnek.rpgu.component.ability.property;

import me.udnek.rpgu.component.ability.property.type.AttributeBasedPropertyType;
import me.udnek.rpgu.lore.ActiveAbilityLorePart;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

public class AttributeBasedProperty extends AbstractAbilityProperty<Player, Double> {

    protected AttributeBasedPropertyType type;


    public static AttributeBasedProperty from(double base, @NotNull AttributeBasedPropertyType type){
        return new AttributeBasedProperty(base).setType(type);
    }

    @ApiStatus.Internal
    public AttributeBasedProperty(double base) {
        super(base);
    }

    @ApiStatus.Internal
    public @NotNull AttributeBasedProperty setType(@NotNull AttributeBasedPropertyType type){
        this.type = type;
        return this;
    }

    @Override
    public @NotNull Double getBase() {return base;}

    public double getWithBase(@NotNull Player player, double base){
        if (base < getType().getAttribute().getMinimum()) return 0d;
        return getType().getAttribute().calculateWithBase(player, base);
    }

    @Override
    public @NotNull Double get(@NotNull Player player) {
        return getWithBase(player, getBase());
    }

    @Override
    public @NotNull AttributeBasedPropertyType getType() {
        return type;
    }

    @Override
    public void describe(@NotNull ActiveAbilityLorePart componentable) {
        getType().describe(this, componentable);
    }
}
