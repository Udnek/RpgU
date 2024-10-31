package me.udnek.rpgu.mechanic.damaging;

import me.udnek.rpgu.attribute.Attributes;
import org.bukkit.Material;
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

    private DamageUtils(){}


    public static @NotNull ItemStack getItemInMainHand(@NotNull LivingEntity entity){
        if (entity instanceof Player player){
            return player.getInventory().getItemInMainHand();
        }
        EntityEquipment equipment = entity.getEquipment();
        if (equipment == null) return new ItemStack(Material.AIR);
        return equipment.getItemInMainHand();
    }

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
