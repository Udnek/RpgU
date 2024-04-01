package me.udnek.rpgu.lore.slotdescription.abstracts;

import com.google.common.collect.Multimap;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public abstract class EquipmentDescription extends SlotDescription{

    public void generate(ItemStack itemStack) {

        Multimap<Attribute, AttributeModifier> attributeModifiers = itemStack.getItemMeta().getAttributeModifiers();
        if (attributeModifiers != null){
            for (Map.Entry<Attribute, AttributeModifier> entry : attributeModifiers.entries()) {
                Attribute attribute = entry.getKey();
                AttributeModifier modifier = entry.getValue();

                if (modifier.getSlot() != getSlot()) continue;

                addAttributeLine(attribute, modifier, false);
            }
        }
    }

    public abstract EquipmentSlot getSlot();

    @Override
    public TextColor getDescriptionColor() {
        return TextColor.color(85, 85, 255);
    }

    @Override
    public TextColor getHeaderColor() {
        return TextColor.color(170, 170, 170);
    }
}
