package me.udnek.rpgu.attribute;

import me.udnek.itemscoreu.customattribute.ConstructableCustomAttribute;
import me.udnek.rpgu.lore.TranslationKeys;
import org.jetbrains.annotations.NotNull;

public class RpgUAttribute extends ConstructableCustomAttribute {
    public RpgUAttribute(@NotNull String rawId, double defaultValue, double min, double max) {
        super(rawId, TranslationKeys.attributePrefix + rawId, defaultValue, min, max);
    }

    public RpgUAttribute(@NotNull String rawId, double min, double max) {
       this(rawId, 0d, min, max);
    }

}
