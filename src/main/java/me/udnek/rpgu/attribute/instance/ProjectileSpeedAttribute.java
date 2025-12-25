package me.udnek.rpgu.attribute.instance;

import me.udnek.coreu.custom.attribute.ConstructableCustomAttribute;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.jetbrains.annotations.NotNull;

public class ProjectileSpeedAttribute extends ConstructableCustomAttribute implements Listener {
    public ProjectileSpeedAttribute(@NotNull String rawId, double defaultValue, double min, double max) {
        super(rawId, defaultValue, min, max);
    }

    @EventHandler
    public void onFire(EntityShootBowEvent event) {
        double amount = calculate(event.getEntity());
        Entity projectile = event.getProjectile();
        projectile.setVelocity(projectile.getVelocity().multiply(amount));
    }
}
