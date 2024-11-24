package me.udnek.rpgu.component.ability.property;

import org.jetbrains.annotations.NotNull;

public abstract class AbstractAbilityProperty<Context, Value> implements AbilityProperty<Context, Value> {

    protected Value base;

    public AbstractAbilityProperty(Value base){
        this.base = base;
    }

    @Override
    public @NotNull Value getBase() {return base;}

}
