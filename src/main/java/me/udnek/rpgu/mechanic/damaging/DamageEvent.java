package me.udnek.rpgu.mechanic.damaging;

import me.udnek.itemscoreu.customevent.CustomEvent;
import org.jetbrains.annotations.NotNull;

public class DamageEvent extends CustomEvent {

    protected DamageInstance damageInstance;
    protected State state;

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

    public enum State{
        AFTER_ATTACK_CALCULATIONS,
        AFTER_EQUIPMENT_ATTACKS,
        AFTER_EQUIPMENT_RECEIVES;
    }
}
