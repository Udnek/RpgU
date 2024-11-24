package me.udnek.rpgu.component.ability.property;

import me.udnek.itemscoreu.customcomponent.CustomComponent;
import me.udnek.rpgu.component.ability.AbilityDescribable;
import me.udnek.rpgu.component.ability.ActiveAbilityComponent;
import me.udnek.rpgu.lore.ActiveAbilityLorePart;
import org.jetbrains.annotations.NotNull;

public interface AbilityProperty<Context, Value> extends CustomComponent<ActiveAbilityComponent<?>>, AbilityDescribable {
    @NotNull Value getBase();
    @NotNull Value get(@NotNull Context context);
}
