package me.udnek.rpgu.attribute.instance;

import me.udnek.rpgu.attribute.RpgUAttribute;
import org.bukkit.entity.AbstractArrow;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;

public class ProjectileDamageMultiplierAttribute extends RpgUAttribute implements Listener {
    public ProjectileDamageMultiplierAttribute() {
        super("projectile_damage_multiplier",1, 0, 100);
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
