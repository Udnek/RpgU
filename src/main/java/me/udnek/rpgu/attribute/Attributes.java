package me.udnek.rpgu.attribute;

import me.udnek.itemscoreu.customattribute.CustomAttribute;
import me.udnek.itemscoreu.customregistry.CustomRegistries;
import me.udnek.rpgu.RpgU;
import me.udnek.rpgu.attribute.instance.ProjectileDamageMultiplierAttribute;
import me.udnek.rpgu.attribute.instance.ProjectileSpeedAttribute;

public class Attributes{

    public static final CustomAttribute MAGICAL_DAMAGE = register(new RpgUAttribute("magical_damage", 0, 0, 100));
    public static final CustomAttribute PROJECTILE_SPEED = register(new ProjectileSpeedAttribute());
    public static final CustomAttribute PROJECTILE_DAMAGE_MULTIPLIER = register(new ProjectileDamageMultiplierAttribute());

    private static CustomAttribute register(CustomAttribute customAttributeType){
        return CustomRegistries.ATTRIBUTE.register(RpgU.getInstance(), customAttributeType);
    }

}
