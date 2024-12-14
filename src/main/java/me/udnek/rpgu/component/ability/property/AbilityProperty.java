package me.udnek.rpgu.component.ability.property;

import me.udnek.itemscoreu.customcomponent.CustomComponent;
import me.udnek.rpgu.component.ability.AbilityComponent;
import me.udnek.rpgu.component.ability.property.function.PropertyFunction;
import me.udnek.rpgu.lore.ability.AbilityLorePart;
import org.jetbrains.annotations.NotNull;

public interface AbilityProperty<Context, Value> extends CustomComponent<AbilityComponent<?>> {
    @NotNull Value getBase();
    @NotNull Value get(@NotNull Context context);
    void describe(@NotNull AbilityLorePart componentable);
}
