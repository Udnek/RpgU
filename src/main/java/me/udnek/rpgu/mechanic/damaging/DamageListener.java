package me.udnek.rpgu.mechanic.damaging;

import me.udnek.itemscoreu.util.SelfRegisteringListener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class DamageListener extends SelfRegisteringListener {
    public DamageListener(JavaPlugin plugin) {
        super(plugin);
    }
    @EventHandler
    public void onEntityTakesDamage(EntityDamageEvent event) {
        new DamageEvent(event).invoke();

/*        switch (event.getCause()){
            case FIRE_TICK:
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
                type = Damage.Type.MAGICAL;
                break;
            default:
                type = Damage.Type.PHYSICAL;
        }
        DamageVisualizer.visualize(new Damage(type, event.getFinalDamage()), event.getEntity());
        if (event.getEntity() instanceof LivingEntity livingEntity){
            int finalNoDamageTicks = noDamageTicks;
            new BukkitRunnable() {
                @Override
                public void run() {livingEntity.setNoDamageTicks(finalNoDamageTicks);}
            }.runTaskLater(RpgU.getInstance(), 1);
        }*/

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
