package me.udnek.rpgu.component.ability;

import com.destroystokyo.paper.ParticleBuilder;
import me.udnek.rpgu.component.ComponentTypes;
import me.udnek.rpgu.particle.ParticleUtils;
import me.udnek.rpgu.util.Utils;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.RayTraceResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

public interface RayTraceActiveAbility<Context> extends ActiveAbilityComponent<Context> {

    default @Nullable Collection<LivingEntity> findLivingEntitiesInRayTraceRadius(@NotNull Player player, @Nullable ParticleBuilder particle){
        RayTraceResult rayTraceResult = Utils.rayTraceBlockOrEntity(player, getComponents().getOrException(ComponentTypes.ABILITY_CAST_RANGE).get(player));
        if (rayTraceResult == null) return null;
        Location location = rayTraceResult.getHitPosition().toLocation(player.getWorld());
        final double radius = getComponents().getOrException(ComponentTypes.ABILITY_AREA_OF_EFFECT).get(player);
        Collection<LivingEntity> nearbyLivingEntities = Utils.livingEntitiesInRadius(location, radius);
        if (particle != null) showRadius(particle.location(location), radius);
        return nearbyLivingEntities;
    }

    default @Nullable Collection<LivingEntity> findLivingEntitiesInRayTraceRadius(@NotNull Player player){
        return findLivingEntitiesInRayTraceRadius(player, null);
    }

    default void showRadius(@NotNull ParticleBuilder particleBuilder, double size){
        ParticleUtils.circle(particleBuilder, size);
    }
}
