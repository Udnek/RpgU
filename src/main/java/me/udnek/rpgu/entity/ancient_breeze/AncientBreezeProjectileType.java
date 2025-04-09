package me.udnek.rpgu.entity.ancient_breeze;

import me.udnek.itemscoreu.customentitylike.entity.ConstructableCustomEntityType;
import me.udnek.itemscoreu.customentitylike.entity.CustomEntityType;
import me.udnek.rpgu.entity.EntityTypes;
import me.udnek.rpgu.mechanic.damaging.Damage;
import me.udnek.rpgu.mechanic.damaging.DamageUtils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class AncientBreezeProjectileType extends ConstructableCustomEntityType<Snowball> implements Listener {

    @Override
    public @NotNull EntityType getVanillaType() {
        return EntityType.SNOWBALL;
    }

    @Override
    public void load(@NotNull Entity entity) {}

    @Override
    public @NotNull Snowball spawnNewEntity(@NotNull Location location) {
        Snowball snowball = super.spawnNewEntity(location);
        snowball.setSilent(true);
        snowball.setItem(new ItemStack(Material.AIR));
        return snowball;
    }

    @Override
    public void unload(@NotNull Entity entity) {}

    @Override
    public @NotNull String getRawId() {
        return "ancient_breeze_projectile";
    }


    public @Nullable AncientBreezeProjectileType getIfCustom(@NotNull Entity entity) {
        CustomEntityType customEntity = CustomEntityType.get(entity);
        if (customEntity == null || customEntity != EntityTypes.ANCIENT_BREEZE_PROJECTILE) return null;
        return (AncientBreezeProjectileType) customEntity;
    }

    @EventHandler
    public void onEntityHit(ProjectileHitEvent event){
        AncientBreezeProjectileType projectile = getIfCustom(event.getEntity());
        if (projectile ==  null) return;
        if (event.getHitEntity() instanceof LivingEntity livingEntity)
            DamageUtils.damage(livingEntity, new Damage(Damage.Type.PHYSICAL, 10));
        event.getEntity().remove();
    }
}
