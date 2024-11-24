package me.udnek.rpgu.component.ability.property.type;

import me.udnek.itemscoreu.customcomponent.ConstructableComponentType;
import me.udnek.itemscoreu.customcomponent.CustomComponent;
import me.udnek.rpgu.component.ability.ActiveAbilityComponent;
import me.udnek.rpgu.lore.ActiveAbilityLorePart;
import org.jetbrains.annotations.NotNull;

public class PropertyType<Component extends CustomComponent<ActiveAbilityComponent<?>>> extends ConstructableComponentType<ActiveAbilityComponent<?>, Component>{

    public PropertyType(@NotNull String rawId, @NotNull Component defaultComponent) {
        super(rawId, defaultComponent);
    }

}
