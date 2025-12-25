package me.udnek.rpgu.entity.ancient_breeze;

import io.papermc.paper.event.entity.EntityMoveEvent;
import io.papermc.paper.event.player.PlayerNameEntityEvent;
import me.udnek.coreu.custom.entitylike.entity.ConstructableCustomEntityType;
import me.udnek.coreu.custom.entitylike.entity.CustomEntity;
import me.udnek.coreu.custom.entitylike.entity.CustomEntityType;
import me.udnek.coreu.custom.entitylike.entity.CustomTickingEntityType;
import me.udnek.rpgu.entity.EntityTypes;
import me.udnek.rpgu.mechanic.damaging.Damage;
import me.udnek.rpgu.mechanic.damaging.DamageEvent;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.loot.LootTables;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class AncientBreezeType extends ConstructableCustomEntityType<Breeze> implements CustomTickingEntityType<AncientBreeze>, Listener {

    @Override
    public @NotNull EntityType getVanillaType() {
        return EntityType.BREEZE;
    }

    @Override
    public void load(@NotNull Entity entity) {

    }

    @Override
    public @NotNull Breeze spawnNewEntity(@NotNull Location location) {
        Breeze breeze = super.spawnNewEntity(location);
        breeze.custom.Name(Component.translatable("entity.rpgu.ancient_breeze"));
        breeze.setCustomNameVisible(false);
        Objects.requireNonNull(breeze.getAttribute(Attribute.MAX_HEALTH)).setBaseValue(AncientBreeze.ANCIENT_BREEZE_HP);
        breeze.heal(AncientBreeze.ANCIENT_BREEZE_HP);
        EntityEquipment equipment = breeze.getEquipment();
        equipment.clear();
        breeze.setLootTable(LootTables.BEE.getLootTable());
        breeze.setRemoveWhenFarAway(false);
        return breeze;
    }

    @Override
    public void unload(@NotNull Entity entity) {

    }

    @Override
    public @NotNull String getRawId() {
        return "ancient_breeze";
    }

    @Override
    public @NotNull AncientBreeze createNewClass() {
        return new AncientBreeze();
    }

    public @Nullable AncientBreeze getIfCustom(@NotNull Entity entity) {
        CustomEntity customEntity = CustomEntityType.getTicking(entity);
        if (customEntity == null || customEntity.getType() != EntityTypes.ANCIENT_BREEZE) return null;
        return (AncientBreeze) customEntity;
    }

    @EventHandler
    public void onEntityHit(DamageEvent event){
        AncientBreeze breeze = getIfCustom(event.getDamageInstance().getVictim());
        if (breeze ==  null) return;
        breeze.onEntityHit(event);
    }

    @EventHandler
    public void onEntityRename(PlayerNameEntityEvent event){
        AncientBreeze breeze = getIfCustom(event.getEntity());
        if (breeze ==  null) return;
        breeze.onEntityRename(event);
    }

    @EventHandler
    public void onEntityMove(EntityMoveEvent event){
        AncientBreeze breeze = getIfCustom(event.getEntity());
        if (breeze ==  null) return;
        breeze.onEntityMove(event);
    }

    @EventHandler
    public void onEntityLaunchProjectile(ProjectileLaunchEvent event){
        if (!(event.getEntity().getShooter() instanceof LivingEntity livingEntity)) return;
        AncientBreeze breeze = getIfCustom(livingEntity);
        if (breeze == null) return;
        event.setCancelled(true);
        breeze.onEntityShoot();
    }
}
