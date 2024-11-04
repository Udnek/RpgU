package me.udnek.rpgu.attribute.instance;

import me.udnek.rpgu.attribute.AttributeWrapperAttribute;
import org.bukkit.attribute.Attribute;
import org.jetbrains.annotations.NotNull;

public class PhysicalArmorAttribute extends AttributeWrapperAttribute {

    public static final double MAX = 20;
    public static final double MAX_ABSORPTION = 0.75;



    public PhysicalArmorAttribute(@NotNull String rawId) {
        super(rawId,0, 0, MAX, Attribute.ARMOR);
    }

    @Override
    public @NotNull String translationKey() {
        return "attribute.name.armor";
    }

    public static double calculateAbsorption(double armor){
        final double k = MAX/MAX_ABSORPTION - MAX;
        return (-k/(armor+k)) + 1;
    }
}
