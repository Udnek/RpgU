package me.udnek.rpgu.component.ability;

import me.udnek.itemscoreu.customcomponent.ComponentHolder;
import me.udnek.itemscoreu.customcomponent.CustomComponent;
import me.udnek.itemscoreu.customitem.CustomItem;
import me.udnek.itemscoreu.util.LoreBuilder;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public interface AbilityComponent<ActivationContext> extends CustomComponent<CustomItem>, ComponentHolder<AbilityComponent<?>> {
    void getLore(@NotNull LoreBuilder loreBuilder);
    void activate(@NotNull CustomItem customItem, @NotNull Player player, @NotNull ActivationContext activationContext);
}
