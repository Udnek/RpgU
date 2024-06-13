package me.udnek.rpgu.attribute;

import me.udnek.itemscoreu.customattribute.AttributeUtils;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class RpgUAttributeUtils {

    public static double attributeAttackSpeedToAttacksPerSecond(double attackSpeed){
        return (4+attackSpeed);
    }

    public static void addSuitableAttribute(ItemStack itemStack, Attribute attribute, String seed, double amount){
        ItemMeta itemMeta = itemStack.getItemMeta();
        switch (itemStack.getType().getEquipmentSlot()) {
            case HAND:
            case OFF_HAND:
                AttributeUtils.appendAttribute(itemMeta, attribute, seed, amount, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND);
                break;
            default:
                switch (attribute) {
                    case GENERIC_ATTACK_SPEED:
                    case GENERIC_ATTACK_DAMAGE:
                    case GENERIC_MOVEMENT_SPEED:
                        AttributeUtils.appendAttribute(itemMeta, attribute, seed, amount, AttributeModifier.Operation.MULTIPLY_SCALAR_1, itemStack.getType().getEquipmentSlot());
                        break;
                    default:
                        AttributeUtils.appendAttribute(itemMeta, attribute, seed, amount, AttributeModifier.Operation.ADD_NUMBER, itemStack.getType().getEquipmentSlot());
                        break;
                }
        }
        itemStack.setItemMeta(itemMeta);
    }
}



















