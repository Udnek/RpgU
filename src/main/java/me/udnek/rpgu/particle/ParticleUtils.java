package me.udnek.rpgu.particle;

import com.destroystokyo.paper.ParticleBuilder;
import me.udnek.itemscoreu.ItemsCoreU;
import org.bukkit.Location;
import org.bukkit.entity.AbstractArrow;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

public class ParticleUtils {

    public static void playParticleUntilGround(AbstractArrow arrow, ParticleBuilder particleBuilder){
        new BukkitRunnable() {
            public void run() {
                if (arrow.isOnGround() || !arrow.isValid()) {
                    cancel();
                }
                particleBuilder.location(arrow.getLocation());
                particleBuilder.spawn();
            }
        }.runTaskTimer(ItemsCoreU.getInstance(), 0, 1);
    }

        public static void summonCircle(@NotNull ParticleBuilder particleBuilder, double size) {
        Location location = particleBuilder.location();
        if (location == null) return;
        for (int d = 0; d <= 90; d += 1) {
            Location particleLoc = new Location(location.getWorld(), location.getX(), location.getY(), location.getZ());
            particleLoc.setX(location.getX() + Math.cos(d) * size);
            particleLoc.setZ(location.getZ() + Math.sin(d) * size);
            particleBuilder.location(particleLoc);
            particleBuilder.spawn();
        }
    }
}
