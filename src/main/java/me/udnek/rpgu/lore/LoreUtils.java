package me.udnek.rpgu.lore;

import com.google.common.collect.Multimap;
import me.udnek.itemscoreu.customattribute.CustomAttribute;
import me.udnek.itemscoreu.customattribute.CustomAttributeModifier;
import me.udnek.itemscoreu.customattribute.CustomAttributesContainer;
import me.udnek.itemscoreu.customattribute.DefaultCustomAttributeHolder;
import me.udnek.itemscoreu.customattribute.equipmentslot.CustomEquipmentSlot;
import me.udnek.itemscoreu.customattribute.equipmentslot.CustomEquipmentSlots;
import me.udnek.itemscoreu.customitem.CustomItem;
import me.udnek.itemscoreu.utils.ComponentU;
import me.udnek.rpgu.Utils;
import me.udnek.rpgu.attribute.CustomUUIDAttributeModifier;
import me.udnek.rpgu.attribute.DefaultVanillaAttributeHolder;
import me.udnek.rpgu.attribute.RpgUAttributeUtils;
import me.udnek.rpgu.attribute.VanillaAttributeContainer;
import me.udnek.rpgu.util.ExtraDescribed;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.EquipmentSlotGroup;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import oshi.util.tuples.Pair;

import java.util.*;

public class LoreUtils {

    public static final TextColor meleeDescriptionColor = TextColor.color(0, 170, 0);
    public static final TextColor otherDescriptionColor = TextColor.color(85, 85, 255);
    public static final TextColor headerColor = TextColor.color(170, 170, 170);

    public static final TextColor equalsAttributeColor = TextColor.color(0, 170, 0);
    public static final TextColor takeAttributeColor = TextColor.color(255, 85, 85);
    public static final TextColor plusAttributeColor = TextColor.color(85, 85, 255);

    public static void apply(ItemStack itemStack, List<Component> lore){
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.lore(lore);
        itemStack.setItemMeta(itemMeta);
    }

    public static void generateFullLoreAndApply(ItemStack itemStack){
        apply(itemStack, generateFullLore(itemStack));
    }

    public static List<Component> generateFullLore(ItemStack itemStack){
        CustomItem customItem = CustomItem.get(itemStack);
        if (customItem == null) return null;

        List<Component> lore = new ArrayList<>();

        // VANILLA

        Multimap<Attribute, AttributeModifier> vanillaAttributes = itemStack.getItemMeta().getAttributeModifiers();
        if (vanillaAttributes == null || vanillaAttributes.isEmpty()){
            vanillaAttributes = itemStack.getType().getDefaultAttributeModifiers();
        }

        // CUSTOM

        CustomAttributesContainer customAttributes;
        if (customItem instanceof DefaultCustomAttributeHolder attributeHolder){
            customAttributes = attributeHolder.getDefaultCustomAttributes();
            if (customAttributes == null) customAttributes = CustomAttributesContainer.empty();
        } else customAttributes = CustomAttributesContainer.empty();

        // VANILLA-CUSTOM

        VanillaAttributeContainer vanillaCustomAttributes;
        if (customItem instanceof DefaultVanillaAttributeHolder attributeHolder){
            vanillaCustomAttributes = attributeHolder.getDefaultVanillaAttributes();
            if (vanillaCustomAttributes == null) vanillaCustomAttributes = VanillaAttributeContainer.empty();
        } else  vanillaCustomAttributes = VanillaAttributeContainer.empty();






        for (CustomEquipmentSlot slot : CustomEquipmentSlots.getAllSlots()) {

            List<Component> attributesLore = new ArrayList<>();

            // VANILLA

            EquipmentSlotGroup vanillaSlot = slot.getVanillaAlternative();
            if (vanillaSlot != null){

                Multimap<Attribute, AttributeModifier> attributesBySlot = me.udnek.itemscoreu.customattribute.AttributeUtils.getAttributesBySlot(vanillaAttributes, vanillaSlot);
                Attribute[] sorted = sortAttributes(attributesBySlot);

                for (Attribute attribute : sorted) {
                    for (AttributeModifier modifier : attributesBySlot.get(attribute)) {
                        attributesLore.add(getAttributeLine(attribute, modifier, slot));
                    }
                }
            }

            // CUSTOM

            CustomAttributesContainer customAttributesBySlot = customAttributes.get(slot);
            for (Map.Entry<CustomAttribute, List<CustomAttributeModifier>> entry : customAttributesBySlot.getAll().entrySet()) {
                CustomAttribute attribute = entry.getKey();
                for (CustomAttributeModifier modifier : entry.getValue()) {
                    attributesLore.add(getAttributeLine(attribute, modifier, slot));
                }
            }

            // VANILLA-CUSTOM

            VanillaAttributeContainer vanillaCustomAttributesBySlot = vanillaCustomAttributes.get(slot);
            for (Map.Entry<Attribute, List<CustomUUIDAttributeModifier>> entry : vanillaCustomAttributesBySlot.getAll().entrySet()) {
                Attribute attribute = entry.getKey();
                for (CustomUUIDAttributeModifier modifier : entry.getValue()) {
                    attributesLore.add(getAttributeLine(attribute, modifier, slot));
                }
            }


            // EXTRA DESCRIPTION

            Map<CustomEquipmentSlot, Pair<Integer, Integer>> extraDescription;
            if (customItem instanceof ExtraDescribed descriptionItem){
                extraDescription = descriptionItem.getExtraDescription();
            } else extraDescription = new HashMap<>();
            List<Component> extraDescriptionLore;
            if (extraDescription.containsKey(slot)){
                extraDescriptionLore = getExtraDescription(customItem, extraDescription.get(slot), slot);
            } else extraDescriptionLore = new ArrayList<>();

            // FINAL

            if (attributesLore.isEmpty() && extraDescriptionLore.isEmpty()) continue;
            lore.add(Component.empty());
            lore.add(getHeader(slot));
            lore.addAll(attributesLore);
            lore.addAll(extraDescriptionLore);


        }
        // TODO: 6/9/2024 ADD ID???
        //lore.add(Component.text(customItem.getId()).color(TextColor.fromHexString("#555555")).decoration(TextDecoration.ITALIC, false));

        return lore;
    }

