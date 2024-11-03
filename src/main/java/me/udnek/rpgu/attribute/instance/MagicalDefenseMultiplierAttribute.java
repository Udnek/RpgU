package me.udnek.rpgu.attribute.instance;

import me.udnek.itemscoreu.customattribute.ConstructableCustomAttribute;
import org.jetbrains.annotations.NotNull;

public class MagicalDefenseMultiplierAttribute extends ConstructableCustomAttribute {

    public static final double MAX = 20;
    public static final double MAX_ABSORPTION = 0.8;

    public MagicalDefenseMultiplierAttribute(@NotNull String rawId) {
        super(rawId, 0, 0, MAX);
    }


    public static double calculateAbsorption(double defense){
        return defense/(MAX/MAX_ABSORPTION);
    }

}
