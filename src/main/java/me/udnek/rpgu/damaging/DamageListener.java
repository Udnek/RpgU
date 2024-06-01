package me.udnek.rpgu.damaging;

import me.udnek.itemscoreu.utils.SelfRegisteringListener;
import me.udnek.rpgu.RpgU;
import me.udnek.rpgu.damaging.visualizer.DamageVisualizer;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.*;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class DamageListener extends SelfRegisteringListener {
    public DamageListener(JavaPlugin plugin) {
        super(plugin);
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        DamageEvent damageEvent = new DamageEvent(event);
        damageEvent.invoke();
    }

    @EventHandler
    public void onEntityTakesDamage(EntityDamageEvent event) {
/*        if (event.getEntity() instanceof LivingEntity){
            new BukkitRunnable() {
                @Override
                public void run() {
                    ((LivingEntity) event.getEntity()).setNoDamageTicks(0);
                }
            }.runTaskLater(RpgU.getInstance(), 1);

        }*/

        Damage.DamageType damageType;
        switch (event.getCause()){
            case ENTITY_ATTACK:
            case ENTITY_SWEEP_ATTACK:
            case ENTITY_EXPLOSION:
            case FALLING_BLOCK:
            case SONIC_BOOM:
            case PROJECTILE:
                return;
            case POISON:
            case MAGIC:
            case WITHER:
                damageType = Damage.DamageType.MAGICAL;
                break;
            default:
                damageType = Damage.DamageType.PHYSICAL;
        }
        DamageVisualizer.visualize(new Damage(damageType, event.getFinalDamage()), event.getEntity());
    }

/*    @EventHandler
    public void onDoubleJump(PlayerToggleFlightEvent event){

        if (!event.isFlying()) return;
        Player player = event.getPlayer();
        if (player.getGameMode() == GameMode.CREATIVE) return;

        event.setCancelled(true);

        if (!player.getLocation().add(0, -1.5, 0).getBlock().isSolid()) return;

        Vector velocity = player.getVelocity().multiply(3).setY(0.75);
        player.setVelocity(velocity);

    }*/

/*    @EventHandler
    public void onPlayerShoots(EntityShootBowEvent event){
        if (!(event.getEntity() instanceof Player)) return;

        Player player = (Player) event.getEntity();
        AbstractArrow projectile = (AbstractArrow) event.getProjectile();
        Random random = new Random();

        double vel = projectile.getVelocity().length();
        double dmg = projectile.getDamage();
        double res = vel*dmg;
        if (projectile.isCritical()){
            res += 1 * (dmg/2 +1);
        }

        player.sendMessage(String.valueOf(res));
        player.sendMessage(String.valueOf(projectile.getDamage()));

        //AbstractArrow projectile = (AbstractArrow) event.getProjectile();

        projectile.setVelocity(projectile.getVelocity().multiply(2f));
        projectile.setDamage(projectile.getDamage() * (1f/2f));

        //player.sendMessage(String.valueOf(projectile.getVelocity().length()*20));
        player.sendMessage(String.valueOf(projectile.getDamage() * projectile.getVelocity().length()));

    }*/
}
