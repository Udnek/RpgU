package me.udnek.rpgu.component.instance;

import me.udnek.coreu.custom.component.CustomComponent;
import me.udnek.coreu.custom.component.CustomComponentType;
import me.udnek.coreu.custom.entitylike.entity.CustomEntityType;
import me.udnek.rpgu.component.ComponentTypes;
import org.bukkit.event.entity.EntityDamageEvent;
import org.jetbrains.annotations.NotNull;

public interface DamageResistent extends CustomComponent<CustomEntityType> {
    DamageResistent DEFAULT = (custom.EntityType, damageCause) -> false;
    DamageResistent NO_ENVIRONMENT_DAMAGE = (custom.EntityType, damageCause) -> {
        switch (damageCause) {
            case FLY_INTO_WALL, FALL, HOT_FLOOR, DROWNING, SUFFOCATION -> {return true;}
            default -> {return false;}
        }
    };

    boolean isResistentTo(@NotNull CustomEntityType custom.EntityType, @NotNull EntityDamageEvent.DamageCause damageCause);

    @Override
    default @NotNull CustomComponentType<CustomEntityType, ?> getType() {
        return ComponentTypes.DAMAGE_RESISTENT_ENTITY;
    }
}
