package me.udnek.rpgu.component.ability.property;

import me.udnek.itemscoreu.customcomponent.CustomComponentType;
import me.udnek.rpgu.component.ComponentTypes;
import me.udnek.rpgu.component.ability.ActiveAbilityComponent;
import me.udnek.rpgu.lore.ActiveAbilityLorePart;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class MissUsageCooldownMultiplierProperty extends AbstractAbilityProperty<Player, Double>{
    public MissUsageCooldownMultiplierProperty(@NotNull Double base) {
        super(base);
    }

    @Override
    public @NotNull Double get(@NotNull Player player) {
        return getBase();
    }

    @Override
    public void describe(@NotNull ActiveAbilityLorePart componentable) {}

    @Override
    public @NotNull CustomComponentType<ActiveAbilityComponent<?>, ?> getType() {
        return ComponentTypes.ABILITY_MISS_USAGE_COOLDOWN_MULTIPLIER;
    }
}
