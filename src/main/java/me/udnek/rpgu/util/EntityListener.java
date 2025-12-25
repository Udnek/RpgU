package me.udnek.rpgu.util;

import me.udnek.coreu.custom.entitylike.entity.CustomEntityType;
import me.udnek.coreu.util.SelfRegisteringListener;
import me.udnek.rpgu.component.ComponentTypes;
import me.udnek.rpgu.mechanic.damaging.DamageEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public class EntityListener extends SelfRegisteringListener {
    public EntityListener(@NotNull Plugin plugin) {super(plugin);}

    @EventHandler
    public void onEntityDamage(DamageEvent event) {
        CustomEntityType.consumeIfCustom(event.getDamageInstance().getVictim(), custom.EntityType -> {
            boolean resistentTo = custom.EntityType.getComponents().getOrDefault(ComponentTypes.DAMAGE_RESISTENT_ENTITY)
                    .isResistentTo(custom.EntityType, event.getDamageInstance().getCause());
            if (resistentTo) event.setCancelled(true);
        });
    }
}
