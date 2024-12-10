package me.udnek.rpgu.component.ability.active;

import me.udnek.itemscoreu.customcomponent.CustomComponent;
import me.udnek.itemscoreu.util.LoreBuilder;
import me.udnek.rpgu.component.ability.AbilityComponent;
import me.udnek.rpgu.component.ability.AbstractAbilityComponent;
import me.udnek.rpgu.component.ability.property.AbilityProperty;
import me.udnek.rpgu.lore.ability.ActiveAbilityLorePart;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.jetbrains.annotations.NotNull;

public abstract class ConstructableActiveAbilityComponent<ActivationContext> extends AbstractAbilityComponent<ActivationContext> implements ActiveAbilityComponent<ActivationContext>{

    public void addLoreLines(@NotNull ActiveAbilityLorePart componentable){
        for (CustomComponent<AbilityComponent<?>> component : getComponents()) {
            if (component instanceof AbilityProperty<?,?> abilityProperty){
                abilityProperty.describe(componentable);
            }
        }
    }

    @Override
    public void getLore(@NotNull LoreBuilder loreBuilder){
        ActiveAbilityLorePart lorePart = new ActiveAbilityLorePart();
        loreBuilder.set(55, lorePart);
        lorePart.addEmptyAboveHeader();
        lorePart.setHeader(Component.translatable("active_ability.rpgu.title"));
        addLoreLines(lorePart);
    }
}
