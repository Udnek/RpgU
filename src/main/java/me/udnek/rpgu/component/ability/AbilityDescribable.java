package me.udnek.rpgu.component.ability;

import me.udnek.rpgu.lore.ActiveAbilityLorePart;
import org.jetbrains.annotations.NotNull;

public interface AbilityDescribable {
    void describe(@NotNull ActiveAbilityLorePart componentable);
}
