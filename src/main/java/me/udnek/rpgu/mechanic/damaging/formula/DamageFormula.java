package me.udnek.rpgu.mechanic.damaging.formula;

import me.udnek.rpgu.mechanic.damaging.Damage;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public interface DamageFormula<Context> {
    @NotNull Damage calculate(@NotNull Context context);
    void description(@NotNull Consumer<@NotNull Component> consumer);
}
