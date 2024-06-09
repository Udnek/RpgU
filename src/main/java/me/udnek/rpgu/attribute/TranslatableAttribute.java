package me.udnek.rpgu.attribute;

import me.udnek.itemscoreu.customattribute.CustomAttribute;
import me.udnek.rpgu.lore.TranslationKeys;

public abstract class TranslatableAttribute extends CustomAttribute {

    public String getTranslatableName(){
        return TranslationKeys.attributePrefix + getRawId();
    }

}
