package me.udnek.rpgu.enchanting.enchantment;

import me.udnek.itemscoreu.customenchantment.CustomEnchantment;
import me.udnek.itemscoreu.customenchantment.CustomEnchantmentManager;
import me.udnek.rpgu.RpgU;

public class Enchantments {

    //public static CustomEnchantment test = register(new TestEnchantment());


    private static CustomEnchantment register(CustomEnchantment customEnchantment){
        return (CustomEnchantment) CustomEnchantmentManager.register(RpgU.getInstance(), customEnchantment);
    }

}
