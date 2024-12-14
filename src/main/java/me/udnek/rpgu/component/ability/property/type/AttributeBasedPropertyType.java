package me.udnek.rpgu.component.ability.property.type;

import me.udnek.itemscoreu.customattribute.CustomAttribute;
import me.udnek.itemscoreu.customregistry.AbstractRegistrable;
import me.udnek.rpgu.component.ability.property.AttributeBasedProperty;
import me.udnek.rpgu.component.ability.property.function.Modifiers;
import me.udnek.rpgu.lore.ability.AbilityLorePart;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

public class AttributeBasedPropertyType extends AbstractRegistrable implements SelfDescribedPropertyType<Double, AttributeBasedProperty> {

    protected AttributeBasedProperty defaultComponent;
    protected String rawId;


    protected CustomAttribute attribute;
    protected String translation;
    protected boolean divideValueBy20;

    public AttributeBasedPropertyType(@NotNull String rawId, @NotNull CustomAttribute attribute, double defaultValue, @NotNull String translation, boolean divideValueBy20) {
        this.rawId = rawId;
        this.translation = translation;
        this.divideValueBy20 = divideValueBy20;
        this.attribute = attribute;
        defaultComponent =  new AttributeBasedProperty(defaultValue, this);
    }


    public AttributeBasedPropertyType(@NotNull String rawId, @NotNull CustomAttribute attribute, double defaultValue, @NotNull String translation) {
        this(rawId, attribute, defaultValue, translation, false);
    }

    public @NotNull CustomAttribute getAttribute() {return attribute;}

    @Override
    public void describe(@NotNull AttributeBasedProperty attributeBasedProperty, @NotNull AbilityLorePart componentable) {
        Function<Double, Double> modifier = divideValueBy20 ? Modifiers.TICKS_TO_SECONDS() : Function.identity();
        componentable.addAbilityStat(Component.translatable(translation, attributeBasedProperty.getFunction().describeWithModifier(modifier)));
    }

    @Override
    public @NotNull AttributeBasedProperty getDefault() {
        return defaultComponent;
    }

    @Override
    @NotNull
    public String getRawId() {
        return rawId;
    }
}













