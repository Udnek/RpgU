package me.udnek.rpgu.component.ability.property.type;

import me.udnek.rpgu.component.ability.property.AbilityProperty;
import me.udnek.rpgu.lore.ability.AbilityLorePart;
import org.jetbrains.annotations.NotNull;

public interface SelfDescribedPropertyType<Value, Component extends AbilityProperty<?, Value>> extends AbilityPropertyType<Component> {

    //void describe(@NotNull net.kyori.adventure.text.Component text, @NotNull AbilityLorePart componentable);
   // void describe(@NotNull Value component, @NotNull AbilityLorePart componentable);
/*    default void describe(@NotNull Component component, @NotNull AbilityLorePart componentable){
        describe(component.getBase(), componentable);
    }*/
    void describe(@NotNull Component component, @NotNull AbilityLorePart componentable);
}
