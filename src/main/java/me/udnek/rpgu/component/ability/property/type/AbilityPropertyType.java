package me.udnek.rpgu.component.ability.property.type;

import me.udnek.coreu.custom.component.CustomComponentType;
import me.udnek.rpgu.component.ability.AbilityComponent;
import me.udnek.rpgu.component.ability.property.AbilityProperty;

public interface AbilityPropertyType<Component extends AbilityProperty<?, ?>> extends CustomComponentType<AbilityComponent<?>, Component> {
}
