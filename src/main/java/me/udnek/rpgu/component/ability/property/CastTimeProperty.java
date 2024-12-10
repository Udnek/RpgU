package me.udnek.rpgu.component.ability.property;

import me.udnek.itemscoreu.customcomponent.CustomComponentType;
import me.udnek.itemscoreu.util.Utils;
import me.udnek.rpgu.component.ComponentTypes;
import me.udnek.rpgu.component.ability.AbilityComponent;
import me.udnek.rpgu.lore.ability.AbilityLorePart;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CastTimeProperty extends AbstractAbilityProperty<Player, Integer> {

    public CastTimeProperty(int time){
        super(time);
    }

    @Override
    public @NotNull Integer get(@NotNull Player player) {
        return getBase();
    }

    @Override
    public void describe(@NotNull AbilityLorePart componentable) {
        Utils.consumeIfNotNull(getBase(), value ->
                componentable.addAbilityStat(Component.translatable("ability.rpgu.cast_time", Component.text(Utils.roundToTwoDigits(value/20d)))));
    }

    @Override
    public @NotNull CustomComponentType<AbilityComponent<?>, ?> getType() {
        return ComponentTypes.ABILITY_CAST_TIME;
    }
}
