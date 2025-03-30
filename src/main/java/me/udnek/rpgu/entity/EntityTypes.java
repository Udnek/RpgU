package me.udnek.rpgu.entity;

import me.udnek.itemscoreu.customentitylike.entity.ConstructableCustomEntityType;
import me.udnek.itemscoreu.customentitylike.entity.CustomEntityType;
import me.udnek.itemscoreu.customentitylike.entity.CustomTickingEntityType;
import me.udnek.itemscoreu.customregistry.CustomRegistries;
import me.udnek.rpgu.RpgU;
import org.bukkit.entity.Breeze;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.jetbrains.annotations.NotNull;

public class EntityTypes {

    public static final CustomTickingEntityType<TotemOfSavingEntity> TOTEM_OF_SAVING = register(new TotemOfSavingEntityType());

    public static final CustomEntityType DAMAGE_DISPLAY = register(new DamageDisplayEntityType());

    public static final CustomTickingEntityType<AncientBreeze> ANCIENT_BREEZE = register(new AncientBreezeType());

    private static @NotNull <T extends CustomEntityType> T register(@NotNull T customEntityType){
        return CustomRegistries.ENTITY_TYPE.register(RpgU.getInstance(), customEntityType);
    }

}
