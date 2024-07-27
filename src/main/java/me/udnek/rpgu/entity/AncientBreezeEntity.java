package me.udnek.rpgu.entity;

import me.udnek.itemscoreu.customentity.ConstructableCustomEntity;
import me.udnek.itemscoreu.utils.LogUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.*;
import org.bukkit.util.Vector;

public class AncientBreezeEntity extends ConstructableCustomEntity<Breeze>{

    @Override
    public EntityType getVanillaEntityType() {return EntityType.BREEZE;}

    @Override
    public void onSpawn() {
    }

    @Override
    public void unload() {}

    @Override
    public void tick() {
        if ((Bukkit.getCurrentTick() % 20) != 0) return;
        LivingEntity target = entity.getTarget();
        if (target == null) return;
        Vector direction = target.getLocation().subtract(entity.getLocation()).toVector().normalize();
        entity.launchProjectile(Fireball.class, direction);
    }

}
