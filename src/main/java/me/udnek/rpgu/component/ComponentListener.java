package me.udnek.rpgu.component;

import me.udnek.coreu.custom.entitylike.entity.CustomEntityType;
import me.udnek.coreu.util.SelfRegisteringListener;
import me.udnek.rpgu.mechanic.damaging.DamageEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public class ComponentListener extends SelfRegisteringListener {
    public ComponentListener(@NotNull Plugin plugin) {super(plugin);}

    @EventHandler
    public void onEntityDamage(DamageEvent event) {
        CustomEntityType.consumeIfCustom(event.getDamageInstance().getVictim(), customEntityType -> {
            boolean resistantTo = customEntityType.getComponents().getOrDefault(Components.DAMAGE_RESISTANT_ENTITY)
                    .isResistantTo(customEntityType, event.getDamageInstance().getCause());
            if (resistantTo) event.setCancelled(true);
        });
    }
}