    public static Attribute[] sortAttributes(Multimap<Attribute, AttributeModifier> multimap){
        Attribute[] keys = new Attribute[multimap.keys().size()];
        multimap.keys().toArray(keys);
        Arrays.sort(keys, new Comparator<Attribute>() {
            @Override
            public int compare(Attribute a1, Attribute a2) {
                if (a1 == Attribute.GENERIC_ATTACK_DAMAGE) return -1;
                if (a2 == Attribute.GENERIC_ATTACK_DAMAGE) return 1;
                return -1;
            }
        });

        return keys;
    }

    public static List<Component> getExtraDescription(CustomItem customItem, Pair<Integer, Integer> range, CustomEquipmentSlot slot){
        List<Component> lore = new ArrayList<>();
        for (int i = range.getA(); i <= range.getB(); i++) {
            lore.add(addOuter(Component.translatable(TranslationKeys.itemPrefix+customItem.getRawId()+".description."+i)).color(getDescriptionColor(slot)));
        }
        return lore;
    }
    public static Component getAttributeLine(Attribute attribute, AttributeModifier modifier, CustomEquipmentSlot slot){
        String attributeName = TranslationKeys.get(attribute);
        double amount = modifier.getAmount();
        if (attribute == Attribute.GENERIC_ATTACK_DAMAGE && modifier.getOperation() == AttributeModifier.Operation.ADD_NUMBER) amount++;
        else if (attribute == Attribute.GENERIC_ATTACK_SPEED && modifier.getOperation() == AttributeModifier.Operation.ADD_NUMBER) amount = RpgUAttributeUtils.attributeAttackSpeedToAttacksPerSecond(amount);
        return getAttributeLine(attributeName, amount, modifier.getOperation(), slot);
    }
    public static Component getAttributeLine(CustomAttribute attribute, CustomAttributeModifier modifier, CustomEquipmentSlot slot){
        String attributeName = attribute.translationKey();
        return getAttributeLine(attributeName, modifier.getAmount(), modifier.getOperation(), slot);
    }
    public static Component getAttributeLine(Attribute attribute, CustomUUIDAttributeModifier modifier, CustomEquipmentSlot slot){
        String attributeName = TranslationKeys.get(attribute);
        double amount = modifier.getAmount();
        return getAttributeLine(attributeName, amount, modifier.getOperation(), slot);
    }

    public static Component getAttributeLine(String attributeName, double amount, AttributeModifier.Operation operation, CustomEquipmentSlot slot){
        String line;
        if (slot == CustomEquipmentSlots.MAIN_HAND) line = "attribute.modifier.equals.";
        else if (amount < 0) line = "attribute.modifier.take.";
        else line = "attribute.modifier.plus.";
        line += TranslationKeys.get(operation);

        TextColor color = getAttributeColor(amount, slot);

        if (operation != AttributeModifier.Operation.ADD_NUMBER) amount*=100;

        Component noOuter = ComponentU.translatableWithInsertion(line, Component.text(Utils.roundDoubleValueToTwoDigits(Math.abs(amount))), Component.translatable(attributeName));
        Component withOuter = addOuter(noOuter).color(color);
        return withOuter;
    }

    public static TextColor getDescriptionColor(CustomEquipmentSlot slot){
        if (slot == CustomEquipmentSlots.MAIN_HAND) return meleeDescriptionColor;
        return otherDescriptionColor;
    }
    public static TextColor getAttributeColor(double amount, CustomEquipmentSlot slot){
        if (slot == CustomEquipmentSlots.MAIN_HAND) return equalsAttributeColor;
        if (amount > 0) return plusAttributeColor;
        return takeAttributeColor;
    }

    public static Component getHeader(CustomEquipmentSlot slot){
        String line = slot.translationKey();
        return Component.translatable(line).color(headerColor).decoration(TextDecoration.ITALIC, false);
    }

    public static Component addOuter(Component noOuter){
        return ComponentU.translatableWithInsertion(TranslationKeys.equipmentDescriptionLine, noOuter).decoration(TextDecoration.ITALIC, false);
    }

}
