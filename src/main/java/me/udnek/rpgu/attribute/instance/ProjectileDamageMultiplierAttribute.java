package me.udnek.rpgu.attribute.instance;

import me.udnek.itemscoreu.customattribute.ConstructableCustomAttribute;
import org.bukkit.entity.AbstractArrow;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.jetbrains.annotations.NotNull;

public class ProjectileDamageMultiplierAttribute extends ConstructableCustomAttribute implements Listener {
    public ProjectileDamageMultiplierAttribute(@NotNull String rawId) {
        super(rawId,1, 0, 100);
    }

    @EventHandler
    public void onFire(EntityShootBowEvent event) {
        double amount = this.calculate(event.getEntity());
        if (amount == getDefaultValue()) return;
        if (!(event.getProjectile() instanceof AbstractArrow arrow)) return;
        arrow.setDamage(arrow.getDamage() * amount);
    }
}
