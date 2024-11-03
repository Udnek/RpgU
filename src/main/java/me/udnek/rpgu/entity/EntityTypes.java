package me.udnek.rpgu.entity;

import me.udnek.itemscoreu.customentity.CustomEntityType;
import me.udnek.itemscoreu.customregistry.CustomRegistries;
import me.udnek.rpgu.RpgU;

public class EntityTypes {

    public static CustomEntityType<TotemOfSavingEntity> TOTEM_OF_SAVING = register(new TotemOfSavingEntityType());


    private static <T extends CustomEntityType<?>> T register(T customEntityType){
        return (T) CustomRegistries.ENTITY_TYPE.register(RpgU.getInstance(), customEntityType);
    }

}
