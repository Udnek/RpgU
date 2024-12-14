package me.udnek.rpgu.effect;

import me.udnek.itemscoreu.customeffect.ConstructableCustomEffect;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionEffectTypeCategory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.*;

public class MireTouch extends ConstructableCustomEffect implements Listener {

    public static final int DEBUFF_DURATION = 8 * 20;

    @Override
    public @NotNull PotionEffectTypeCategory getCategory() {return PotionEffectTypeCategory.BENEFICIAL;}

    @Override
    public @NotNull String getRawId() {return "mire_touch";}

    @Override
    public int getColorIfDefaultParticle() {return Color.GRAY.getRGB();}

    @Override
    public @Nullable PotionEffectType getVanillaDisguise() {return null;}

    @EventHandler
    public void setBasePlayerHealth(EntityDamageByEntityEvent event){
        if (!(event.getDamager() instanceof LivingEntity damager && event.getEntity() instanceof LivingEntity victim)) return;
        if (Effects.MIRE_TOUCH.has(damager)){
            PotionEffect potionEffect = new PotionEffect(PotionEffectType.SLOWNESS, DEBUFF_DURATION, getAppliedLevel(damager), false,  true, true);
            victim.addPotionEffect(potionEffect);
        }

    }
}
