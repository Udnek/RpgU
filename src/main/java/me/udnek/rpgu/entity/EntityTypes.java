package me.udnek.rpgu.entity;

import me.udnek.itemscoreu.customentity.CustomEntityType;
import me.udnek.itemscoreu.customregistry.CustomRegistries;
import me.udnek.rpgu.RpgU;
import org.jetbrains.annotations.NotNull;

public class EntityTypes {

    public static CustomEntityType<TotemOfSavingEntity> TOTEM_OF_SAVING = register(new TotemOfSavingEntityType());

    public static CustomEntityType<DamageDisplayEntity> DAMAGE_DISPLAY = register(new CustomEntityType<DamageDisplayEntity>("damage_display") {
        @Override
        protected @NotNull DamageDisplayEntity getNewCustomEntityClass() {
            return new DamageDisplayEntity();
        }
    });

    private static @NotNull <T extends CustomEntityType<?>> T register(@NotNull T customEntityType){
        return CustomRegistries.ENTITY_TYPE.register(RpgU.getInstance(), customEntityType);
    }

}
