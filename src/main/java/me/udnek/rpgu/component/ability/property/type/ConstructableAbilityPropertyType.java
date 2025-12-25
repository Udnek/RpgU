package me.udnek.rpgu.component.ability.property.type;

import me.udnek.coreu.custom.component.ConstructableComponentType;
import me.udnek.rpgu.component.ability.AbilityComponent;
import me.udnek.rpgu.component.ability.property.AbilityProperty;
import org.jetbrains.annotations.NotNull;

public class ConstructableAbilityPropertyType<Component extends AbilityProperty<?, ?>> extends ConstructableComponentType<AbilityComponent<?>, Component> implements AbilityPropertyType<Component> {
    public ConstructableAbilityPropertyType(@NotNull String rawId, @NotNull Component defaultComponent) {
        super(rawId, defaultComponent);
    }
}
