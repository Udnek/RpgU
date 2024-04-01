package me.udnek.rpgu.particle;

import com.destroystokyo.paper.ParticleBuilder;
import me.udnek.itemscoreu.ItemsCoreU;
import org.bukkit.entity.AbstractArrow;
import org.bukkit.scheduler.BukkitRunnable;

public class BowParticles {

    public static void playParticleUntilGround(AbstractArrow arrow, ParticleBuilder particleBuilder){
        (new BukkitRunnable() {
            public void run() {
                if (arrow.isOnGround() || !arrow.isValid()) {
                    cancel();
                }
                particleBuilder.location(arrow.getLocation());
                particleBuilder.spawn();
            }
        }).runTaskTimer(ItemsCoreU.getInstance(), 0, 1);
    }
}
