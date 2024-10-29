package me.udnek.rpgu.mechanic.damaging;

import me.udnek.rpgu.attribute.Attributes;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class DamageUtils {

/*    public static Damage calculateMeleeDamage(LivingEntity entity){
        AttributeInstance attribute = entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE);
        Damage damage = new Damage();

        if (attribute != null){
            double rawDamage = attribute.getValue();
            if (entity instanceof Player player) rawDamage *= player.getAttackCooldown();
            damage.addPhysical(rawDamage);
        }

        double magicalDamage = Attributes.MAGICAL_DAMAGE.calculate(entity);
        damage.addMagical(magicalDamage);

        EntityEquipment equipment = entity.getEquipment();
        if (equipment != null){
            ItemStack itemStack = equipment.getItem(EquipmentSlot.HAND);

            int sharpness = itemStack.getEnchantmentLevel(Enchantment.SHARPNESS);
            damage.addPhysical(sharpness == 0 ? 0 : sharpness*0.5 + 0.5);
        }


        return damage;
    }*/

    public static Damage.Type getDamageType(@NotNull EntityDamageEvent event){
        return switch (event.getCause()){
            case WORLD_BORDER,
                 VOID,
                 LIGHTNING,
                 POISON,
                 MAGIC,
                 WITHER,
                 THORNS,
                 DRAGON_BREATH,
                 CUSTOM,
                 SONIC_BOOM,
                 KILL,
                 SUICIDE
                    -> Damage.Type.MAGICAL;
            default -> Damage.Type.PHYSICAL;
        };
    }
}
