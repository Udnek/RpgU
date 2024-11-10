package me.udnek.rpgu.item;

import com.destroystokyo.paper.ParticleBuilder;
import me.udnek.itemscoreu.customitem.ConstructableCustomItem;
import me.udnek.itemscoreu.customitem.CustomItem;
import me.udnek.itemscoreu.nms.ConsumableAnimation;
import me.udnek.itemscoreu.nms.ConsumableComponent;
import me.udnek.rpgu.component.ConstructableActiveAbilityComponent;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.RayTraceResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ShamanTambourine extends ConstructableCustomItem {

    public static int CAST_TIME = (int) (0.75 * 20);

    @Override
    public @NotNull String getRawId() {return "shaman_tambourine";}
    @Override
    public @NotNull Material getMaterial() {return Material.GUNPOWDER;}

    @Override
    public @Nullable ConsumableComponent getConsumable() {
        ConsumableComponent component = new ConsumableComponent();
        component.setConsumeTicks(CAST_TIME);
        component.setAnimation(ConsumableAnimation.BOW);
        component.setHasConsumeParticles(false);
        component.setSound(null);
        return component;
    }

    @Override
    public void initializeComponents() {
        super.initializeComponents();
        setComponent(new ShamanTambourineComponent());
    }

    public static class ShamanTambourineComponent implements ConstructableActiveAbilityComponent<PlayerItemConsumeEvent> {

        @Override
        public int getBaseCooldown() {return 20;}
        @Override
        public int getBaseCastRange() {return 15;}
        @Override
        public int getBaseCastTime() {return CAST_TIME;}

        @Override
        public @NotNull ConstructableActiveAbilityComponent.ActionResult action(@NotNull CustomItem customItem, @NotNull Player player, @NotNull PlayerItemConsumeEvent event) {
            RayTraceResult rayTraceResult = player.getWorld().rayTraceEntities(player.getEyeLocation(), player.getLocation().getDirection(), getCastRange(player), 1, entity -> entity!=player);
            if (!(rayTraceResult != null && rayTraceResult.getHitEntity() instanceof LivingEntity living)) {
                ParticleBuilder builder = new ParticleBuilder(Particle.SHRIEK).count(1)
                        .location(player.getLocation().add(player.getLocation().getDirection().multiply(getCastRange(player))).add(0, 1, 0));
                builder.data(0);
                builder.spawn();
                return ActionResult.NO_COOLDOWN;
            }
            living.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 20, 0));
            new ParticleBuilder(Particle.SONIC_BOOM).count(1).location(rayTraceResult.getHitPosition().toLocation(player.getWorld())).spawn();
            return ActionResult.APPLY_COOLDOWN;
        }

        @Override
        public void onConsume(@NotNull CustomItem customItem, @NotNull PlayerItemConsumeEvent event) {
            event.setCancelled(true);
            activate(customItem, event.getPlayer(), event);
        }
    }
}
