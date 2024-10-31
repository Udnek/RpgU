package me.udnek.rpgu.item;

import me.udnek.itemscoreu.customitem.CustomItem;
import me.udnek.itemscoreu.customitem.CustomItemProperties;
import me.udnek.rpgu.lore.TranslationKeys;

public interface RpgUCustomItem extends CustomItem, CustomItemProperties {

    @Override
    default String getRawItemName(){return TranslationKeys.itemPrefix + getRawId();}
}
