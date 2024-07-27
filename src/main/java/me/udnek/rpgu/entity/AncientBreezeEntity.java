package me.udnek.rpgu.entity;

import me.udnek.itemscoreu.customentity.ConstructableCustomEntity;
import me.udnek.itemscoreu.utils.LogUtils;
import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.*;
import org.bukkit.util.Vector;

import java.util.Random;

public class AncientBreezeEntity extends ConstructableCustomEntity<Breeze>{
    public static final double SIZE = 1.7;

    @Override
    public EntityType getVanillaEntityType() {return EntityType.BREEZE;}
    @Override
    public void onSpawn() {
        entity.getAttribute(Attribute.GENERIC_SCALE).setBaseValue(SIZE);
    }
    @Override
    public void unload() {}
    @Override
    public void tick() {
        if ((Bukkit.getCurrentTick() % 10) == 0) dash();
        if ((Bukkit.getCurrentTick() % 20) == 0) specialAttack();

    }
    public void dash(){
        LivingEntity target = entity.getTarget();
        if (target == null) return;
        if (target.getLocation().distance(entity.getLocation()) < 13) return;
        entity.setVelocity(target.getLocation().subtract(entity.getLocation()).toVector().normalize());
    }

    public void specialAttack(){
        LivingEntity target = entity.getTarget();
        if (target == null) return;
        Vector direction = target.getLocation().add(0, -SIZE, 0).subtract(entity.getLocation()).toVector().normalize().multiply(2);
        int nextInt = new Random().nextInt(6);
        //LogUtils.log(nextInt);
        Class<? extends Projectile> projectile = switch (nextInt) {
            case 0 -> Fireball.class;
            case 1 -> Arrow.class;
            case 2 -> WindCharge.class;
            case 3 -> WitherSkull.class;
            case 4 -> Trident.class;
            case 5 -> SpectralArrow.class;
            default -> Fireball.class;
        };
        entity.launchProjectile(projectile, direction);
    }

}
