package me.udnek.rpgu.component.ability;

import me.udnek.coreu.custom.component.ComponentHolder;
import me.udnek.coreu.custom.component.CustomComponent;
import me.udnek.coreu.custom.item.CustomItem;
import me.udnek.coreu.util.LoreBuilder;
import org.jetbrains.annotations.NotNull;

public interface AbilityComponent<ActivationContext> extends CustomComponent<CustomItem>, ComponentHolder<AbilityComponent<?>, CustomComponent<AbilityComponent<?>>> {
    void getLore(@NotNull LoreBuilder loreBuilder);
}
