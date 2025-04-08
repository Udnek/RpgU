package me.udnek.rpgu.component.ability;

import me.udnek.itemscoreu.customcomponent.ComponentHolder;
import me.udnek.itemscoreu.customcomponent.CustomComponent;
import me.udnek.itemscoreu.customitem.CustomItem;
import me.udnek.itemscoreu.util.LoreBuilder;
import org.jetbrains.annotations.NotNull;

public interface AbilityComponent<ActivationContext> extends CustomComponent<CustomItem>, ComponentHolder<AbilityComponent<?>, CustomComponent<AbilityComponent<?>>> {
    void getLore(@NotNull LoreBuilder loreBuilder);
}
