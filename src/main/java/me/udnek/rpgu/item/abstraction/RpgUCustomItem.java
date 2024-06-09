package me.udnek.rpgu.item.abstraction;

import me.udnek.itemscoreu.customitem.CustomItem;
import me.udnek.rpgu.damaging.DamageEvent;
import me.udnek.rpgu.lore.TranslationKeys;

public abstract class RpgUCustomItem extends CustomItem {
    @Override
    public String getRawItemName() {
        return TranslationKeys.itemPrefix + getRawId();
    }

    public void onEntityAttacks(DamageEvent event){}
}
