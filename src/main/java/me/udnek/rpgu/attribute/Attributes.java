package me.udnek.rpgu.attribute;

import me.udnek.itemscoreu.customattribute.CustomAttribute;
import me.udnek.itemscoreu.customattribute.CustomAttributeManager;
import me.udnek.rpgu.RpgU;

public class Attributes{

    public static final MagicalDamageAttribute MAGICAL_DAMAGE = (MagicalDamageAttribute) register(new MagicalDamageAttribute());

    private static CustomAttribute register(CustomAttribute customAttributeType){
        return CustomAttributeManager.register(RpgU.getInstance(), customAttributeType);
    }

}
