package me.udnek.rpgu.component.ability.property;

import me.udnek.itemscoreu.customcomponent.CustomComponentType;
import me.udnek.itemscoreu.util.Utils;
import me.udnek.rpgu.component.ComponentTypes;
import me.udnek.rpgu.component.ability.ActiveAbilityComponent;
import me.udnek.rpgu.lore.ActiveAbilityLorePart;
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
    public void describe(@NotNull ActiveAbilityLorePart componentable) {
        Utils.consumeIfNotNull(getBase(), value ->
                componentable.addWithFormat(Component.translatable("ability.rpgu.cast_time", Component.text(Utils.roundToTwoDigits((double) value /20)))));
    }

    @Override
    public @NotNull CustomComponentType<ActiveAbilityComponent<?>, ?> getType() {
        return ComponentTypes.ABILITY_CAST_TIME;
    }
}
