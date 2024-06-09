package me.udnek.rpgu.attribute;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.EquipmentSlotGroup;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;

public class AttributeUtils {

    public static double attributeAttackSpeedToAttacksPerSecond(double attackSpeed){
        return (4+attackSpeed);
    }

/*    public static double getAttackDamage(ItemStack itemStack){
        return getWeaponAttribute(itemStack, Attribute.GENERIC_ATTACK_DAMAGE);
    }

    public static double getAttackSpeed(ItemStack itemStack){
        return getWeaponAttribute(itemStack, Attribute.GENERIC_ATTACK_SPEED);
    }


    public static double getWeaponAttribute(ItemStack itemStack, Attribute attribute){

        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta == null) return 0;

        Collection<AttributeModifier> attributeModifiers;
        if (itemMeta.hasAttributeModifiers()){
            attributeModifiers = itemMeta.getAttributeModifiers(attribute);
        }
        else {
            attributeModifiers = itemStack.getType().getDefaultAttributeModifiers(EquipmentSlot.HAND).get(attribute);
        }

        if (attributeModifiers == null || attributeModifiers.isEmpty()) return 0;

        AttributeModifier attributeModifier = attributeModifiers.iterator().next();
        if (attributeModifier.getOperation() == AttributeModifier.Operation.ADD_NUMBER && attributeModifier.getSlot() == EquipmentSlot.HAND){
            return attributeModifier.getAmount();
        }
        return 0;
    }*/

    public static void addDefaultAttributes(ItemStack itemStack){

        Multimap<Attribute, AttributeModifier> attributeModifiers = itemStack.getType().getDefaultAttributeModifiers(itemStack.getType().getEquipmentSlot());
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setAttributeModifiers(attributeModifiers);
        itemStack.setItemMeta(itemMeta);

    }


    public static void addSuitableAttribute(ItemStack itemStack, Attribute attribute, double amount){
        ItemMeta itemMeta = itemStack.getItemMeta();
        switch (itemStack.getType().getEquipmentSlot()) {
            case HAND:
            case OFF_HAND:
                appendAttribute(itemMeta, attribute, amount, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND);
                break;
            default:
                switch (attribute) {
                    case GENERIC_ATTACK_SPEED:
                    case GENERIC_ATTACK_DAMAGE:
                    case GENERIC_MOVEMENT_SPEED:
                        appendAttribute(itemMeta, attribute, amount, AttributeModifier.Operation.MULTIPLY_SCALAR_1, itemStack.getType().getEquipmentSlot());
                        break;
                    default:
                        appendAttribute(itemMeta, attribute, amount, AttributeModifier.Operation.ADD_NUMBER, itemStack.getType().getEquipmentSlot());
                        break;
                }
        }
        itemStack.setItemMeta(itemMeta);
    }

    public static void appendAttribute(ItemStack itemStack, Attribute attribute, double amount, AttributeModifier.Operation operation, EquipmentSlot equipmentSlot){
        ItemMeta itemMeta = itemStack.getItemMeta();
        appendAttribute(itemMeta, attribute, amount, operation, equipmentSlot);
        itemStack.setItemMeta(itemMeta);
    }


    public static void appendAttribute(ItemMeta itemMeta, Attribute attribute, double amount, AttributeModifier.Operation operation, EquipmentSlot equipmentSlot) {
        if (!itemMeta.hasAttributeModifiers()) {
            addAttribute(itemMeta, attribute, amount, operation, equipmentSlot);
            return;
        }
        Collection<AttributeModifier> attributeModifiers = itemMeta.getAttributeModifiers(attribute);
        if (attributeModifiers == null || attributeModifiers.isEmpty()){
            addAttribute(itemMeta, attribute, amount, operation, equipmentSlot);
            return;
        }

        ArrayListMultimap<Attribute, AttributeModifier> newAttributeMap = ArrayListMultimap.create();

        for (Map.Entry<Attribute, AttributeModifier> entry : itemMeta.getAttributeModifiers().entries()) {
            Attribute thisAttribute = entry.getKey();
            AttributeModifier thisAttributeModifier = entry.getValue();

            if (thisAttribute == attribute && thisAttributeModifier.getSlot() == equipmentSlot && thisAttributeModifier.getOperation() == operation){
                AttributeModifier newAttributeModifier = new AttributeModifier(UUID.randomUUID(), "", amount+thisAttributeModifier.getAmount(), operation, equipmentSlot);
                newAttributeMap.put(thisAttribute, newAttributeModifier);
            }
            else {
                newAttributeMap.put(thisAttribute, thisAttributeModifier);
            }
        }
        itemMeta.setAttributeModifiers(newAttributeMap);
    }

    public static void addAttribute(ItemMeta itemMeta, Attribute attribute, double amount, AttributeModifier.Operation operation, EquipmentSlot equipmentSlot){
        itemMeta.addAttributeModifier(attribute, new AttributeModifier(UUID.randomUUID(), "addedByAttributeUtils", amount, operation, equipmentSlot));
    }

    public static Multimap<Attribute, AttributeModifier> getAttributesBySlot(Multimap<Attribute, AttributeModifier> attributes, EquipmentSlotGroup slot){
        ArrayListMultimap<Attribute, AttributeModifier> newAttributes = ArrayListMultimap.create();
        for (Map.Entry<Attribute, AttributeModifier> entry : attributes.entries()) {
            AttributeModifier modifier = entry.getValue();
            if (modifier.getSlotGroup() != slot) continue;
            newAttributes.put(entry.getKey(), modifier);
        }
        return newAttributes;
    }
}



















