package me.udnek.rpgu.attribute;

import me.udnek.itemscoreu.customattribute.CustomAttributeType;
import me.udnek.rpgu.lore.TranslationKeys;
import net.kyori.adventure.text.Component;

public abstract class TranslatableAttributeType extends CustomAttributeType {

    public Component getTranslatableName(){
        return Component.translatable(TranslationKeys.attributePrefix + getName());
    }

}
