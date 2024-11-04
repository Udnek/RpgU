package me.udnek.rpgu.item;

import me.udnek.itemscoreu.customitem.ConstructableCustomItem;
import me.udnek.itemscoreu.customitem.CustomItem;
import me.udnek.rpgu.component.ActiveAbilityComponent;
import org.bukkit.Material;
import org.bukkit.event.player.PlayerInteractEvent;
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

    public static class ShamanTambourineComponent implements ActiveAbilityComponent{

        @Override
        public @Nullable Integer getBaseCooldown() {
            return 20;
        }

        @Override
        public @Nullable Integer getBaseCastRange() {
            return null;
        }

        @Override
        public @NotNull ActivationResult onActivation(@NotNull CustomItem customItem, @NotNull PlayerInteractEvent event) {
            event.getPlayer().sendMessage("ACT");
            return ActivationResult.SUCCESSFUL;
        }
    }
}
