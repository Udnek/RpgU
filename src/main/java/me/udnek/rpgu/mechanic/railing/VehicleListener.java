package me.udnek.rpgu.mechanic.railing;

import io.papermc.paper.event.entity.EntityPortalReadyEvent;
import me.udnek.coreu.util.SelfRegisteringListener;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Minecart;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDismountEvent;
import org.bukkit.event.entity.EntityMountEvent;
import org.bukkit.event.vehicle.VehicleMoveEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class VehicleListener extends SelfRegisteringListener {

    public VehicleListener(@NotNull Plugin plugin) {
        super(plugin);
    }


    @EventHandler
    void onVehicleMove(VehicleMoveEvent event){
        final int LOWER_RENDER_DISTANCE = 2;

        List<Entity> passengers = event.getVehicle().getPassengers();
        if (passengers.isEmpty()) return;
        if (!(passengers.getFirst() instanceof Player player)) return;
        Location direction = event.getTo().subtract(event.getFrom());
        double speed = direction.length() * 20;
        if (speed < 48){
//            if (player.getViewDistance() == LOWER_RENDER_DISTANCE){
//                player.setViewDistance(player.getWorld().getViewDistance());
//            }
            return;
        }
        if (player.getViewDistance() > LOWER_RENDER_DISTANCE){
            player.setViewDistance(LOWER_RENDER_DISTANCE);
        }
//        Vector multiplied = direction.toVector().normalize().multiply(16);
//        // forcibly loads chunks ahead
////        for (int i = 2; i <= 4; i++) {
////            Chunk chunk = event.getTo().add(multiplied.clone().multiply(i)).getChunk();
////        }
    }

    @EventHandler
    void onEnter(EntityMountEvent event){
        if (!(event.getMount() instanceof Minecart minecart)) return;
        // removes air friction
        minecart.setFlyingVelocityMod(new Vector(1,1,1));
        // removes ground friction
        minecart.setDerailedVelocityMod(new Vector(1,1,1));
    }

    @EventHandler
    void onExit(EntityDismountEvent event){
        if (!(event.getEntity() instanceof Player player)) return;
        player.setViewDistance(player.getWorld().getViewDistance());
    }

    @EventHandler
    void onChangeDimension(EntityPortalReadyEvent event){
        System.out.println("Entity exited: " + event.getEntity());
        if (!(event.getEntity() instanceof Minecart minecart)) return;
        if (minecart.getVelocity().lengthSquared() != 0) return;
        System.out.println("vel: " + minecart.getVelocity());
        if (minecart.getPassengers().isEmpty()) return;
//        Entity passenger = minecart.getPassengers().getFirst();
//        Vector passengerVelocity = passenger.getVelocity();
//        System.out.println("pass: " + passengerVelocity);
//        new BukkitRunnable() {
//            @Override
//            public void run() {
//                passengerVelocity.normalize();
//                System.out.println("applying: " + passengerVelocity);
//                minecart.setVelocity(passengerVelocity);
//                passenger.setVelocity(passengerVelocity);
//            }
//        }.runTaskLater(RpgU.getInstance(), 20*3);

    }

}
