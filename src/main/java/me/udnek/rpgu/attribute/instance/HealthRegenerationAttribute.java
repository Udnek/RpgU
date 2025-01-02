package me.udnek.rpgu.attribute.instance;

import me.udnek.itemscoreu.customattribute.ConstructableCustomAttribute;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.jetbrains.annotations.NotNull;

public class HealthRegenerationAttribute extends ConstructableCustomAttribute implements Listener {
    public HealthRegenerationAttribute(@NotNull String rawId) {
        super(rawId,1, 0, 1024);
    }

    @EventHandler
    public void onHeal(EntityRegainHealthEvent event){
        EntityRegainHealthEvent.RegainReason regainReason = event.getRegainReason();
        if (regainReason != EntityRegainHealthEvent.RegainReason.SATIATED) return;
        if (!(event.getEntity() instanceof LivingEntity livingEntity)) return;
        event.setAmount(calculate(livingEntity));
    }
}
