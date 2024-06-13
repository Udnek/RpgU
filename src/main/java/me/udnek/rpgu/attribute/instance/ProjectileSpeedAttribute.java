package me.udnek.rpgu.attribute.instance;

import me.udnek.itemscoreu.customevent.AllEventListener;
import me.udnek.rpgu.attribute.RpgUAttribute;
import org.bukkit.entity.Entity;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityShootBowEvent;

public class ProjectileSpeedAttribute extends RpgUAttribute implements AllEventListener {
    @Override
    public String getRawId() {
        return "projectile_speed";
    }

    @Override
    public double getDefaultValue() {return 1;}

    @Override
    public void onBukkitEvent(Event event) {
        if (!(event instanceof EntityShootBowEvent shootEvent)) return;
        double amount = this.calculate(shootEvent.getEntity());
        if (amount == getDefaultValue()) return;
        Entity projectile = shootEvent.getProjectile();
        projectile.setVelocity(projectile.getVelocity().multiply(amount));
    }
}
