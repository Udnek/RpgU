package me.udnek.rpgu.util;

import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.function.Consumer;

public class Utils {
    public static <T extends Number> void consumeIfPositive(@NotNull T t, @NotNull Consumer<T> consumer){
        if (t.doubleValue() > 0) consumer.accept(t);
    }

    public static @NotNull Collection<LivingEntity> livingEntitiesInRadius(@NotNull Location location, double radius){
        return location.getWorld().getNearbyLivingEntities(location, radius, livingEntity -> !(livingEntity.getLocation().distance(location) > radius));
    }
}
