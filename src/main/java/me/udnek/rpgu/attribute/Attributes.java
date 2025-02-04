package me.udnek.rpgu.attribute;

import me.udnek.itemscoreu.customattribute.ConstructableCustomAttribute;
import me.udnek.itemscoreu.customattribute.ConstructableReversedCustomAttribute;
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
    public static final CustomAttribute PHYSICAL_RESISTANCE;

    public static final CustomAttribute MAGICAL_POTENTIAL;
    public static final CustomAttribute MELEE_MAGICAL_DAMAGE_MULTIPLIER;
    public static final CustomAttribute MAGICAL_DEFENSE_MULTIPLIER;
    public static final CustomAttribute MAGICAL_RESISTANCE;

    public static final CustomAttribute CAST_RANGE;
    public static final CustomAttribute COOLDOWN_TIME;
    public static final CustomAttribute AREA_OF_EFFECT;
    public static final CustomAttribute ABILITY_DURATION;

    public static final CustomAttribute PROJECTILE_DAMAGE;
    public static final CustomAttribute BACKSTAB_DAMAGE;
    public static final CustomAttribute PROJECTILE_SPEED;
    public static final CustomAttribute CRITICAL_DAMAGE;
    public static final CustomAttribute HEALTH_REGENERATION;

    public static final CustomAttribute PROJECTILE_PROTECTION;
    public static final CustomAttribute BLAST_PROTECTION;
    public static final CustomAttribute FIRE_PROTECTION;
    public static final CustomAttribute FALLING_PROTECTION;


    static {
        PHYSICAL_DAMAGE = register(new AttributeWrapperAttribute("physical_damage",0, 1024, Attribute.ATTACK_DAMAGE));
        ATTACK_SPEED = register(new AttributeWrapperAttribute("attack_speed", 0, 1024, Attribute.ATTACK_SPEED));
        PHYSICAL_ARMOR = register(new PhysicalArmorAttribute("physical_armor"));
        MAGICAL_POTENTIAL = register(new ConstructableCustomAttribute("magical_potential",0,0, 1024));
        MELEE_MAGICAL_DAMAGE_MULTIPLIER = register(new ConstructableCustomAttribute("melee_magical_damage_multiplier",0, 0, 1024));
        MAGICAL_DEFENSE_MULTIPLIER = register(new ConstructableCustomAttribute("magical_defense_multiplier", 0, 0, 20));
        CAST_RANGE = register(new ConstructableCustomAttribute("cast_range",1,0, 1024));
        COOLDOWN_TIME = register(new ConstructableCustomAttribute("cooldown_time",1,0, 1024, false));
        PROJECTILE_DAMAGE = register(new ProjectileDamageMultiplierAttribute("projectile_damage", 1, 0, 1024));
        PROJECTILE_SPEED = register(new ProjectileSpeedAttribute("projectile_speed", 1, 0, 1024));
        BACKSTAB_DAMAGE = register(new BackstabDamageAttribute("backstab_damage", 1, 1, 1024, true, true));
        AREA_OF_EFFECT = register(new ConstructableCustomAttribute("area_of_effect",1,0, 1024));
        ABILITY_DURATION = register(new ConstructableCustomAttribute("ability_duration",1,0, 1024));
        HEALTH_REGENERATION = register(new HealthRegenerationAttribute("health_regeneration", 1, 0, 1024));
        PHYSICAL_RESISTANCE = register(new PhysicalResistanceAttribute("physical_resistance", 0, 0, 1));
        MAGICAL_RESISTANCE = register(new MagicalResistanceAttribute("magical_resistance", 0, 0, 1));
        CRITICAL_DAMAGE = register(new ConstructableCustomAttribute("critical_damage", 1.5, 0, 1024, true, true));

        PROJECTILE_PROTECTION = register(new EnchantmentCalculatorAttribute("projectile_protection", Enchantment.PROJECTILE_PROTECTION));
        BLAST_PROTECTION = register(new EnchantmentCalculatorAttribute("blast_protection", Enchantment.BLAST_PROTECTION));
        FIRE_PROTECTION = register(new EnchantmentCalculatorAttribute("fire_protection", Enchantment.FIRE_PROTECTION));
        FALLING_PROTECTION = register(new EnchantmentCalculatorAttribute("falling_protection", Enchantment.FEATHER_FALLING));
    }


    private static CustomAttribute register(CustomAttribute customAttributeType){
        return CustomRegistries.ATTRIBUTE.register(RpgU.getInstance(), customAttributeType);
    }

}
