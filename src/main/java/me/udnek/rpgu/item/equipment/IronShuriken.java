/*
package me.udnek.rpgu.item.equipment;

import com.destroystokyo.paper.ParticleBuilder;
import me.udnek.itemscoreu.customitem.ConstructableCustomItem;
import me.udnek.itemscoreu.utils.RightClickable;
import me.udnek.rpgu.item.RpgUCustomItem;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Snowball;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class IronShuriken extends ConstructableCustomItem implements RightClickable, RpgUCustomItem {
    @Override
    public Integer getCustomModelData() {
        return 3100;
    }
    @Override
    public Material getMaterial() {
        return Material.FISHING_ROD;
    }
    @Override
    public String getRawId() {
        return "iron_shuriken";
    }

    @Override
    public void onRightClicks(PlayerInteractEvent event) {
        event.setCancelled(true);

        Snowball projectile = event.getPlayer().launchProjectile(Snowball.class);

        projectile.setItem(event.getItem());
        event.getPlayer().damageItemStack(event.getHand(), 1);
        projectile.setVelocity(projectile.getVelocity().multiply(0.7));
    }


    // TODO: 6/8/2024 MAKE WORKING
    public void onThrowableProjectileHits(ProjectileHitEvent event, ItemStack itemStack) {
        Projectile projectile = event.getEntity();
        projectile.remove();

        ParticleBuilder particleBuilder = new ParticleBuilder(Particle.BLOCK);
        particleBuilder.offset(0.1, 0.1, 0.1);
        particleBuilder.count(8);

        if (event.getHitEntity() != null){
            if (event.getHitEntity() instanceof Damageable){
                ((Damageable) event.getHitEntity()).damage(5);
            }


            Entity entity = event.getEntity();
            particleBuilder.location(entity.getLocation());
            particleBuilder.data(Material.REDSTONE_BLOCK.createBlockData());
            //for (int i = 0; i < 10; i++) {
            //    entity.getLocation().getWorld().spawnParticle(Particle.BLOCK_DUST, entity.getLocation(), 0, 10, 1, 0.1, 10, Material.REDSTONE_BLOCK.createBlockData());
            //}
        }
        else {
            particleBuilder.location(projectile.getLocation());
            //projectile.setVelocity(projectile.getVelocity().multiply(-1));
            //Snowball newProjectile = (Snowball) projectile.getWorld().spawnEntity(projectile.getLocation(), EntityType.SNOWBALL);
            //newProjectile.setItem(itemStack);
            //newProjectile.setVelocity(projectile.getVelocity().multiply(-1));

            particleBuilder.data(event.getHitBlock().getType().createBlockData());
        }


        particleBuilder.spawn();

    }
}
*/
