package me.udnek.rpgu.component.ability.property.type;

import me.udnek.itemscoreu.customcomponent.CustomComponent;
import me.udnek.rpgu.component.ability.ActiveAbilityComponent;
import me.udnek.rpgu.lore.ActiveAbilityLorePart;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

public abstract class SelfDescribedPropertyType<Component extends CustomComponent<ActiveAbilityComponent<?>>> extends PropertyType<Component>{

    public SelfDescribedPropertyType(@NotNull String rawId, @NotNull Component defaultComponent) {
        super(rawId, defaultComponent);
    }

    abstract public void describe(@NotNull Component component, @NotNull ActiveAbilityLorePart componentable);
}
