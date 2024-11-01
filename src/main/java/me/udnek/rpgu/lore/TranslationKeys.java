package me.udnek.rpgu.lore;

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
    public static final String whenEquippedAsArtifact = "item.modifiers.artifacts";
    public static final String whenEquippedAsFirstArtifact = "item.modifiers.first_artifact";
    public static final String whenEquippedAsSecondArtifact = "item.modifiers.second_artifact";
    public static final String whenEquippedAsThirdArtifact = "item.modifiers.third_artifact";
    public static final String whenEquippedAsOrigin = "item.modifiers.origin";
    public static final String whenShoots = "item.modifiers.shot";


    public static final String equipmentDescriptionLine = "equipment.rpgu.description_line";

    public static final String attributePrefix = "attribute.rpgu.name.";

    public static final String attributePhysicalDamage = "attribute.rpgu.name.physical_damage";
    public static final String attributeAttackSpeed = "attribute.rpgu.name.attack_speed";

    public static final String artifactHud = "image.rpgu.artifact.hud";
    public static final String artifactCooldown = "image.rpgu.artifact.cooldown.";
    public static final String unknownArtifactImage = "image.rpgu.artifact.unknown_artifact";

    public static String get(Attribute attribute){
        if (attribute == Attribute.ATTACK_DAMAGE) return attributePhysicalDamage;
        if (attribute == Attribute.ATTACK_SPEED) return attributeAttackSpeed;
        return attribute.translationKey();
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
}
