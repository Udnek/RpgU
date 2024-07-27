package me.udnek.rpgu.attribute.instance;

import me.udnek.rpgu.attribute.RpgUAttribute;
import org.bukkit.entity.AbstractArrow;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;

public class ProjectileDamageAttribute extends RpgUAttribute implements Listener {
    @Override
    public String getRawId() {
        return "projectile_damage";
    }

    @Override
    public double getDefaultValue() {return 1;}

    @EventHandler
    public void onFire(EntityShootBowEvent event) {
        double amount = this.calculate(event.getEntity());
        if (amount == getDefaultValue()) return;
        if (!(event.getProjectile() instanceof AbstractArrow arrow)) return;
        arrow.setDamage(arrow.getDamage() * amount);
    }
}
