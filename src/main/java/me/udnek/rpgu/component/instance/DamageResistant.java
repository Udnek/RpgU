package me.udnek.rpgu.component.instance;

import me.udnek.coreu.custom.component.CustomComponent;
import me.udnek.coreu.custom.component.CustomComponentType;
import me.udnek.coreu.custom.entitylike.entity.CustomEntityType;
import me.udnek.rpgu.component.Components;
import org.bukkit.event.entity.EntityDamageEvent;
import org.jetbrains.annotations.NotNull;

public interface DamageResistant extends CustomComponent<CustomEntityType> {
    DamageResistant DEFAULT = (customEntityType, damageCause) -> false;
    DamageResistant NO_ENVIRONMENT_DAMAGE = (customEntityType, damageCause) -> {
        switch (damageCause) {
            case FLY_INTO_WALL, FALL, HOT_FLOOR, DROWNING, SUFFOCATION -> {return true;}
            default -> {return false;}
        }
    };

    boolean isResistantTo(@NotNull CustomEntityType customEntityType, @NotNull EntityDamageEvent.DamageCause damageCause);

    @Override
    default @NotNull CustomComponentType<CustomEntityType, ?> getType() {
        return Components.DAMAGE_RESISTANT_ENTITY;
    }
}
