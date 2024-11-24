package me.udnek.rpgu.component.ability.property.type;

import me.udnek.itemscoreu.customcomponent.ConstructableComponentType;
import me.udnek.rpgu.component.ability.ActiveAbilityComponent;
import me.udnek.rpgu.component.ability.property.AbilityProperty;
import org.jetbrains.annotations.NotNull;

public class AbilityPropertyType<Component extends AbilityProperty<?, ?>> extends ConstructableComponentType<ActiveAbilityComponent<?>, Component>{

    public AbilityPropertyType(@NotNull String rawId, @NotNull Component defaultComponent) {
        super(rawId, defaultComponent);
    }

}
