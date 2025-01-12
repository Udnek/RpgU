package me.udnek.rpgu.mechanic.damaging;

import me.udnek.itemscoreu.util.SelfRegisteringListener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public class DamageListener extends SelfRegisteringListener {

    public static boolean CUSTOM_DAMAGE_SYSTEM = true;

    public DamageListener(@NotNull Plugin plugin) {super(plugin);}

    @EventHandler
    public void onEntityTakesDamage(EntityDamageEvent event) {
        if (!CUSTOM_DAMAGE_SYSTEM) {
            DamageVisualizer.visualize(new Damage(Damage.Type.PHYSICAL, event.getDamage()), event.getEntity());
            return;
        }
        new DamageInstance(event).invoke();
    }
}
