package me.udnek.rpgu.lore;

import me.udnek.itemscoreu.customattribute.equipmentslot.CustomEquipmentSlot;
import me.udnek.itemscoreu.customattribute.equipmentslot.CustomEquipmentSlots;
import me.udnek.rpgu.attribute.equipmentslot.EquipmentSlots;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;

public class TranslationKeys {
    public static final String itemPrefix = "item.rpgu.";

    public static final String whenInMainHand= "item.modifiers.mainhand";
    public static final String whenInOffHand= "item.modifiers.offhand";
    public static final String whenOnHead = "item.modifiers.head";
    public static final String whenOnBody = "item.modifiers.chest";
    public static final String whenOnLegs = "item.modifiers.legs";
    public static final String whenOnFeet = "item.modifiers.feet";
    public static final String whenEquippedAsArtifact = "item.modifiers.artifact";
    public static final String whenShoots = "item.modifiers.shot";


    public static final String equipmentDescriptionLine = "equipment.rpgu.description_line";

    public static final String attributePrefix = "attribute.rpgu.";

    public static final String attributePhysicalDamage = "attribute.rpgu.physical_damage";
    public static final String attributeMagicalDamage = "attribute.rpgu.magical_damage";
    public static final String attributeAttackSpeed = "attribute.rpgu.attack_speed";
    public static final String attributeProjectileSpeed = "attribute.rpgu.projectile_speed";
    public static final String attributeProjectilePhysicalDamage = "attribute.rpgu.projectile_damage_physical";
    public static final String attributeProjectileMagicalDamage = "attribute.rpgu.projectile_damage_magical";


    public static String get(Attribute attribute){
        switch (attribute){
            case GENERIC_ATTACK_DAMAGE -> {return attributePhysicalDamage;}
            case GENERIC_ATTACK_SPEED -> {return attributeAttackSpeed;}
            default -> {
                return attribute.getTranslationKey();
            }
        }
    }
    public static String get(AttributeModifier.Operation operation){
        switch (operation){
            case ADD_NUMBER -> {return "0";}
            case ADD_SCALAR -> {return "1";}
            case MULTIPLY_SCALAR_1 -> {return "2";}
            case null, default -> {
                return "unknown";
            }
        }
    }
    public static String get(CustomEquipmentSlot slot){
        if (CustomEquipmentSlots.MAIN_HAND == slot) return whenInMainHand;
        if (CustomEquipmentSlots.OFF_HAND == slot) return whenInOffHand;
        if (CustomEquipmentSlots.HEAD == slot) return whenOnHead;
        if (CustomEquipmentSlots.CHEST == slot) return whenOnBody;
        if (CustomEquipmentSlots.LEGS == slot) return whenOnLegs;
        if (CustomEquipmentSlots.FEET == slot) return whenOnFeet;
        if (EquipmentSlots.ARTIFACT == slot) return whenEquippedAsArtifact;
        return "unknown";
    }
}
