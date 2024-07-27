package me.udnek.rpgu.attribute.instance;

import me.udnek.itemscoreu.customevent.AllEventListener;
import me.udnek.rpgu.attribute.RpgUAttribute;
import org.bukkit.entity.AbstractArrow;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityShootBowEvent;

public class ProjectileDamageAttribute extends RpgUAttribute implements AllEventListener {
    @Override
    public String getRawId() {
        return "projectile_damage";
    }

    @Override
    public double getDefaultValue() {return 1;}

    @Override
    public void onEvent(Event event) {
        if (!(event instanceof EntityShootBowEvent shootEvent)) return;
        double amount = this.calculate(shootEvent.getEntity());
        if (amount == getDefaultValue()) return;
        if (!(shootEvent.getProjectile() instanceof AbstractArrow arrow)) return;
        arrow.setDamage(arrow.getDamage() * amount);
    }
}
