package me.udnek.rpgu.entity.ancient_breeze;

import me.udnek.itemscoreu.customentitylike.entity.ConstructableCustomEntity;
import me.udnek.itemscoreu.customentitylike.entity.CustomTickingEntityType;
import me.udnek.rpgu.entity.EntityTypes;
import org.bukkit.entity.Piglin;
import org.jetbrains.annotations.NotNull;

public class AncientBreezeShield extends ConstructableCustomEntity<Piglin> {

    AncientBreeze spawnedBy;

    public void setSpawnedBy(@NotNull AncientBreeze customEntity) {
        spawnedBy = customEntity;
    }

    public void entityDeath() {
        if (spawnedBy != null) spawnedBy.checkAliveShields();
    }

    @Override
    public void delayedTick() {}

    @Override
    public @NotNull CustomTickingEntityType<?> getType() {
        return EntityTypes.ANCIENT_BREEZE_SHIELD;
    }
}
