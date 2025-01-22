package me.udnek.rpgu.entity;

import me.udnek.itemscoreu.customentitylike.entity.CustomEntityType;
import me.udnek.itemscoreu.customentitylike.entity.CustomTickingEntityType;
import me.udnek.itemscoreu.customregistry.CustomRegistries;
import me.udnek.rpgu.RpgU;
import org.jetbrains.annotations.NotNull;

public class EntityTypes {

    public static final CustomTickingEntityType<TotemOfSavingEntity> TOTEM_OF_SAVING = register(new TotemOfSavingEntityType());

    public static final CustomEntityType DAMAGE_DISPLAY = register(new DamageDisplayEntityType());

    private static @NotNull <T extends CustomEntityType> T register(@NotNull T customEntityType){
        return CustomRegistries.ENTITY_TYPE.register(RpgU.getInstance(), customEntityType);
    }

}
