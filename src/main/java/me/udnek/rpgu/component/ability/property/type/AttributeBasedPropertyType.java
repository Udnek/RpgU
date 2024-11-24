package me.udnek.rpgu.component.ability.property.type;

import me.udnek.itemscoreu.customattribute.CustomAttribute;
import me.udnek.itemscoreu.customcomponent.CustomComponent;
import me.udnek.itemscoreu.util.Utils;
import me.udnek.rpgu.component.ability.ActiveAbilityComponent;
import me.udnek.rpgu.component.ability.property.AttributeBasedProperty;
import me.udnek.rpgu.lore.ActiveAbilityLorePart;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

public class AttributeBasedPropertyType extends SelfDescribedPropertyType<AttributeBasedProperty> {

    protected CustomAttribute attribute;
    protected String translation;
    protected boolean divideValueBy20;

    public AttributeBasedPropertyType(@NotNull String rawId, double defaultValue, @NotNull CustomAttribute attribute, @NotNull String translation, boolean divideValueBy20) {
        super(rawId, new AttributeBasedProperty(defaultValue));
        this.attribute = attribute;
        this.translation = translation;
        this.divideValueBy20 = divideValueBy20;
    }

    public AttributeBasedPropertyType(@NotNull String rawId, double defaultValue, @NotNull CustomAttribute attribute, @NotNull String translation) {
        this(rawId, defaultValue, attribute, translation, false);
    }

    public @NotNull CustomAttribute getAttribute() {
        return attribute;
    }

    @Override
    public void describe(@NotNull AttributeBasedProperty component, @NotNull ActiveAbilityLorePart componentable) {
        double base = component.getBase();
        if (base < attribute.getMinimum()) return;
        if (divideValueBy20) base /= 20d;
        componentable.addWithFormat(Component.translatable(translation, Component.text(Utils.roundToTwoDigits(base))));
    }
}













