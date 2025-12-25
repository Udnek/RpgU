package me.udnek.rpgu.attribute;

import me.udnek.coreu.custom.attribute.AttributeUtils;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.EquipmentSlotGroup;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class RpgUAttributeUtils {

    public static double attributeAttackSpeedToAttacksPerSecond(double attackSpeed){
        return (4+attackSpeed);
    }

    public static void addSuitableAttribute(@NotNull ItemStack itemStack, @NotNull Attribute attribute, @Nullable NamespacedKey id, double amount){
        switch (itemStack.getType().getEquipmentSlot()) {
            case HAND:
            case OFF_HAND:
                AttributeUtils.appendAttribute(itemStack, attribute, id, amount, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.MAINHAND);
                break;
            default:
                if (attribute == Attribute.ATTACK_SPEED ||
                    attribute == Attribute.ATTACK_DAMAGE ||
                    attribute == Attribute.MOVEMENT_SPEED)
                {
                    AttributeUtils.appendAttribute(itemStack, attribute, id, amount, AttributeModifier.Operation.MULTIPLY_SCALAR_1, itemStack.getType().getEquipmentSlot().getGroup());
                } else {
                    AttributeUtils.appendAttribute(itemStack, attribute, id, amount, AttributeModifier.Operation.ADD_NUMBER, itemStack.getType().getEquipmentSlot().getGroup());
                }
        }
    }
}



















