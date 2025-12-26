package me.udnek.rpgu.component.ability;

import me.udnek.coreu.custom.item.CustomItem;
import me.udnek.coreu.rpgu.component.ability.active.RPGUItemActiveAbility;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.jetbrains.annotations.NotNull;

public interface RPGUActiveTriggerableAbility<Context> extends RPGUItemActiveAbility<Context> {

    default void onRightClick(@NotNull CustomItem customItem, @NotNull PlayerInteractEvent event) {}
    default void onConsume(@NotNull CustomItem customItem, @NotNull PlayerItemConsumeEvent event) {}
}
