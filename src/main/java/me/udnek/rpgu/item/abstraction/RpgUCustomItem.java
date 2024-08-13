package me.udnek.rpgu.item.abstraction;

import me.udnek.itemscoreu.customitem.CustomItem;
import me.udnek.itemscoreu.customitem.CustomItemProperties;
import me.udnek.rpgu.lore.TranslationKeys;

public interface RpgUCustomItem extends CustomItem, CustomItemProperties {

    // TODO: 8/7/2024 DO NOT EXTEND THIS CLASS IN EQUIPPBACLE

    @Override
    default String getRawItemName(){return TranslationKeys.itemPrefix + getRawId();}

}
