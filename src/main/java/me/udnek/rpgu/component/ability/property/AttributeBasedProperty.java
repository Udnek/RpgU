package me.udnek.rpgu.component.ability.property;

import me.udnek.itemscoreu.customcomponent.CustomComponentType;
import me.udnek.rpgu.component.ability.ActiveAbilityComponent;
import me.udnek.rpgu.component.ability.property.type.AttributeBasedPropertyType;
import me.udnek.rpgu.lore.ActiveAbilityLorePart;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

public class AttributeBasedProperty extends AbstractAbilityProperty<Player, Double> {

    protected AttributeBasedPropertyType type;

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

    @Override
    public @NotNull Double get(@NotNull Player player) {
        double base = getBase();
        if (base < type.getAttribute().getMinimum()) return 0d;
        return type.getAttribute().calculateWithBase(player, base);
    }

    @Override
    public @NotNull AttributeBasedPropertyType getType() {
        return type;
    }

    @Override
    public void describe(@NotNull ActiveAbilityLorePart componentable) {
        type.describe(this, componentable);
    }
}
