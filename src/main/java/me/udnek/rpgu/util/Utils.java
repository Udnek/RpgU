package me.udnek.rpgu.util;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.function.Consumer;

public class Utils {
    public static <T extends Number> void consumeIfPositive(@NotNull T t, @NotNull Consumer<T> consumer){
        if (t.doubleValue() > 0) consumer.accept(t);
    }

    public static @Nullable RayTraceResult rayTraceBlockOrEntity(@NotNull Player player, double castRange){
        return rayTraceBlockOrEntity(player, castRange, 0);
    }

    public static @Nullable RayTraceResult rayTraceBlockOrEntity(@NotNull Player player, double castRange, double raySize){
        Location location = player.getEyeLocation();
        World world = player.getWorld();
        RayTraceResult rayTraceResult = null;
        RayTraceResult rayTraceResultBlocks = world.rayTraceBlocks(location, location.getDirection(), castRange);

        if (rayTraceResultBlocks != null) {rayTraceResult = rayTraceResultBlocks;}
        else if (world.rayTraceEntities(location, location.getDirection(), castRange, raySize) != null) {
            rayTraceResult = world.rayTraceEntities(location, location.getDirection(), castRange, raySize);
        }

        return rayTraceResult;
    }

    public static @NotNull Collection<LivingEntity> livingEntitiesInRadius(@NotNull Location location, double radius){
        return location.getWorld().getNearbyLivingEntities(location, radius, livingEntity -> !(livingEntity.getLocation().distance(location) > radius));
    }

    public static @Nullable Location rayTraceBlockUnder(@NotNull Player player){
        return rayTraceBlockUnder(player.getLocation());
    }
    public static @Nullable Location rayTraceBlockUnder(@NotNull Location location){
        World world = location.getWorld();
        RayTraceResult rayTraceResult = world.rayTraceBlocks(location.add(0 , 1, 0), new Vector().setY(-1), 10000);

        if (rayTraceResult == null) return null;
        return rayTraceResult.getHitBlock().getLocation();
    }
}
