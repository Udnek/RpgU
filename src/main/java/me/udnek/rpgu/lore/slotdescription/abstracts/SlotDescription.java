package me.udnek.rpgu.lore.slotdescription.abstracts;

import me.udnek.itemscoreu.utils.TranslateUtils;
import me.udnek.rpgu.Utils;
import me.udnek.rpgu.lore.TranslationKeys;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class SlotDescription {



    public List<Component> mainData;
    public List<Component> extraData;

    public SlotDescription(){
        mainData = new ArrayList<>();
        extraData = new ArrayList<>();
    }

    public abstract TextColor getHeaderColor();
    public abstract TextColor getDescriptionColor();

    public void addAttributeLine(Attribute attribute, AttributeModifier modifier, boolean isPrimal){
        String modifierKey = getAttributeModifierKey(modifier, isPrimal);
        double amount = modifier.getOperation() == AttributeModifier.Operation.ADD_NUMBER ? modifier.getAmount() : modifier.getAmount()*100d;
        addRawAttributeLine(modifierKey, attribute.translationKey(), amount);

    }
    public void addRawAttributeLine(String modifierKey, String attributeKey, double amount){
        addToMainData(Component.translatable(modifierKey, Arrays.asList(Component.text(Utils.roundDoubleValueToTwoDigits(amount)), Component.translatable(attributeKey))));
    }

    public void addToMainData(Component component){
        mainData.add(TranslateUtils.getTranslatedWith(getLineOffsetKey(), component).color(getDescriptionColor()).decoration(TextDecoration.ITALIC, false));
    }
    public void addToExtraData(Component component){
        extraData.add(TranslateUtils.getTranslatedWith(getLineOffsetKey(), component).color(getDescriptionColor()).decoration(TextDecoration.ITALIC, false));
    }

    public abstract void generate(ItemStack itemStack);

    public abstract String getHeaderKey();

    public String getLineOffsetKey(){
        return TranslationKeys.equipmentDescriptionLine;
    }

    public Component getWithLineOffset(Component line){
        return Component.translatable(getLineOffsetKey(), line);
    }


    public String getAttributeModifierKey(AttributeModifier attributeModifier, boolean isPrimal){
        String operationId;
        switch (attributeModifier.getOperation()){
            case ADD_NUMBER:
                operationId = "0";
                break;
            case ADD_SCALAR:
                operationId = "1";
                break;
            case MULTIPLY_SCALAR_1:
                operationId = "2";
                break;
            default:
                operationId = "unknown_id";
                break;
        }
        if (attributeModifier.getAmount() < 0 || isPrimal){
            return "attribute.modifier.equals."+operationId;
        }
        return "attribute.modifier.plus."+operationId;

    }

    public List<Component> build(){

        List<Component> lore = new ArrayList<>();
        lore.add(Component.translatable(getHeaderKey()).color(getHeaderColor()).decoration(TextDecoration.ITALIC, false));
        lore.addAll(mainData);
        lore.addAll(extraData);

        return lore;

    }

    public void generateAndAdd(List<Component> lore, ItemStack itemStack, boolean addEmptyInTheEnd){
        generate(itemStack);
        if (isEmpty()) return;
        lore.addAll(build());
        lore.add(Component.empty());
    }

    public boolean isEmpty(){
        return mainData.isEmpty() && extraData.isEmpty();
    }
}
