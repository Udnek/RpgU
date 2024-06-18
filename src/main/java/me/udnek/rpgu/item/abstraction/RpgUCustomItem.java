package me.udnek.rpgu.item.abstraction;

import me.udnek.itemscoreu.customitem.CustomItemInterface;
import me.udnek.rpgu.damaging.DamageEvent;
import me.udnek.rpgu.lore.TranslationKeys;

public interface RpgUCustomItem extends CustomItemInterface {
    @Override
    default String getRawItemName(){return TranslationKeys.itemPrefix + getRawId();}

}
