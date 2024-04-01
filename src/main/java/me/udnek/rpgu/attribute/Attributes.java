package me.udnek.rpgu.attribute;

import me.udnek.itemscoreu.customattribute.CustomAttributeManager;
import me.udnek.itemscoreu.customattribute.CustomAttributeType;
import me.udnek.rpgu.RpgU;

public class Attributes{

    public static final MagicalDamageAttribute magicalDamage = (MagicalDamageAttribute) register(new MagicalDamageAttribute());

    private static CustomAttributeType register(CustomAttributeType customAttributeType){
        return CustomAttributeManager.register(RpgU.getInstance(), customAttributeType);
    }

}
