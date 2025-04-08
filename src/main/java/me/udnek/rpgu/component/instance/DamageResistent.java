package me.udnek.rpgu.component.instance;

import me.udnek.itemscoreu.customcomponent.CustomComponent;
import me.udnek.itemscoreu.customcomponent.CustomComponentType;
import me.udnek.itemscoreu.customentitylike.entity.CustomEntityType;
import me.udnek.rpgu.component.ComponentTypes;
import org.bukkit.event.entity.EntityDamageEvent;
import org.jetbrains.annotations.NotNull;

public interface DamageResistent extends CustomComponent<CustomEntityType> {
    DamageResistent DEFAULT = (customEntityType, damageCause) -> false;
    DamageResistent NO_ENVIRONMENT_DAMAGE = (customEntityType, damageCause) -> {
        switch (damageCause) {
            case FLY_INTO_WALL, FALL, HOT_FLOOR, DROWNING, SUFFOCATION -> {return true;}
            default -> {return false;}
        }
    };

    boolean isResistentTo(@NotNull CustomEntityType customEntityType, @NotNull EntityDamageEvent.DamageCause damageCause);

    @Override
    default @NotNull CustomComponentType<CustomEntityType, ?> getType() {
        return ComponentTypes.DAMAGE_RESISTENT_ENTITY;
    }
}
