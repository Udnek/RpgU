package me.udnek.rpgu.component.ability.passive;

import me.udnek.itemscoreu.customcomponent.CustomComponentMap;
import me.udnek.itemscoreu.customcomponent.CustomComponentType;
import me.udnek.itemscoreu.customequipmentslot.UniversalInventorySlot;
import me.udnek.itemscoreu.customitem.CustomItem;
import me.udnek.itemscoreu.util.LoreBuilder;
import me.udnek.rpgu.component.ComponentTypes;
import me.udnek.rpgu.component.ability.AbilityComponent;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityResurrectEvent;
import org.bukkit.event.entity.EntityToggleGlideEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.jetbrains.annotations.NotNull;

public interface PassiveAbilityComponent<ActivationContext> extends AbilityComponent<ActivationContext> {

    PassiveAbilityComponent<?> DEFAULT = new PassiveAbilityComponent<>() {
        final CustomComponentMap<AbilityComponent<?>> components = new CustomComponentMap<>();
        @Override
        public void getLore(@NotNull LoreBuilder loreBuilder) {}
        @Override
        public void activate(@NotNull CustomItem customItem, @NotNull LivingEntity livingEntity, boolean canselIfCooldown,
                             @NotNull UniversalInventorySlot slot, @NotNull Object object) {}
        @Override
        public @NotNull CustomComponentMap<AbilityComponent<?>> getComponents() {return components;}
    };

    default void onResurrect(@NotNull CustomItem customItem, @NotNull UniversalInventorySlot slot, boolean activatedBefore,
                             @NotNull EntityResurrectEvent event){
        if (!activatedBefore) event.setCancelled(true);
    }
    default void onGlide(@NotNull CustomItem customItem, @NotNull EntityToggleGlideEvent event){}
    default void onDeath(@NotNull CustomItem customItem, @NotNull PlayerDeathEvent event){}

    @Override
    default @NotNull CustomComponentType<CustomItem, ?> getType(){
        return ComponentTypes.PASSIVE_ABILITY_ITEM;
    }
}
