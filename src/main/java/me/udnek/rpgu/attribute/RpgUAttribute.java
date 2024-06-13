package me.udnek.rpgu.attribute;

import me.udnek.itemscoreu.customattribute.CustomAttribute;
import me.udnek.rpgu.lore.TranslationKeys;
import org.jetbrains.annotations.NotNull;

public abstract class RpgUAttribute extends CustomAttribute {
    @Override
    public @NotNull String translationKey() {
        return TranslationKeys.attributePrefix + getRawId();
    }
}
