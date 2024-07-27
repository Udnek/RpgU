package me.udnek.rpgu.mechanic.rail;

import me.udnek.itemscoreu.utils.LogUtils;
import me.udnek.itemscoreu.utils.SelfRegisteringListener;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Minecart;
import org.bukkit.entity.Player;
import org.bukkit.entity.Vehicle;
import org.bukkit.event.EventHandler;
import org.bukkit.event.vehicle.VehicleCreateEvent;
import org.bukkit.event.vehicle.VehicleEnterEvent;
import org.bukkit.event.vehicle.VehicleExitEvent;
import org.bukkit.event.vehicle.VehicleMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class MinecartListener extends SelfRegisteringListener {

    public static final double DEFAULT_MINECART_SPEED = 0.4;
    public static final double BUFFED_MINECART_SPEED = 0.8;

    public MinecartListener(JavaPlugin plugin) {super(plugin);}

    // TODO: 6/21/2024 REMOVE DEBUG

    @EventHandler
    public void onVehicleMove(VehicleMoveEvent event){

        Vehicle vehicle = event.getVehicle();
        List<Entity> passengers = vehicle.getPassengers();

        if (passengers.isEmpty()) return;
        if (!(passengers.get(0) instanceof Player player)) return;
        //if (!(vehicle instanceof Minecart minecart)) return;

        player.sendMessage(String.valueOf(event.getFrom().distance(event.getTo())));

        /*LogUtils.log("max speed:" + minecart.getMaxSpeed());
        minecart.setMaxSpeed(10);*/

    }
    @EventHandler
    public void onPlayerEntersVehicle(VehicleEnterEvent event){
        LogUtils.log("CALLED VEHICLE ENTER");
        if (!(event.getEntered() instanceof Player player)) return;
        if (!(event.getVehicle() instanceof Minecart minecart)) return;
        minecart.setMaxSpeed(BUFFED_MINECART_SPEED);
    }
    @EventHandler
    public void onPlayerExitsVehicle(VehicleExitEvent event){
        LogUtils.log("CALLED VEHICLE EXIT");
        if (!(event.getExited() instanceof Player player)) return;
        if (!(event.getVehicle() instanceof Minecart minecart)) return;
        minecart.setMaxSpeed(DEFAULT_MINECART_SPEED);
    }


    @EventHandler
    public void onVehicleCreate(VehicleCreateEvent event){
        LogUtils.log("CALLED VEHICLE CREATE");
        Vehicle vehicle = event.getVehicle();
        if (!(vehicle instanceof Minecart minecart)) return;
        LogUtils.log("max speed:" + minecart.getMaxSpeed());
        //minecart.setMaxSpeed(0.8);
    }
}
