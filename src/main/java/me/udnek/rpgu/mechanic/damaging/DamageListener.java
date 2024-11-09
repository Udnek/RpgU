package me.udnek.rpgu.mechanic.damaging;

import me.udnek.itemscoreu.util.SelfRegisteringListener;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.AbstractArrow;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class DamageListener extends SelfRegisteringListener {

    public static boolean CUSTOM_DAMAGE_SYSTEM = true;

    public DamageListener(JavaPlugin plugin) {
        super(plugin);
    }
    @EventHandler
    public void onEntityTakesDamage(EntityDamageEvent event) {
        if (!CUSTOM_DAMAGE_SYSTEM) {
            DamageVisualizer.visualize(new Damage(Damage.Type.PHYSICAL, event.getDamage()), event.getEntity());
            return;
        }
        new DamageInstance(event).invoke();
    }
    @EventHandler
    public void onEntityShootBow(EntityShootBowEvent event){
        if (!CUSTOM_DAMAGE_SYSTEM) return;
        if (!(event.getProjectile() instanceof AbstractArrow arrow)) return;
        if (event.getBow() == null) return;
        arrow.setDamage(arrow.getDamage() + event.getBow().getEnchantmentLevel(Enchantment.POWER));
    }
}
