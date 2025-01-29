package me.udnek.rpgu.attribute.instance;

import me.udnek.itemscoreu.customattribute.ConstructableCustomAttribute;
import me.udnek.rpgu.mechanic.damaging.DamageEvent;
import me.udnek.rpgu.mechanic.damaging.DamageInstance;
import me.udnek.rpgu.particle.BackstabParticle;
import me.udnek.rpgu.util.Sounds;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class BackstabDamageAttribute extends ConstructableCustomAttribute implements Listener {

    public BackstabDamageAttribute(@NotNull String rawId, double defaultValue, double min, double max, boolean beneficial, boolean numberAsPercentageLore) {
        super(rawId, defaultValue, min, max, beneficial, numberAsPercentageLore);
    }

    @EventHandler
    public void onDamage(DamageEvent event){
        if (event.getState() != DamageEvent.State.AFTER_EQUIPMENT_ATTACKS) return;
        DamageInstance damageInstance = event.getDamageInstance();

        if (damageInstance.getDamager() == null) return;
        Entity damager = damageInstance.getDamager();
        if (!(event.getDamageInstance().getCausingDamager() instanceof LivingEntity causingDamager)) return;
        if (damageInstance.getCooledAttackStrength() < 0.848) return;
        if (!(isBackstab(damager, damageInstance.getVictim()) || isBackstab(causingDamager, damageInstance.getVictim()))) return;

        double amount = calculate(causingDamager);
        if (amount == 1) return;
        damageInstance.getDamage().multiply(amount);
        if (damageInstance.getVictim() instanceof LivingEntity livingVictim){
            Sounds.BACKSTAB.play(livingVictim.getEyeLocation());
            livingVictim.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20*5, 0, false));
            playParticles(livingVictim);
        }
    }

    public boolean isBackstab(@NotNull Entity damager, @NotNull Entity victim){
        Vector victimDirection = victim.getLocation().getDirection();
        Vector positionDiff = victim.getLocation().toVector().subtract(damager.getLocation().toVector()).setY(0);
        return victimDirection.angle(positionDiff) <= Math.toRadians(45);
    }

    public void playParticles(@NotNull LivingEntity victim){
        Random random = new Random();
        BoundingBox boundingBox = victim.getBoundingBox();
        double scale = (boundingBox.getWidthX() + boundingBox.getHeight());
        BackstabParticle particle = new BackstabParticle(scale);
        particle.play(victim.getEyeLocation().add((victim.getLocation())).multiply(0.5)
                .add(new Vector(random.nextFloat()-0.5, random.nextFloat()-0.5, random.nextFloat()-0.5).multiply(scale/2d)));
    }
}
