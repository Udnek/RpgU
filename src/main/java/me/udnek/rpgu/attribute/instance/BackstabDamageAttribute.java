package me.udnek.rpgu.attribute.instance;

import me.udnek.itemscoreu.customattribute.ConstructableCustomAttribute;
import me.udnek.rpgu.mechanic.damaging.DamageEvent;
import me.udnek.rpgu.mechanic.damaging.DamageInstance;
import me.udnek.rpgu.particle.BackstabParticle;
import me.udnek.rpgu.util.Sounds;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class BackstabDamageAttribute extends ConstructableCustomAttribute implements Listener {


    public BackstabDamageAttribute(@NotNull String rawId, double defaultValue, double min, double max) {
        super(rawId, defaultValue, min, max);
    }

    @EventHandler
    public void onDamage(DamageEvent event){
        if (event.getState() != DamageEvent.State.AFTER_EQUIPMENT_ATTACKS) return;
        DamageInstance damageInstance = event.getDamageInstance();
        if (!(damageInstance.getDamager() instanceof LivingEntity damager)) return;
        if (damageInstance.getCooledAttackStrength() < 0.848) return;
        if (!isBackstab(damager, damageInstance.getVictim())) return;

        double amount = calculate(damager);
        if (amount == 1) return;
        damageInstance.getDamage().multiplyPhysical(amount);
        if (damageInstance.getVictim() instanceof LivingEntity livingVictim){
            Sounds.playSound(Sounds.BACKSTAB, livingVictim.getEyeLocation());
            //livingVictim.getWorld().playSound(livingVictim.getEyeLocation(), "rpgu:test", 100, 1);
            livingVictim.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20*5, 0, false));
            playParticles(livingVictim.getEyeLocation().add((livingVictim.getLocation())).multiply(0.5));
        }
    }

    public boolean isBackstab(@NotNull Entity damager, @NotNull Entity victim){
        Vector damagerDirection = damager.getLocation().getDirection();
        Vector victimDir = victim.getLocation().getDirection();
        return damagerDirection.angle(victimDir) <= Math.toRadians(45);
    }

    public void playParticles(@NotNull Location location){
        Random random = new Random();
        BackstabParticle particle = new BackstabParticle();
        particle.play(location.add(random.nextFloat()-0.5, random.nextFloat()-0.5, random.nextFloat()-0.5));
    }
}
