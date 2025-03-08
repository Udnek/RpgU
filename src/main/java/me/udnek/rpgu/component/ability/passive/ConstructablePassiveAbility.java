package me.udnek.rpgu.component.ability.passive;

import me.udnek.itemscoreu.customcomponent.CustomComponent;
import me.udnek.itemscoreu.util.LoreBuilder;
import me.udnek.rpgu.component.AbilityActivationHandler;
import me.udnek.rpgu.component.ability.AbilityComponent;
import me.udnek.rpgu.component.ability.property.AbilityProperty;
import me.udnek.rpgu.lore.AttributesLorePart;
import me.udnek.rpgu.lore.ability.PassiveAbilityLorePart;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

public abstract class ConstructablePassiveAbility<ActivationContext> extends AbilityActivationHandler<ActivationContext> implements PassiveAbility {


    public void addLoreLines(@NotNull PassiveAbilityLorePart componentable){
        for (CustomComponent<AbilityComponent<?>> component : getComponents()) {
            if (component instanceof AbilityProperty<?,?> abilityProperty){
                abilityProperty.describe(componentable);
            }
        }
    }

    @Override
    public void getLore(@NotNull LoreBuilder loreBuilder){
        LoreBuilder.Componentable componentable = loreBuilder.get(LoreBuilder.Position.ATTRIBUTES);
        PassiveAbilityLorePart lorePart;
        if (!(componentable instanceof AttributesLorePart attributesLorePart)){
            AttributesLorePart newPart = new AttributesLorePart();
            loreBuilder.set(LoreBuilder.Position.ATTRIBUTES, newPart);
            lorePart = newPart;
        } else {
            lorePart = attributesLorePart;
        }

        lorePart.setEquipmentSlot(getSlot());
        lorePart.setHeader(Component.translatable("passive_ability.rpgu.title"));
        addLoreLines(lorePart);
    }
}
