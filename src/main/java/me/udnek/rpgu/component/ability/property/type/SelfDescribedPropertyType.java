package me.udnek.rpgu.component.ability.property.type;

import me.udnek.rpgu.component.ability.property.AbilityProperty;
import me.udnek.rpgu.lore.ability.AbilityLorePart;
import me.udnek.rpgu.lore.ability.ActiveAbilityLorePart;
import org.jetbrains.annotations.NotNull;

public abstract class SelfDescribedPropertyType<Value, Component extends AbilityProperty<?, Value>> extends AbilityPropertyType<Component> {

    public SelfDescribedPropertyType(@NotNull String rawId, @NotNull Component defaultComponent) {
        super(rawId, defaultComponent);
    }

    abstract public void describe(@NotNull net.kyori.adventure.text.Component text, @NotNull AbilityLorePart componentable);
    abstract public void describe(@NotNull Value component, @NotNull AbilityLorePart componentable);
    public void describe(@NotNull Component component, @NotNull AbilityLorePart componentable){
        describe(component.getBase(), componentable);
    }
}
