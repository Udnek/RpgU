package me.udnek.rpgu.component.ability.property;

import me.udnek.itemscoreu.customcomponent.CustomComponentType;
import me.udnek.rpgu.component.ComponentTypes;
import me.udnek.rpgu.component.ability.AbilityComponent;
import me.udnek.rpgu.lore.ability.AbilityLorePart;
import me.udnek.rpgu.lore.ability.ActiveAbilityLorePart;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class MissUsageCooldownMultiplierProperty implements AbilityProperty<Player, Double> {

    protected double base;

    public MissUsageCooldownMultiplierProperty(@NotNull Double base) {
        this.base = base;
    }

    @Override
    public @NotNull Double getBase() {
        return base;
    }

    @Override
    public @NotNull Double get(@NotNull Player player) {
        return getBase();
    }

    @Override
    public void describe(@NotNull AbilityLorePart componentable) {}

    @Override
    public @NotNull CustomComponentType<AbilityComponent<?>, ?> getType() {
        return ComponentTypes.ABILITY_MISS_USAGE_COOLDOWN_MULTIPLIER;
    }
}
