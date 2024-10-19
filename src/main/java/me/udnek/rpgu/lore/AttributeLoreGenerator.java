package me.udnek.rpgu.lore;

import com.google.common.collect.Multimap;
import me.udnek.itemscoreu.customattribute.AttributeUtils;
import me.udnek.itemscoreu.customattribute.CustomAttribute;
import me.udnek.itemscoreu.customattribute.CustomAttributeModifier;
import me.udnek.itemscoreu.customattribute.CustomAttributesContainer;
import me.udnek.itemscoreu.customcomponent.CustomComponentType;
import me.udnek.itemscoreu.customequipmentslot.CustomEquipmentSlot;
import me.udnek.itemscoreu.customitem.CustomItem;
import me.udnek.itemscoreu.customregistry.CustomRegistries;
import me.udnek.itemscoreu.utils.ComponentU;
import me.udnek.itemscoreu.utils.LoreBuilder;
import me.udnek.rpgu.attribute.CustomKeyedAttributeModifier;
import me.udnek.rpgu.attribute.RpgUAttributeUtils;
import me.udnek.rpgu.attribute.VanillaAttributesContainer;
import me.udnek.rpgu.component.ComponentTypes;
import me.udnek.rpgu.util.Utils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.EquipmentSlotGroup;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class AttributeLoreGenerator {

    public static final TextColor MELEE_DESCRIPTION_COLOR = NamedTextColor.DARK_GREEN;
    public static final TextColor OTHER_DESCRIPTION_COLOR = NamedTextColor.BLUE;
    public static final TextColor HEADER_COLOR = NamedTextColor.GRAY;

    public static final TextColor EQUALS_ATTRIBUTE_COLOR = NamedTextColor.DARK_GREEN;
    public static final TextColor TAKE_ATTRIBUTE_COLOR = NamedTextColor.RED;
    public static final TextColor PLUS_ATTRIBUTE_COLOR = NamedTextColor.BLUE;


    public static void generate(@NotNull ItemStack itemStack, @NotNull LoreBuilder builder){

        LoreBuilder.Componentable componentable = builder.get(LoreBuilder.Position.ATTRIBUTES);
        AttributesLorePart attributesLorePart;
        if (componentable instanceof AttributesLorePart){
            attributesLorePart = (AttributesLorePart) componentable;
        } else {
            attributesLorePart = new AttributesLorePart();
            builder.set(LoreBuilder.Position.ATTRIBUTES, attributesLorePart);
        }

        // VANILLA
        Multimap<Attribute, AttributeModifier> vanillaAttributes = AttributeUtils.getAttributes(itemStack);
        // CUSTOM
        CustomAttributesContainer customAttributes;
        // VANILLA-CUSTOM
        VanillaAttributesContainer vanillaCustomAttributes;

        CustomItem customItem = CustomItem.get(itemStack);
        if (customItem != null){
            customAttributes = customItem.getComponentOrDefault(CustomComponentType.CUSTOM_ITEM_ATTRIBUTES).getAttributes(customItem);
            // VANILLA-CUSTOM
            vanillaCustomAttributes = customItem.getComponentOrDefault(ComponentTypes.VANILLA_ATTRIBUTES_ITEM).getAttributes();
        } else {
            customAttributes = CustomAttributesContainer.empty();
            vanillaCustomAttributes = VanillaAttributesContainer.empty();
        }


        for (CustomEquipmentSlot slot : CustomRegistries.EQUIPMENT_SLOT.getAll()) {

            //List<Component> attributesLore = new ArrayList<>();

            // VANILLA
            EquipmentSlotGroup vanillaSlot = slot.getVanillaAlternative();
            if (vanillaSlot != null){

                Multimap<Attribute, AttributeModifier> attributesBySlot = me.udnek.itemscoreu.customattribute.AttributeUtils.getAttributesBySlot(vanillaAttributes, vanillaSlot);
                Attribute[] sorted = sortAttributes(attributesBySlot);

                for (Attribute attribute : sorted) {
                    for (AttributeModifier modifier : attributesBySlot.get(attribute)) {
                        attributesLorePart.addAttribute(slot, getAttributeLine(attribute, modifier, slot));
                    }
                }
            }

            // CUSTOM
            CustomAttributesContainer customAttributesBySlot = customAttributes.get(slot);
            for (Map.Entry<CustomAttribute, List<CustomAttributeModifier>> entry : customAttributesBySlot.getAll().entrySet()) {
                CustomAttribute attribute = entry.getKey();
                for (CustomAttributeModifier modifier : entry.getValue()) {
                    attributesLorePart.addAttribute(slot, getAttributeLine(attribute, modifier, slot));
                }
            }

            // VANILLA-CUSTOM
            VanillaAttributesContainer vanillaCustomAttributesBySlot = vanillaCustomAttributes.get(slot);
            for (Map.Entry<Attribute, List<CustomKeyedAttributeModifier>> entry : vanillaCustomAttributesBySlot.getAll().entrySet()) {
                Attribute attribute = entry.getKey();
                for (CustomKeyedAttributeModifier modifier : entry.getValue()) {
                    attributesLorePart.addAttribute(slot, getAttributeLine(attribute, modifier, slot));
                }
            }


            // EXTRA DESCRIPTION

/*            Map<CustomEquipmentSlot, Pair<Integer, Integer>> extraDescription;
            if (customItem instanceof ExtraDescribed descriptionItem){
                extraDescription = descriptionItem.getExtraDescription();
            } else extraDescription = new HashMap<>();
            List<Component> extraDescriptionLore;
            if (extraDescription.containsKey(slot)){
                extraDescriptionLore = getExtraDescription(customItem, extraDescription.get(slot), slot);
            } else extraDescriptionLore = new ArrayList<>();*/

            // FINAL

            /*if (attributesLorePart.get(slot) == null){
                attributesLorePart.remove(slot);*/

        }
        // TODO: 6/9/2024 ADD ID???
        //lore.add(Component.text(customItem.getId()).color(TextColor.fromHexString("#555555")).decoration(TextDecoration.ITALIC, false));

        if (customItem != null){
            builder.add(LoreBuilder.Position.ID,
                    Component.text(customItem.getId()).color(NamedTextColor.DARK_GRAY).decoration(TextDecoration.ITALIC, false)
            );
        }
    }

    public static Attribute[] sortAttributes(Multimap<Attribute, AttributeModifier> multimap){
        Attribute[] keys = new Attribute[multimap.keys().size()];
        multimap.keys().toArray(keys);
        Arrays.sort(keys, new Comparator<Attribute>() {
            @Override
            public int compare(Attribute a1, Attribute a2) {
                if (a1 == Attribute.GENERIC_ATTACK_DAMAGE) return -1;
                if (a2 == Attribute.GENERIC_ATTACK_DAMAGE) return 1;
                return 0;
            }
        });

        return keys;
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
    public static Component getAttributeLine(Attribute attribute, CustomKeyedAttributeModifier modifier, CustomEquipmentSlot slot){
        String attributeName = TranslationKeys.get(attribute);
        double amount = modifier.getAmount();
        return getAttributeLine(attributeName, amount, modifier.getOperation(), slot);
    }

    public static Component getAttributeLine(String attributeName, double amount, AttributeModifier.Operation operation, CustomEquipmentSlot slot){
        String line;
        if (slot == CustomEquipmentSlot.MAIN_HAND) line = "attribute.modifier.equals.";
        else if (amount < 0) line = "attribute.modifier.take.";
        else line = "attribute.modifier.plus.";
        line += TranslationKeys.get(operation);

        TextColor color = getAttributeColor(amount, slot);

        if (operation != AttributeModifier.Operation.ADD_NUMBER) amount*=100d;

        Component noTab = ComponentU.translatableWithInsertion(
                line,
                Component.text(Utils.roundDoubleValueToTwoDigits(Math.abs(amount))),
                Component.translatable(attributeName))
                .color(color);
        //Component withTab = addTab(noTab).color(color);
        return noTab;
    }

/*    public static TextColor getDescriptionColor(CustomEquipmentSlot slot){
        if (slot == CustomEquipmentSlot.MAIN_HAND) return MELEE_DESCRIPTION_COLOR;
        return OTHER_DESCRIPTION_COLOR;
    }*/
    public static TextColor getAttributeColor(double amount, CustomEquipmentSlot slot){
        if (slot == CustomEquipmentSlot.MAIN_HAND) return EQUALS_ATTRIBUTE_COLOR;
        if (amount < 0) return TAKE_ATTRIBUTE_COLOR;
        return PLUS_ATTRIBUTE_COLOR;
    }

    public static Component getHeader(CustomEquipmentSlot slot){
        String line = slot.translationKey();
        return Component.translatable(line).color(HEADER_COLOR).decoration(TextDecoration.ITALIC, false);
    }

    public static Component addTab(Component noOuter){
        return ComponentU.translatableWithInsertion(TranslationKeys.equipmentDescriptionLine, noOuter).decoration(TextDecoration.ITALIC, false);
    }

}
