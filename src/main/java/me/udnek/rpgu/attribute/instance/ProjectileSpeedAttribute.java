package me.udnek.rpgu.attribute.instance;

import me.udnek.rpgu.attribute.RpgUAttribute;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;

public class ProjectileSpeedAttribute extends RpgUAttribute implements Listener {
    @Override
    public String getRawId() {
        return "projectile_speed";
    }

    @Override
    public double getDefaultValue() {return 1;}

    @EventHandler
    public void onFire(EntityShootBowEvent event) {
        double amount = this.calculate(event.getEntity());
        if (amount == getDefaultValue()) return;
        Entity projectile = event.getProjectile();
        projectile.setVelocity(projectile.getVelocity().multiply(amount));
    }
}
