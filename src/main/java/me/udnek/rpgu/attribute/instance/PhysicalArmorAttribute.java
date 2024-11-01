package me.udnek.rpgu.attribute.instance;

import me.udnek.itemscoreu.customattribute.ConstructableCustomAttribute;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

public class PhysicalArmorAttribute extends ConstructableCustomAttribute {

    public static final double MAX = 20;
    public static final double MAX_ABSORPTION = 0.75;



    public PhysicalArmorAttribute(@NotNull String rawId) {
        super(rawId, "attribute.name.armor", 0, 0, MAX);
    }

    @Override
    public double calculate(@NotNull LivingEntity entity) {
        AttributeInstance attribute = entity.getAttribute(Attribute.ARMOR);
        if (attribute == null) return getDefaultValue();
        return Math.clamp(attribute.getValue(), 0, 20);
    }

    public static double calculateAbsorption(double armor){
        final double k = MAX/MAX_ABSORPTION - MAX;
        return (-k/(armor+k)) + 1;
    }
}
