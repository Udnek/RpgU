package me.udnek.rpgu.component.ability.vanilla;

import me.udnek.coreu.custom.component.CustomComponent;
import me.udnek.coreu.custom.component.CustomComponentType;
import me.udnek.coreu.rpgu.component.RPGUActiveItem;
import me.udnek.rpgu.component.ability.VanillaAbilities;
import net.kyori.adventure.text.Component;
import org.apache.logging.log4j.util.TriConsumer;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CrossbowAbility extends BowAbility {

    public static final CrossbowAbility DEFAULT = new CrossbowAbility();

    @Override
    public void getEngAndRuProperties(TriConsumer<String, String, List<Component>> Eng_Ru_Args) {
        Eng_Ru_Args.accept("Damage when shot: %s", "Урон при выстреле: %s", List.of(Component.text(6)));
    }

    @Override
    public @NotNull CustomComponentType<? super RPGUActiveItem, ? extends CustomComponent<? super RPGUActiveItem>> getType() {
        return VanillaAbilities.CROSSBOW;
    }
}
