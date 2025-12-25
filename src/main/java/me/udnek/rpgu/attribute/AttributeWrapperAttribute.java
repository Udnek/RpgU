package me.udnek.rpgu.attribute;

import me.udnek.coreu.custom.attribute.ConstructableCustomAttribute;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

public class AttributeWrapperAttribute extends ConstructableCustomAttribute {

    protected final Attribute target;

    public AttributeWrapperAttribute(@NotNull String rawId, double defaultValue, double min, double max, @NotNull Attribute attribute) {
        super(rawId, defaultValue, min, max);
        this.target = attribute;
    }

    public AttributeWrapperAttribute(@NotNull String rawId, double min, double max, @NotNull Attribute attribute) {
        this(rawId, 0, min, max, attribute);
    }

    @Override
    public double calculate(@NotNull LivingEntity entity) {
        AttributeInstance attribute = entity.getAttribute(target);
        if (attribute == null) return getDefault();
        return Math.clamp(attribute.getValue(), 0, 20);
    }
}
