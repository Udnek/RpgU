package me.udnek.rpgu.util;

import com.google.common.base.Preconditions;
import me.udnek.rpgu.equipment.slot.UniversalInventorySlot;
import org.bukkit.FluidCollisionMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class Utils {
    public static <T extends Number> void consumeIfPositive(@NotNull T t, @NotNull Consumer<T> consumer){
        if (t.doubleValue() > 0) consumer.accept(t);
    }

    // RAYTRACE
    public static @Nullable RayTraceResult rayTraceBlockOrEntity(@NotNull LivingEntity livingEntity, double castRange){
        return rayTraceBlockOrEntity(livingEntity, castRange, 0);
    }
    public static @Nullable RayTraceResult rayTraceBlockOrEntity(@NotNull LivingEntity livingEntity, double castRange, double raySize){
        Location location = livingEntity.getEyeLocation();
        World world = livingEntity.getWorld();

        RayTraceResult rayTraceResultBlocks = world.rayTraceBlocks(location, location.getDirection(), castRange, FluidCollisionMode.NEVER, true);

        if (rayTraceResultBlocks != null) return rayTraceResultBlocks;
        return world.rayTraceEntities(location, location.getDirection(), castRange, raySize, entity -> entity != livingEntity);
    }
    public static @Nullable Location rayTraceBlockUnder(@NotNull LivingEntity livingEntity){
        return rayTraceBlockUnder(livingEntity.getLocation());
    }
    public static @Nullable Location rayTraceBlockUnder(@NotNull Location location){
        World world = location.getWorld();
        RayTraceResult rayTraceResult = world.rayTraceBlocks(location.add(0 , 1, 0), new Vector().setY(-1), 10000, FluidCollisionMode.NEVER, true);

        if (rayTraceResult == null) return null;
        return rayTraceResult.getHitPosition().toLocation(location.getWorld());
    }

    // NEARBY
    public static @NotNull Collection<LivingEntity> livingEntitiesInRadius(@NotNull Location location, double radius){
        return location.getWorld().getNearbyLivingEntities(location, radius, livingEntity -> livingEntity.getLocation().distance(location) <= radius);
    }
    public static @NotNull Collection<LivingEntity> livingEntitiesInRadiusIntersects(@NotNull Location location, double radius){
        return location.getWorld().getNearbyLivingEntities(location, radius+15,
                entity -> entity.getBoundingBox().expand(radius).contains(location.toVector()));
    }


    public static @NotNull Material replaceSufix(@NotNull Material material, @NotNull String string){
        String key = material.getKey().getKey();
        String name = (key.substring(0, key.lastIndexOf("_")) + string).toUpperCase();
        return Preconditions.checkNotNull(Material.getMaterial(name), "There is no such material", name);
    }

    public static @NotNull Material replacePrefix(@NotNull Material material, @NotNull String string){
        String key = material.getKey().getKey();
        String name = (string + key.substring(key.indexOf("_") + 1)).toUpperCase();
        return Preconditions.checkNotNull(Material.getMaterial(name), "There is no such material", name);
    }

    public static void iterateThroughAllSlots(@NotNull BiConsumer<UniversalInventorySlot, ItemStack> consumer, @NotNull LivingEntity entity){
        if (entity instanceof InventoryHolder inventoryHolder){
            @Nullable ItemStack[] contents = inventoryHolder.getInventory().getContents();
            for (int i = 0; i < contents.length; i++) {
                ItemStack content = contents[i];
                consumer.accept(new UniversalInventorySlot(i), content);
            }
        }else {
            EntityEquipment equipment = entity.getEquipment();
            if (equipment == null) return;
            consumer.accept(new UniversalInventorySlot(EquipmentSlot.HAND), equipment.getItemInMainHand());
            consumer.accept(new UniversalInventorySlot(EquipmentSlot.OFF_HAND), equipment.getItemInOffHand());
            consumer.accept(new UniversalInventorySlot(EquipmentSlot.HEAD), equipment.getHelmet());
            consumer.accept(new UniversalInventorySlot(EquipmentSlot.CHEST), equipment.getChestplate());
            consumer.accept(new UniversalInventorySlot(EquipmentSlot.LEGS), equipment.getLeggings());
            consumer.accept(new UniversalInventorySlot(EquipmentSlot.FEET), equipment.getBoots());
        }
    }
}
