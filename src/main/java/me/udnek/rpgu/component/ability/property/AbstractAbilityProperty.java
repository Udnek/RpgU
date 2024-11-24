package me.udnek.rpgu.component.ability.property;

public abstract class AbstractAbilityProperty<Context, Value> implements AbilityProperty<Context, Value> {

    protected Value base;

    public AbstractAbilityProperty(Value base){
        this.base = base;
    }

    @Override
    public Value getBase() {return base;}

}
