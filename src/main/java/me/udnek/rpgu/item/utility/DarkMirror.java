package me.udnek.rpgu.item.utility;

import me.udnek.coreu.custom.component.instance.RightClickableItem;
import me.udnek.coreu.custom.item.ConstructableCustomItem;
import me.udnek.coreu.custom.item.CustomItem;
import me.udnek.jeiu.component.HiddenItemComponent;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class DarkMirror extends ConstructableCustomItem {

    @Override
    public @NotNull String getRawId() {
        return "dark_mirror";
    }

    @Override
    public @NotNull Material getMaterial() {
        return Material.FISHING_ROD;
    }

    @Override
    public void initializeComponents() {
        super.initializeComponents();
        getComponents().set(new DarkMirror.MagicalMirrorComponent());
        getComponents().set(HiddenItemComponent.INSTANCE);
    }

    static class MagicalMirrorComponent implements RightClickableItem {
        static HashMap<PotionEffectType, PotionEffectType> effectSwitches = new HashMap<>();
        static List<PotionEffectType> dispellableEffects = new ArrayList<>();
        public static final int EXTRA_DURATION = 0;

        static{
            //TODO add vulnerability effect or smth, so Resistance and Fire Resistance can be swapped with it
            effectSwitches.put(PotionEffectType.SPEED, PotionEffectType.SLOWNESS);
            effectSwitches.put(PotionEffectType.SLOWNESS, PotionEffectType.SPEED);
            effectSwitches.put(PotionEffectType.HASTE, PotionEffectType.MINING_FATIGUE);
            effectSwitches.put(PotionEffectType.MINING_FATIGUE, PotionEffectType.HASTE);
            effectSwitches.put(PotionEffectType.STRENGTH, PotionEffectType.WEAKNESS);
            effectSwitches.put(PotionEffectType.INSTANT_HEALTH, PotionEffectType.INSTANT_DAMAGE);
            effectSwitches.put(PotionEffectType.INSTANT_DAMAGE, PotionEffectType.INSTANT_HEALTH);
            effectSwitches.put(PotionEffectType.JUMP_BOOST, PotionEffectType.SLOWNESS);
            effectSwitches.put(PotionEffectType.NAUSEA, PotionEffectType.NIGHT_VISION);
            effectSwitches.put(PotionEffectType.REGENERATION, PotionEffectType.WITHER);
            dispellableEffects.add(PotionEffectType.RESISTANCE);
            dispellableEffects.add(PotionEffectType.FIRE_RESISTANCE);
            dispellableEffects.add(PotionEffectType.WATER_BREATHING);
            effectSwitches.put(PotionEffectType.INVISIBILITY, PotionEffectType.GLOWING);
            effectSwitches.put(PotionEffectType.BLINDNESS, PotionEffectType.NIGHT_VISION);
            effectSwitches.put(PotionEffectType.NIGHT_VISION, PotionEffectType.DARKNESS);
            effectSwitches.put(PotionEffectType.HUNGER, PotionEffectType.SATURATION);
            effectSwitches.put(PotionEffectType.WEAKNESS, PotionEffectType.STRENGTH);
            effectSwitches.put(PotionEffectType.POISON, PotionEffectType.REGENERATION);
            effectSwitches.put(PotionEffectType.WITHER, PotionEffectType.REGENERATION);
            effectSwitches.put(PotionEffectType.HEALTH_BOOST, PotionEffectType.WITHER);
            effectSwitches.put(PotionEffectType.ABSORPTION, PotionEffectType.WITHER);
            effectSwitches.put(PotionEffectType.SATURATION, PotionEffectType.HUNGER);
            effectSwitches.put(PotionEffectType.GLOWING, PotionEffectType.INVISIBILITY);
            effectSwitches.put(PotionEffectType.LEVITATION, PotionEffectType.SLOW_FALLING);
            effectSwitches.put(PotionEffectType.LUCK, PotionEffectType.UNLUCK);
            effectSwitches.put(PotionEffectType.UNLUCK, PotionEffectType.LUCK);
            effectSwitches.put(PotionEffectType.SLOW_FALLING, PotionEffectType.LEVITATION);
            effectSwitches.put(PotionEffectType.CONDUIT_POWER, PotionEffectType.MINING_FATIGUE);
            effectSwitches.put(PotionEffectType.DOLPHINS_GRACE, PotionEffectType.SLOWNESS);
            effectSwitches.put(PotionEffectType.DARKNESS, PotionEffectType.NIGHT_VISION);
            dispellableEffects.add(PotionEffectType.WIND_CHARGED);
            dispellableEffects.add(PotionEffectType.WEAVING);
            dispellableEffects.add(PotionEffectType.OOZING);
            dispellableEffects.add(PotionEffectType.INFESTED);
        }

        @Override
        public void onRightClick(@NotNull CustomItem custom.Item, @NotNull PlayerInteractEvent event) {
            event.setCancelled(true);
            Player player = event.getPlayer();
            Collection<PotionEffect> potionEffects = new ArrayList<>(player.getActivePotionEffects());
            for (PotionEffect potionEffect : potionEffects) {
                if (dispellableEffects.contains(potionEffect.getType())){
                    player.removePotionEffect(potionEffect.getType());
                } else if (effectSwitches.containsKey(potionEffect.getType())) {
                    player.removePotionEffect(potionEffect.getType());
                    PotionEffectType newEffectType = effectSwitches.get(potionEffect.getType());
                    player.addPotionEffect(potionEffect.withType(newEffectType).withDuration(potionEffect.getDuration() + EXTRA_DURATION));
                }
            }
        }
    }
}
