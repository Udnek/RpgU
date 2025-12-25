package me.udnek.rpgu.entity.ancient_breeze;

import me.udnek.coreu.custom.entitylike.entity.ConstructableCustomEntityType;
import me.udnek.coreu.custom.entitylike.entity.CustomEntity;
import me.udnek.coreu.custom.entitylike.entity.CustomEntityType;
import me.udnek.coreu.custom.entitylike.entity.CustomTickingEntityType;
import me.udnek.rpgu.component.instance.DamageResistent;
import me.udnek.rpgu.entity.EntityTypes;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Piglin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class AncientBreezeShieldType extends ConstructableCustomEntityType<Piglin> implements CustomTickingEntityType<AncientBreezeShield>,Listener {

    public AncientBreezeShieldType(){
        getComponents().set(DamageResistent.NO_ENVIRONMENT_DAMAGE);
    }

    @EventHandler
    public void onDeath(EntityDeathEvent event) {
        CustomEntity customEntity = CustomEntityType.getTicking(event.getEntity());
        if (customEntity == null || customEntity.getType() != EntityTypes.ANCIENT_BREEZE_SHIELD) return;
        ((AncientBreezeShield) customEntity).entityDeath();
    }

    @Override
    public @NotNull EntityType getVanillaType() {
        return EntityType.PIGLIN;
    }

    @Override
    public @NotNull Piglin spawnNewEntity(@NotNull Location location) {
        Piglin entity = super.spawnNewEntity(location);
        entity.setSilent(true);
        entity.setCollidable(true);
        //entity.setInvisible(true);
        entity.setPersistent(true);
        entity.setRotation(entity.getYaw(), 0);
        EntityEquipment equipment = entity.getEquipment();
        equipment.clear();
        entity.setImmuneToZombification(true);
        entity.setAdult();
        Objects.requireNonNull(entity.getAttribute(Attribute.MAX_HEALTH)).setBaseValue(10);
        entity.setAware(false);
        entity.clearLootTable();
        entity.setRemoveWhenFarAway(false);
        entity.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, PotionEffect.INFINITE_DURATION, 0, false, false, false));
        entity.addPotionEffect(new PotionEffect(PotionEffectType.WATER_BREATHING, PotionEffect.INFINITE_DURATION, 0, true, false, true));
        return entity;
    }

    @Override
    public void load(@NotNull Entity entity) {}

    @Override
    public void unload(@NotNull Entity entity) {}

    @Override
    public @NotNull String getRawId() {
        return "ancient_breeze_shield";
    }

    @Override
    public @NotNull AncientBreezeShield createNewClass() {
        return new AncientBreezeShield();
    }
}
