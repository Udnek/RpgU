package me.udnek.rpgu.item;

import io.papermc.paper.event.player.PlayerStopUsingItemEvent;
import me.udnek.itemscoreu.customitem.ConstructableCustomItem;
import me.udnek.itemscoreu.customitem.CustomItem;
import me.udnek.rpgu.component.ConstructableActiveAbilityComponent;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.RayTraceResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ShamanTambourine extends ConstructableCustomItem {
    @Override
    public @NotNull String getRawId() {return "shaman_tambourine";}
    @Override
    public @NotNull Material getMaterial() {return Material.GUNPOWDER;}

    @Override
    public void initializeComponents() {
        super.initializeComponents();
        setComponent(new ShamanTambourineComponent());
    }

    public static class ShamanTambourineComponent implements ConstructableActiveAbilityComponent<PlayerInteractEvent> {

        @Override
        public @Nullable Integer getBaseCooldown() {return 20;}
        @Override
        public int getBaseCastRange() {return 15;}

        @Override
        public @NotNull ConstructableActiveAbilityComponent.ActionResult action(@NotNull CustomItem customItem, @NotNull Player player, @NotNull PlayerInteractEvent event) {
            RayTraceResult rayTraceResult = event.getPlayer().rayTraceEntities(getCastRange(player));
            if (rayTraceResult == null || rayTraceResult.getHitEntity() == null) return ActionResult.UNSUCCESSFUL;
            if (!(rayTraceResult.getHitEntity() instanceof LivingEntity living)) return ActionResult.UNSUCCESSFUL;
            living.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 20, 0));
            return ActionResult.SUCCESSFUL;
        }

        @Override
        public void onRightClick(@NotNull CustomItem customItem, @NotNull PlayerInteractEvent event) {
            activate(customItem, event.getPlayer(), event);
        }

        @Override
        public void onStopUsing(@NotNull CustomItem customItem, @NotNull PlayerStopUsingItemEvent event) {}
    }
}
