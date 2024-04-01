package me.udnek.rpgu.lore.type;

import me.udnek.itemscoreu.utils.TranslateUtils;
import me.udnek.rpgu.Utils;
import me.udnek.rpgu.lore.TranslationKeys;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class LoreRanged {

    public static final TextColor titleColor = TextColor.color(170, 170, 170);
    public static final TextColor mainColor = TextColor.color(0, 170, 0);


    public static List<Component> generateDefaultLore(ItemStack itemStack){

        ArrayList<Component> lore = new ArrayList<>();

        lore.add(whenShoots());

        //Component line = generateAttributeLine(TranslationKeys.attributeProjectilePhysicalDamage, ProjectilePhysicalDamageAttribute.get(itemStack));
        //lore.add(line);
        // TODO: 2/27/2024 MAGICAL DAMAGE
/*        double magicalDamage = ProjectileMagicalDamageAttribute.get(itemStack);
        if (magicalDamage != 0){
            line = generateAttributeLine(TranslationKeys.attributeProjectileMagicalDamage, magicalDamage);
            lore.add(line);
        }*/
        //line = generateAttributeLine(TranslationKeys.attributeProjectileSpeed, ProjectileSpeedAttribute.get(itemStack));
        //lore.add(line);

        return lore;
    }

    public static Component decorateAttributeDescription(Component component){
        return Component.translatable(TranslationKeys.equipmentDescriptionLine, component).color(mainColor).decoration(TextDecoration.ITALIC, false);
    }

    public static Component generateAttributeLine(String key, double value){
        return decorateAttributeDescription(TranslateUtils.getTranslatedWith(key, Utils.roundDoubleValueToTwoDigits(value)));
    }

    public static Component whenShoots(){
        return Component.translatable(TranslationKeys.whenShoots).color(titleColor).decoration(TextDecoration.ITALIC, false);
    }
}
