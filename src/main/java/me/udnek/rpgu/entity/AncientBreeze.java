package me.udnek.rpgu.entity;

import me.udnek.itemscoreu.customentitylike.entity.ConstructableCustomEntity;
import me.udnek.itemscoreu.customentitylike.entity.CustomTickingEntityType;
import org.bukkit.Bukkit;
import org.bukkit.entity.Breeze;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.SpectralArrow;
import org.jetbrains.annotations.NotNull;

public class AncientBreeze extends ConstructableCustomEntity<Breeze> {

    protected int pullCooldown = 0;
    protected int projectileCooldown = 0;

    @Override
    public void delayedTick() {
        LivingEntity target = entity.getTarget();
        if (target == null) return;
        if (projectileCooldown <= 0){
            projectileCooldown = 20;
            entity.launchProjectile(SpectralArrow.class, target.getLocation().subtract(entity.getEyeLocation()).toVector().multiply(0.8));
        }
        if (!entity.isOnGround() && pullCooldown <= 0) {
            pullCooldown = 20;
            target.setVelocity(entity.getLocation().subtract(target.getLocation()).toVector().multiply(0.5));
        }
        pullCooldown -= getTickDelay();
        projectileCooldown -= getTickDelay();
    }

    @Override
    public @NotNull CustomTickingEntityType<?> getType() {
        return EntityTypes.ANCIENT_BREEZE;
    }
}
