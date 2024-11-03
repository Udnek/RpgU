package me.udnek.rpgu.component;

import me.udnek.itemscoreu.customcomponent.CustomComponent;
import me.udnek.itemscoreu.customcomponent.CustomComponentType;
import me.udnek.itemscoreu.customitem.CustomItem;
import me.udnek.itemscoreu.util.LoreBuilder;
import me.udnek.itemscoreu.util.Utils;
import me.udnek.rpgu.attribute.Attributes;
import me.udnek.rpgu.lore.ActiveAbilityLorePart;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface ActiveAbilityComponent extends CustomComponent<CustomItem> {

    ActiveAbilityComponent DEFAULT = new ActiveAbilityComponent() {
        @Override
        public @Nullable Integer getBaseCooldown() {return null;}
        @Override
        public @Nullable Integer getBaseCastRange() {return null;}
        @Override
        public void getLore(@NotNull LoreBuilder loreBuilder) {}
        @Override
        public @NotNull ActivationResult onActivation(@NotNull CustomItem customItem, @NotNull PlayerInteractEvent event) {return ActivationResult.SUCCESSFUL;}
        @Override
        public void onRightClick(@NotNull CustomItem customItem, @NotNull PlayerInteractEvent event) {}
    };

    @Nullable Integer getBaseCooldown();
    @Nullable default Integer getCooldown(@NotNull Player player){
        Integer baseCooldown = getBaseCooldown();
        if (baseCooldown == null) return null;
        return (int) Attributes.COOLDOWN_TIME.calculateWithBase(player, baseCooldown);
    }
    @Nullable Integer getBaseCastRange();
    @Nullable default Integer getCastRange(@NotNull Player player){
        Integer baseCastRange = getBaseCastRange();
        if (baseCastRange == null) return null;
        return (int) Attributes.CAST_RANGE.calculateWithBase(player, baseCastRange);
    }

    default void addCooldownLine(@NotNull LoreBuilder.Componentable componentable){
        Utils.consumeIfNotNull(getBaseCooldown(), integer ->
                componentable.add(Component.translatable("Cooldown (secs): " + Utils.roundToTwoDigits(integer/20d))));

    }

    default void addCastRangeLine(@NotNull LoreBuilder.Componentable componentable){
        Utils.consumeIfNotNull(getBaseCastRange(), integer ->
                componentable.add(Component.translatable("Cast Range (blocks): " + Utils.roundToTwoDigits(integer))));
    }

    default void getLore(@NotNull LoreBuilder loreBuilder){
        ActiveAbilityLorePart componentable = new ActiveAbilityLorePart();
        loreBuilder.set(55, componentable);
        componentable.setHeader(Component.translatable("Active ability").color(NamedTextColor.GREEN));
        addCooldownLine(componentable);
        addCastRangeLine(componentable);
    }

    default void onRightClick(@NotNull CustomItem customItem, @NotNull PlayerInteractEvent event){
        if (customItem.hasCooldown(event.getPlayer())) return;
        Integer cooldown = getCooldown(event.getPlayer());
        if (cooldown != null) customItem.setCooldown(event.getPlayer(), cooldown);
        onActivation(customItem, event);
    }

    @NotNull ActivationResult onActivation(@NotNull CustomItem customItem, @NotNull PlayerInteractEvent event);

    default @NotNull CustomComponentType<CustomItem, ?> getType() {
        return ComponentTypes.ACTIVE_ABILITY_ITEM;
    }

    enum ActivationResult{
        SUCCESSFUL,
        UNSUCCESSFUL,
        UNSUCCESSFUL_WITH_COOLDOWN_PENALTY
    }
}
