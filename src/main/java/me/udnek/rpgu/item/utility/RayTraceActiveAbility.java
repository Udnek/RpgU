package me.udnek.rpgu.item.utility;

import com.destroystokyo.paper.ParticleBuilder;
import me.udnek.rpgu.component.ComponentTypes;
import me.udnek.rpgu.component.ability.ConstructableActiveAbilityComponent;
import me.udnek.rpgu.particle.ParticleUtils;
import me.udnek.rpgu.util.Utils;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.RayTraceResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

public abstract class RayTraceActiveAbility extends ConstructableActiveAbilityComponent<PlayerInteractEvent> {

    public @Nullable Collection<LivingEntity> findLivingEntitiesInRayTraceRadius(@NotNull Player player, @NotNull ParticleBuilder particleBuilder, double angle){
        RayTraceResult rayTraceResult = Utils.rayTraceBlockOrEntity(player, getComponents().get(ComponentTypes.ABILITY_CAST_RANGE).get(player));
        if (rayTraceResult == null) return null;
        Location location = rayTraceResult.getHitPosition().toLocation(player.getWorld());
        final double radius = getComponents().get(ComponentTypes.ABILITY_AREA_OF_EFFECT).get(player);
        Collection<LivingEntity> nearbyLivingEntities = Utils.livingEntitiesInRadius(location, radius);
        showRadius(particleBuilder.location(location), radius, angle);

        return nearbyLivingEntities;
    }

    public void showRadius(@NotNull ParticleBuilder particleBuilder, double size, double angle){
        ParticleUtils.circle(particleBuilder, size, angle);
    }
}
