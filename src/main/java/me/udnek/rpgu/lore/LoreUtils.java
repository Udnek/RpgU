package me.udnek.rpgu.lore;

import me.udnek.itemscoreu.customattribute.CustomAttributeType;
import me.udnek.itemscoreu.customitem.CustomItem;
import me.udnek.itemscoreu.utils.CustomItemUtils;
import me.udnek.rpgu.attribute.TranslatableAttributeType;
import me.udnek.rpgu.damaging.AttributeUtils;
import me.udnek.rpgu.damaging.Damage;
import me.udnek.rpgu.damaging.DamageUtils;
import me.udnek.rpgu.item.abstracts.attributable.DefaultAttributeHolder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LoreUtils {
    public static void apply(ItemStack itemStack, List<Component> lore){
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.lore(lore);
        itemStack.setItemMeta(itemMeta);
    }

    // TODO: 3/3/2024 DO
    public static void generateFullLore(ItemStack itemStack){
        CustomItem customItem = CustomItemUtils.getFromItemStack(itemStack);
        if (customItem == null) return;

        List<Component> lore = new ArrayList<>();

        if (customItem instanceof DefaultAttributeHolder){
            DefaultAttributeHolder attributeHolder = (DefaultAttributeHolder) customItem;

            for (Map.Entry<CustomAttributeType, Double> entry : attributeHolder.getItemInMainHandAttributes().getAll().entrySet()) {
                if (!(entry.getKey() instanceof TranslatableAttributeType)) continue;

                


            }


        }


    }
}
