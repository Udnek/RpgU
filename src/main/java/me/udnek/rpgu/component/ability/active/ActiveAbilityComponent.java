package me.udnek.rpgu.component.ability.active;

import io.papermc.paper.event.player.PlayerStopUsingItemEvent;
import me.udnek.itemscoreu.customcomponent.CustomComponentMap;
import me.udnek.itemscoreu.customcomponent.CustomComponentType;
import me.udnek.itemscoreu.customitem.CustomItem;
import me.udnek.itemscoreu.util.LoreBuilder;
import me.udnek.rpgu.component.ComponentTypes;
import me.udnek.rpgu.component.ability.AbilityComponent;
import me.udnek.rpgu.equipment.slot.UniversalInventorySlot;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.jetbrains.annotations.NotNull;

public interface ActiveAbilityComponent<ActivationContext> extends AbilityComponent<ActivationContext> {

    ActiveAbilityComponent<?> DEFAULT = new ActiveAbilityComponent<>() {
        final CustomComponentMap<AbilityComponent<?>> components = new CustomComponentMap<>();
        @Override
        public @NotNull CustomComponentMap<AbilityComponent<?>> getComponents() {return components;}
        @Override
        public void getLore(@NotNull LoreBuilder loreBuilder) {}
        @Override
        public void activate(@NotNull CustomItem customItem, @NotNull LivingEntity livingEntity, boolean canselIfCooldown,
                             UniversalInventorySlot slot, @NotNull Object object) {}
    };

    default void onRightClick(@NotNull CustomItem customItem, @NotNull PlayerInteractEvent event){}
    default void onStopUsing(@NotNull CustomItem customItem, @NotNull PlayerStopUsingItemEvent event){}
    default void onConsume(@NotNull CustomItem customItem, @NotNull PlayerItemConsumeEvent event){}


    default @NotNull CustomComponentType<CustomItem, ?> getType(){return ComponentTypes.ACTIVE_ABILITY_ITEM;}
}
