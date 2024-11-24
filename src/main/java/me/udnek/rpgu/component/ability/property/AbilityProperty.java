package me.udnek.rpgu.component.ability.property;

import me.udnek.itemscoreu.customcomponent.CustomComponent;
import me.udnek.rpgu.component.ability.ActiveAbilityComponent;
import me.udnek.rpgu.lore.ActiveAbilityLorePart;
import org.jetbrains.annotations.NotNull;

interface AbilityProperty<Context, Value> extends CustomComponent<ActiveAbilityComponent<?>> {
    Value getBase();
    Value get(@NotNull Context context);
    void describe(@NotNull ActiveAbilityLorePart componentable);
}
