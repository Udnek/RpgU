package me.udnek.rpgu.entity;

import me.udnek.itemscoreu.customentitylike.entity.CustomEntityType;
import me.udnek.itemscoreu.customentitylike.entity.CustomTickingEntityType;
import me.udnek.itemscoreu.customregistry.CustomRegistries;
import me.udnek.rpgu.RpgU;
import me.udnek.rpgu.entity.ancient_breeze.*;
import me.udnek.rpgu.entity.totem_of_saving.TotemOfSavingEntity;
import me.udnek.rpgu.entity.totem_of_saving.TotemOfSavingEntityType;
import org.jetbrains.annotations.NotNull;

public class EntityTypes {

    public static final CustomTickingEntityType<TotemOfSavingEntity> TOTEM_OF_SAVING = register(new TotemOfSavingEntityType());

    public static final CustomEntityType DAMAGE_DISPLAY = register(new DamageDisplayEntityType());

    public static final CustomTickingEntityType<AncientBreeze> ANCIENT_BREEZE = register(new AncientBreezeType());
    public static final CustomTickingEntityType<AncientBreezeShield> ANCIENT_BREEZE_SHIELD = register(new AncientBreezeShieldType());
    public static final CustomEntityType ANCIENT_BREEZE_PROJECTILE = register(new AncientBreezeProjectileType());

    private static @NotNull <T extends CustomEntityType> T register(@NotNull T customEntityType){
        return CustomRegistries.ENTITY_TYPE.register(RpgU.getInstance(), customEntityType);
    }

}
