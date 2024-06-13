package me.udnek.rpgu.attribute;

import me.udnek.itemscoreu.customattribute.CustomAttribute;
import me.udnek.itemscoreu.customattribute.CustomAttributeManager;
import me.udnek.rpgu.RpgU;
import me.udnek.rpgu.attribute.instance.MagicalDamageAttribute;
import me.udnek.rpgu.attribute.instance.ProjectileDamageAttribute;
import me.udnek.rpgu.attribute.instance.ProjectileSpeedAttribute;

public class Attributes{

    public static final MagicalDamageAttribute MAGICAL_DAMAGE = (MagicalDamageAttribute) register(new MagicalDamageAttribute());
    public static final ProjectileSpeedAttribute PROJECTILE_SPEED = (ProjectileSpeedAttribute) register(new ProjectileSpeedAttribute());
    public static final ProjectileDamageAttribute PROJECTILE_DAMAGE = (ProjectileDamageAttribute) register(new ProjectileDamageAttribute());

    private static CustomAttribute register(CustomAttribute customAttributeType){
        return CustomAttributeManager.register(RpgU.getInstance(), customAttributeType);
    }

}
