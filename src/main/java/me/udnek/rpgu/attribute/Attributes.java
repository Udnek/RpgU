package me.udnek.rpgu.attribute;

import me.udnek.itemscoreu.customattribute.CustomAttribute;
import me.udnek.itemscoreu.customregistry.CustomRegistries;
import me.udnek.rpgu.RpgU;
import me.udnek.rpgu.attribute.instance.MagicalDefenseMultiplierAttribute;
import me.udnek.rpgu.attribute.instance.PhysicalArmorAttribute;

public class Attributes{
    public static final CustomAttribute PHYSICAL_ARMOR;
    public static final CustomAttribute MAGICAL_POTENTIAL;
    public static final CustomAttribute MELEE_MAGICAL_DAMAGE_MULTIPLIER;
    public static final CustomAttribute MAGICAL_DEFENSE_MULTIPLIER;

    static {
        PHYSICAL_ARMOR = register(new PhysicalArmorAttribute("physical_armor"));
        MAGICAL_POTENTIAL = register(new RpgUAttribute("magical_potential",0, 1024));
        MELEE_MAGICAL_DAMAGE_MULTIPLIER = register(new RpgUAttribute("meele_magical_damage_multiplier",0, 1024));
        MAGICAL_DEFENSE_MULTIPLIER = register(new MagicalDefenseMultiplierAttribute("magical_defense_multiplier"));
    }


    private static CustomAttribute register(CustomAttribute customAttributeType){
        return CustomRegistries.ATTRIBUTE.register(RpgU.getInstance(), customAttributeType);
    }

}
