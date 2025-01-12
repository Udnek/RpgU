package me.udnek.rpgu.attribute.instance;

import me.udnek.itemscoreu.customattribute.ConstructableCustomAttribute;
import me.udnek.rpgu.mechanic.damaging.DamageEvent;
import me.udnek.rpgu.mechanic.damaging.DamageInstance;
import me.udnek.rpgu.particle.BackstabParticle;
import me.udnek.rpgu.util.Sounds;
import org.bukkit.Location;
import org.bukkit.damage.DamageSource;
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

    public BackstabDamageAttribute(@NotNull String rawId, double defaultValue, double min, double max, boolean beneficial, boolean numberAsPercentageLore) {
        super(rawId, defaultValue, min, max, beneficial, numberAsPercentageLore);
    }

    @EventHandler
    public void onDamage(DamageEvent event){
        if (event.getState() != DamageEvent.State.AFTER_EQUIPMENT_ATTACKS) return;
        if (!(event.getDamageInstance().getHandler().getDamageSource().getCausingEntity() instanceof LivingEntity damager)) return;
        DamageInstance damageInstance = event.getDamageInstance();
        if (damageInstance.getCooledAttackStrength() < 0.848) return;
        if (!isBackstab(damager, damageInstance.getVictim())) return;

        double amount = calculate(damager);
        if (amount == 1) return;
        damageInstance.getDamage().multiply(amount);
        if (damageInstance.getVictim() instanceof LivingEntity livingVictim){
            Sounds.BACKSTAB.play(livingVictim.getEyeLocation());
            livingVictim.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20*5, 0, false));
            playParticles(livingVictim.getEyeLocation().add((livingVictim.getLocation())).multiply(0.5));
        }
    }

    public boolean isBackstab(@NotNull Entity damager, @NotNull Entity victim){
        Vector victimDirection = victim.getLocation().getDirection();
        Vector positionDiff = victim.getLocation().toVector().subtract(damager.getLocation().toVector());
        return victimDirection.angle(positionDiff) <= Math.toRadians(45);
    }

    public void playParticles(@NotNull Location location){
        Random random = new Random();
        BackstabParticle particle = new BackstabParticle();
        particle.play(location.add(random.nextFloat()-0.5, random.nextFloat()-0.5, random.nextFloat()-0.5));
    }
}
