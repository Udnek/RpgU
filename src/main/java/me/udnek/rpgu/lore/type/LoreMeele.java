package me.udnek.rpgu.lore.type;

import me.udnek.itemscoreu.utils.TranslateUtils;
import me.udnek.rpgu.Utils;
import me.udnek.rpgu.attribute.Attributes;
import me.udnek.rpgu.damaging.AttributeUtils;
import me.udnek.rpgu.damaging.Damage;
import me.udnek.rpgu.damaging.DamageUtils;
import me.udnek.rpgu.lore.TranslationKeys;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class LoreMeele{

    public static final TextColor titleColor = TextColor.color(170, 170, 170);
    public static final TextColor mainColor = TextColor.color(0, 170, 0);
    public static final TextColor offColor = TextColor.color(85, 85, 85);


    public static List<Component> generateDefaultLore(ItemStack itemStack){

        ArrayList<Component> lore = new ArrayList<>();

        Damage damage = DamageUtils.getBaseItemDamage(itemStack);
        double attacksPerSecond = AttributeUtils.attributeAttackSpeedToAttacksPerSecond(AttributeUtils.getAttackSpeed(itemStack));

        lore.add(whenInMainHandLine());
        Component line = generateAttributeLine("FIX", damage.getPhysicalDamage());
        lore.add(line);
        if (damage.getMagicalDamage() != 0){
            line = generateAttributeLine("FIX", damage.getMagicalDamage());
            lore.add(line);
        }
        line = generateAttributeLine("FIX", attacksPerSecond);
        lore.add(line);

        return lore;
    }

    public static Component toExtraInfoStyle(Component component){
        return Component.translatable(TranslationKeys.equipmentDescriptionLine, component).color(mainColor).decoration(TextDecoration.ITALIC, false);
    }


    public static Component generateAttributeLine(String key, double value){
        return decorateAttributeDescription(TranslateUtils.getTranslatedWith(key, Utils.roundDoubleValueToTwoDigits(value)));
    }


    public static Component decorateAttributeDescription(Component component){
        return Component.translatable(TranslationKeys.equipmentDescriptionLine, component).color(mainColor).decoration(TextDecoration.ITALIC, false);
    }


    public static Component whenInMainHandLine(){
        return Component.translatable(TranslationKeys.whenInMainHand).color(titleColor).decoration(TextDecoration.ITALIC, false);
    }
}
