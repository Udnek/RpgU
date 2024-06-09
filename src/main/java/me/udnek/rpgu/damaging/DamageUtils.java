package me.udnek.rpgu.damaging;

import me.udnek.itemscoreu.customattribute.DefaultCustomAttributeHolder;
import me.udnek.itemscoreu.customitem.CustomItem;
import me.udnek.rpgu.attribute.AttributeUtils;
import me.udnek.rpgu.attribute.Attributes;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public class DamageUtils {

    public static Damage calculateMeleeDamage(LivingEntity entity){
        AttributeInstance attribute = entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE);
        Damage damage = new Damage();

        if (attribute != null){
            double rawDamage = attribute.getValue();
            if (entity instanceof Player player) rawDamage *= player.getAttackCooldown();
            damage.addPhysicalDamage(rawDamage);
        }

        double magicalDamage = Attributes.MAGICAL_DAMAGE.calculate(entity);
        damage.addMagicalDamage(magicalDamage);

        EntityEquipment equipment = entity.getEquipment();
        if (equipment != null){
            ItemStack itemStack = equipment.getItem(EquipmentSlot.HAND);

            int sharpness = itemStack.getEnchantmentLevel(Enchantment.SHARPNESS);
            damage.addPhysicalDamage(sharpness == 0 ? 0 : sharpness*0.5 + 0.5);
        }


        return damage;
    }

    public static Damage calculateMeleeDamage(Entity entity){
        if (entity instanceof LivingEntity livingEntity) return calculateMeleeDamage(livingEntity);
        return new Damage();
    }
/*    public static Damage getBaseItemDamage(ItemStack itemStack){

        // TODO: 6/8/2024 MAGICAL DAMAGE
        //double magicalDamage = Attributes.magicalDamage.getMeeleDamage(itemStack);

        double magicalDamage = 0;
*//*        CustomItem customItem = CustomItem.get(itemStack);
*//**//*        if (customItem != null && customItem instanceof DefaultCustomAttributeHolder attributeHolder){
            attributeHolder.getDefaultCustomAttributes().
        }*//*

        double physicalDamage = AttributeUtils.getAttackDamage(itemStack) + 1;

        return new Damage(physicalDamage, magicalDamage);
    }*/

}
