package me.udnek.rpgu.attribute.instance;

import me.udnek.itemscoreu.customattribute.ConstructableCustomAttribute;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.jetbrains.annotations.NotNull;

public class HealthRegenerationAttribute extends ConstructableCustomAttribute implements Listener {

    public HealthRegenerationAttribute(@NotNull String rawId, double defaultValue, double min, double max) {
        super(rawId, defaultValue, min, max);
    }

    @EventHandler
    public void onRegeneration(EntityRegainHealthEvent event){
        EntityRegainHealthEvent.RegainReason regainReason = event.getRegainReason();
        if (!(event.getEntity() instanceof LivingEntity entity)) return;
        switch (regainReason) {
            case SATIATED -> event.setAmount(calculate(entity));
            case MAGIC_REGEN, REGEN -> event.setAmount(event.getAmount() + calculate(entity));
        }
    }
}
