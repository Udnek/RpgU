package me.udnek.rpgu.entity;

import me.udnek.itemscoreu.customentity.CustomEntity;
import me.udnek.itemscoreu.customentity.CustomEntityType;
import me.udnek.itemscoreu.customregistry.CustomRegistries;
import me.udnek.rpgu.RpgU;

public class EntityTypes {

    public static CustomEntityType ANCIENT_BREEZE = register(new CustomEntityType("ancient_breeze") {
        @Override
        protected CustomEntity getNewCustomEntityClass() {
            return new AncientBreezeEntity();
        }
    });


    private static CustomEntityType register(CustomEntityType customEntityType){
        return CustomRegistries.ENTITY_TYPE.register(RpgU.getInstance(), customEntityType);
    }
}
