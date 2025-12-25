package me.udnek.rpgu.component.ability.active;

import io.papermc.paper.event.player.PlayerStopUsingItemEvent;
import me.udnek.coreu.custom.component.CustomComponent;
import me.udnek.coreu.custom.component.CustomComponentMap;
import me.udnek.coreu.custom.component.CustomComponentType;
import me.udnek.coreu.custom.item.CustomItem;
import me.udnek.coreu.util.LoreBuilder;
import me.udnek.rpgu.component.ComponentTypes;
import me.udnek.rpgu.component.ability.AbilityComponent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.jetbrains.annotations.NotNull;

public interface ActiveAbilityComponent<ActivationContext> extends AbilityComponent<ActivationContext> {

    ActiveAbilityComponent<?> DEFAULT = new ActiveAbilityComponent<>() {
        final CustomComponentMap<AbilityComponent<?>, CustomComponent<AbilityComponent<?>>> components = new CustomComponentMap<>();
        @Override
        public @NotNull CustomComponentMap<AbilityComponent<?>, CustomComponent<AbilityComponent<?>>> getComponents() {return components;}
        @Override
        public void getLore(@NotNull LoreBuilder loreBuilder) {}
    };

    default void onRightClick(@NotNull CustomItem custom.Item, @NotNull PlayerInteractEvent event){}
    default void onStopUsing(@NotNull CustomItem custom.Item, @NotNull PlayerStopUsingItemEvent event){}
    default void onConsume(@NotNull CustomItem custom.Item, @NotNull PlayerItemConsumeEvent event){}


    default @NotNull CustomComponentType<CustomItem, ?> getType(){return ComponentTypes.ACTIVE_ABILITY_ITEM;}
}
