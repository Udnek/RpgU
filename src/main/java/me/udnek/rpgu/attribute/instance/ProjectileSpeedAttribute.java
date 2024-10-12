package me.udnek.rpgu.attribute.instance;

import me.udnek.rpgu.attribute.RpgUAttribute;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;

public class ProjectileSpeedAttribute extends RpgUAttribute implements Listener {
    public ProjectileSpeedAttribute() {
        super("projectile_speed", 1, 0, 100);
    }

    @EventHandler
    public void onFire(EntityShootBowEvent event) {
        double amount = this.calculate(event.getEntity());
        if (amount == getDefaultValue()) return;
        Entity projectile = event.getProjectile();
        projectile.setVelocity(projectile.getVelocity().multiply(amount));
    }
}
