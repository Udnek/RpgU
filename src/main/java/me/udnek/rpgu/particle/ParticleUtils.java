package me.udnek.rpgu.particle;

import com.destroystokyo.paper.ParticleBuilder;
import com.google.common.base.Preconditions;
import me.udnek.coreu.ItemsCoreU;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.AbstractArrow;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

public class ParticleUtils {

    public static void playUntilGround(@NotNull AbstractArrow arrow, @NotNull ParticleBuilder particle){
        new BukkitRunnable() {
            public void run() {
                if (arrow.isOnGround() || !arrow.isValid()) {
                    cancel();
                }
                particle.location(arrow.getLocation());
                particle.spawn();
            }
        }.runTaskTimer(coreu.getInstance(), 0, 1);
    }
    
    public static void drawLine(@NotNull Particle particle, @NotNull Location from, @NotNull Location to, double space) {
        World world = from.getWorld();
        double distance = from.distance(to);
        Vector pointFrom = from.toVector();
        Vector pointTo = to.toVector();
        Vector vector = pointTo.clone().subtract(pointFrom).normalize().multiply(space);
        for (double length = 0; length < distance; pointFrom.add(vector)) {
            world.spawnParticle(particle, pointFrom.getX(), pointFrom.getY(), pointFrom.getZ(), 1);
            length += space;
        }
    }

    public static void circle(@NotNull ParticleBuilder particleBuilder, double radius) {
        circleWithDensity(particleBuilder, radius, 0.5);
    }

    public static void circleWithDensity(@NotNull ParticleBuilder particleBuilder, double radius, double distanceBetweenParticles) {
        circleWithAngle(particleBuilder, radius, 360d/(2d*Math.PI*radius/distanceBetweenParticles));
    }

    public static void circleWithAngle(@NotNull ParticleBuilder particleBuilder, double radius, double angleDegrees) {
        Location location = particleBuilder.location();
        Preconditions.checkArgument(location != null, "Location must be not null");
        for (double d = 0; d <= 360; d += angleDegrees) {
            Location particleLoc = new Location(location.getWorld(), location.getX(), location.getY(), location.getZ());
            particleLoc.setX(location.getX() + Math.cos(Math.toRadians(d)) * radius);
            particleLoc.setZ(location.getZ() + Math.sin(Math.toRadians(d)) * radius);
            particleBuilder.location(particleLoc);
            particleBuilder.spawn();
        }
    }
}
