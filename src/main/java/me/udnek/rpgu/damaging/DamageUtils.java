package me.udnek.rpgu.damaging;

import me.udnek.rpgu.attribute.Attributes;
import me.udnek.rpgu.attribute.MagicalDamageAttribute;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;

public class DamageUtils {

/*    public static void dealRawDamage(LivingEntity target, double amount){
        target.damage(amount);
    }

    public static void dealDamage(LivingEntity target, Damage damage){
        DamageUtils.dealRawDamage(target, damage.getPhysicalDamage());
        DamageUtils.dealRawDamage(target, damage.getPhysicalDamage());
    }*/

    public static Damage calculateMeleeDamage(Player player){
        AttributeInstance attribute = player.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE);
        Damage damage = new Damage();
        if (attribute != null){
            double rawDamage = attribute.getValue() * player.getAttackCooldown();
            damage.addPhysicalDamage(rawDamage);
        }
        ItemStack itemStack = player.getEquipment().getItemInMainHand();

        double magicalDamage = Attributes.magicalDamage.getMeeleDamage(itemStack);

        damage.addMagicalDamage(magicalDamage);

        int sharpness = itemStack.getEnchantmentLevel(Enchantment.DAMAGE_ALL);
        damage.addPhysicalDamage(sharpness == 0 ? 0 : sharpness*0.5 + 0.5);

        return damage;
    }

    public static Damage getBaseItemDamage(ItemStack itemStack){

        double magicalDamage = Attributes.magicalDamage.getMeeleDamage(itemStack);
        double physicalDamage = AttributeUtils.getAttackDamage(itemStack) + 1;

        return new Damage(physicalDamage, magicalDamage);
    }

}
