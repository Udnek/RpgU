package me.udnek.rpgu.lore.slotdescription;

import com.google.common.collect.Multimap;
import me.udnek.rpgu.damaging.AttributeUtils;
import me.udnek.rpgu.damaging.DamageUtils;
import me.udnek.rpgu.lore.TranslationKeys;
import me.udnek.rpgu.lore.slotdescription.abstracts.SlotDescription;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class HandDescription extends SlotDescription {

    @Override
    public void generate(ItemStack itemStack) {

        Multimap<Attribute, AttributeModifier> attributeModifiers = itemStack.getItemMeta().getAttributeModifiers();
        if (attributeModifiers != null){
            for (Map.Entry<Attribute, AttributeModifier> entry : attributeModifiers.entries()) {
                Attribute attribute = entry.getKey();
                AttributeModifier modifier = entry.getValue();

                if (modifier.getSlot() != EquipmentSlot.HAND) continue;

                if (attribute == Attribute.GENERIC_ATTACK_SPEED){
                    addRawAttributeLine(
                            "attribute.modifier.equals.0",
                            TranslationKeys.attributeAttackSpeed,
                            AttributeUtils.attributeAttackSpeedToAttacksPerSecond(modifier.getAmount()));
                }
                else if (attribute == Attribute.GENERIC_ATTACK_DAMAGE){
                    addRawAttributeLine(
                            "attribute.modifier.equals.0",
                            TranslationKeys.attributePhysicalDamage,
                            modifier.getAmount() + 1);
                }
                else {
                    addAttributeLine(attribute, modifier, false);
                }
            }
        }



        double magicalDamage = DamageUtils.getBaseItemDamage(itemStack).getMagicalDamage();

        if (magicalDamage != 0){
            addRawAttributeLine("attribute.modifier.equals.0", TranslationKeys.attributeMagicalDamage, magicalDamage);
        }

    }

    @Override
    public String getHeaderKey() {
        return TranslationKeys.whenInMainHand;
    }


    public TextColor getHeaderColor() {
        return TextColor.color(170, 170, 170);
    }

    @Override
    public TextColor getDescriptionColor() {
        return TextColor.color(0, 170, 0);
    }
}
