package me.udnek.rpgu.component.ability;

import com.destroystokyo.paper.event.player.PlayerLaunchProjectileEvent;
import io.papermc.paper.event.entity.EntityAttemptSmashAttackEvent;
import io.papermc.paper.event.player.PlayerShieldDisableEvent;
import me.udnek.coreu.custom.item.CustomItem;
import me.udnek.coreu.rpgu.component.ability.active.RPGUItemActiveAbility;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.jetbrains.annotations.NotNull;

public interface RPGUActiveTriggerableAbility<Context> extends RPGUItemActiveAbility<Context> {

    default void onRightClick(@NotNull CustomItem customItem, @NotNull PlayerInteractEvent event) {}
    default void onConsume(@NotNull CustomItem customItem, @NotNull PlayerItemConsumeEvent event) {}
    default void onShoot(@NotNull CustomItem customItem, @NotNull EntityShootBowEvent event) {}
    default void onShieldBreak(@NotNull CustomItem customItem, @NotNull PlayerShieldDisableEvent event) {}
    default void onLaunchTrident(@NotNull CustomItem customItem, @NotNull PlayerLaunchProjectileEvent event) {}
    default void onSmashAttack(@NotNull CustomItem customItem, @NotNull EntityAttemptSmashAttackEvent event) {}
}
