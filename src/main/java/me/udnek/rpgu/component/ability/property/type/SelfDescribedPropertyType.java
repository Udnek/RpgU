package me.udnek.rpgu.component.ability.property.type;

import me.udnek.rpgu.component.ability.property.AbilityProperty;
import me.udnek.rpgu.lore.ActiveAbilityLorePart;
import org.jetbrains.annotations.NotNull;

public abstract class SelfDescribedPropertyType<Value, Component extends AbilityProperty<?, Value>> extends AbilityPropertyType<Component> {

    public SelfDescribedPropertyType(@NotNull String rawId, @NotNull Component defaultComponent) {
        super(rawId, defaultComponent);
    }

    abstract public void describe(@NotNull net.kyori.adventure.text.Component text, @NotNull ActiveAbilityLorePart componentable);
    abstract public void describe(@NotNull Value component, @NotNull ActiveAbilityLorePart componentable);
    public void describe(@NotNull Component component, @NotNull ActiveAbilityLorePart componentable){
        describe(component.getBase(), componentable);
    }
}
