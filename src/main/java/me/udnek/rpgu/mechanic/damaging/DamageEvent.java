package me.udnek.rpgu.mechanic.damaging;

import me.udnek.coreu.custom.event.CustomEvent;
import org.bukkit.entity.Entity;
import org.bukkit.event.Cancellable;
import org.bukkit.event.entity.EntityEvent;
import org.jetbrains.annotations.NotNull;

public class DamageEvent extends CustomEvent implements Cancellable {

    protected DamageInstance damageInstance;
    protected State state;
    protected boolean cancelled;

    public DamageEvent(@NotNull DamageInstance damageInstance){
        this.damageInstance = damageInstance;
        this.state = State.AFTER_ATTACK_CALCULATIONS;
    }

    public @NotNull DamageInstance getDamageInstance() {
        return damageInstance;
    }

    protected void setState(@NotNull State state){
        this.state = state;
    }

    public @NotNull State getState() {
        return state;
    }

    @Override
    public boolean isCancelled() {return cancelled;}

    @Override
    public void setCancelled(boolean cancel) { cancelled = cancel;}

    public enum State{
        AFTER_ATTACK_CALCULATIONS,
        AFTER_EQUIPMENT_ATTACKS,
        AFTER_EQUIPMENT_RECEIVES;
    }
}
