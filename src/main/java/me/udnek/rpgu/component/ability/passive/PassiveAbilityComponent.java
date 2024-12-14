package me.udnek.rpgu.component.ability.passive;

import me.udnek.itemscoreu.customcomponent.CustomComponentMap;
import me.udnek.itemscoreu.customcomponent.CustomComponentType;
import me.udnek.itemscoreu.customitem.CustomItem;
import me.udnek.itemscoreu.util.LoreBuilder;
import me.udnek.rpgu.component.ComponentTypes;
import me.udnek.rpgu.component.ability.AbilityComponent;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public interface PassiveAbilityComponent<ActivationContext> extends AbilityComponent<ActivationContext> {

    PassiveAbilityComponent<?> DEFAULT = new PassiveAbilityComponent<>() {
        final CustomComponentMap<AbilityComponent<?>> components = new CustomComponentMap<>();
        @Override
        public void getLore(@NotNull LoreBuilder loreBuilder) {}
        @Override
        public void activate(@NotNull CustomItem customItem, @NotNull Player player, @NotNull Object o) {}
        @Override
        public @NotNull CustomComponentMap<AbilityComponent<?>> getComponents() {return components;}
    };


    @Override
    default @NotNull CustomComponentType<CustomItem, ?> getType(){
        return ComponentTypes.PASSIVE_ABILITY_ITEM;
    }
}
