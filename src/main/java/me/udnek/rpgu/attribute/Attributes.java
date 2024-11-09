package me.udnek.rpgu.attribute;

import me.udnek.itemscoreu.customattribute.ConstructableCustomAttribute;
import me.udnek.itemscoreu.customattribute.CustomAttribute;
import me.udnek.itemscoreu.customregistry.CustomRegistries;
import me.udnek.rpgu.RpgU;
import me.udnek.rpgu.attribute.instance.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.enchantments.Enchantment;

public class Attributes{
    public static final CustomAttribute PHYSICAL_DAMAGE;
    public static final CustomAttribute ATTACK_SPEED;
    public static final CustomAttribute PHYSICAL_ARMOR;
    public static final CustomAttribute MAGICAL_POTENTIAL;
    public static final CustomAttribute MELEE_MAGICAL_DAMAGE_MULTIPLIER;
    public static final CustomAttribute MAGICAL_DEFENSE_MULTIPLIER;
    public static final CustomAttribute CAST_RANGE;
    public static final CustomAttribute COOLDOWN_TIME;
    public static final CustomAttribute PROJECTILE_DAMAGE_MULTIPLIER;
    public static final CustomAttribute PROJECTILE_SPEED;
    public static final CustomAttribute BACKSTAB_DAMAGE_MULTIPLIER;

    public static final CustomAttribute PROJECTILE_PROTECTION;
    public static final CustomAttribute BLAST_PROTECTION;
    public static final CustomAttribute FIRE_PROTECTION;
    public static final CustomAttribute FALLING_PROTECTION;


    static {
        PHYSICAL_DAMAGE = register(new AttributeWrapperAttribute("physical_damage",0, 1024, Attribute.ATTACK_DAMAGE));
        ATTACK_SPEED = register(new AttributeWrapperAttribute("attack_speed", 0, 1024, Attribute.ATTACK_SPEED));
        PHYSICAL_ARMOR = register(new PhysicalArmorAttribute("physical_armor"));
        MAGICAL_POTENTIAL = register(new ConstructableCustomAttribute("magical_potential",0, 1024));
        MELEE_MAGICAL_DAMAGE_MULTIPLIER = register(new ConstructableCustomAttribute("melee_magical_damage_multiplier",0, 1024));
        MAGICAL_DEFENSE_MULTIPLIER = register(new MagicalDefenseMultiplierAttribute("magical_defense_multiplier"));
        CAST_RANGE = register(new ConstructableCustomAttribute("cast_range",1,0, 1024));
        COOLDOWN_TIME = register(new ConstructableCustomAttribute("cooldown_time",1,0, 1024, false));
        PROJECTILE_DAMAGE_MULTIPLIER = register(new ProjectileDamageMultiplierAttribute("projectile_damage_multiplier"));
        PROJECTILE_SPEED = register(new ProjectileSpeedAttribute("projectile_speed"));
        BACKSTAB_DAMAGE_MULTIPLIER = register(new BackstabDamageAttribute("backstab_damage_multiplier"));

        PROJECTILE_PROTECTION = register(new EnchantmentCalculatorAttribute("projectile_protection", Enchantment.PROJECTILE_PROTECTION));
        BLAST_PROTECTION = register(new EnchantmentCalculatorAttribute("blast_protection", Enchantment.BLAST_PROTECTION));
        FIRE_PROTECTION = register(new EnchantmentCalculatorAttribute("fire_protection", Enchantment.FIRE_PROTECTION));
        FALLING_PROTECTION = register(new EnchantmentCalculatorAttribute("falling_protection", Enchantment.FEATHER_FALLING));
    }


    private static CustomAttribute register(CustomAttribute customAttributeType){
        return CustomRegistries.ATTRIBUTE.register(RpgU.getInstance(), customAttributeType);
    }

}
