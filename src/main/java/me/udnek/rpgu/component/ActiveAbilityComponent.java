package me.udnek.rpgu.component;

import io.papermc.paper.event.player.PlayerStopUsingItemEvent;
import me.udnek.itemscoreu.customcomponent.CustomComponent;
import me.udnek.itemscoreu.customcomponent.CustomComponentType;
import me.udnek.itemscoreu.customitem.CustomItem;
import me.udnek.itemscoreu.util.LoreBuilder;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.jetbrains.annotations.NotNull;

public interface ActiveAbilityComponent<ActivationContext> extends CustomComponent<CustomItem> {

    ActiveAbilityComponent<?> DEFAULT = new ActiveAbilityComponent<>() {
        @Override
        public void getLore(@NotNull LoreBuilder loreBuilder) {}
        @Override
        public void onRightClick(@NotNull CustomItem customItem, @NotNull PlayerInteractEvent event) {}
        @Override
        public void onStopUsing(@NotNull CustomItem customItem, @NotNull PlayerStopUsingItemEvent event) {}
        @Override
        public void activate(@NotNull CustomItem customItem, @NotNull Player player, @NotNull Object o) {}
    };

    void getLore(@NotNull LoreBuilder loreBuilder);

    void onRightClick(@NotNull CustomItem customItem, @NotNull PlayerInteractEvent event);
    void onStopUsing(@NotNull CustomItem customItem, @NotNull PlayerStopUsingItemEvent event);

    void activate(@NotNull CustomItem customItem, @NotNull Player player, @NotNull ActivationContext activationContext);

    default @NotNull CustomComponentType<CustomItem, ?> getType(){return ComponentTypes.ACTIVE_ABILITY_ITEM;}
}
