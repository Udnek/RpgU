package me.udnek.rpgu.item.abstracts;

import me.udnek.rpgu.damaging.DamageEvent;
import org.bukkit.entity.LivingEntity;

public interface WeaponItem {
    default void onEntityAttacks(LivingEntity entity, DamageEvent event){}
}
