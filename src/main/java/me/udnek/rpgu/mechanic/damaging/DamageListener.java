package me.udnek.rpgu.mechanic.damaging;

import me.udnek.coreu.util.SelfRegisteringListener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public class DamageListener extends SelfRegisteringListener {

    public static boolean customDamageSystemEnabled = true;

    public DamageListener(@NotNull Plugin plugin) {super(plugin);}

    @EventHandler
    public void onEntityTakesDamage(EntityDamageEvent event) {
        if (!customDamageSystemEnabled) {
            DamageVisualizer.visualize(new Damage(Damage.Type.PHYSICAL, event.getDamage()), event.getEntity());
            return;
        }
        new DamageInstance(event).invoke();
    }
}
