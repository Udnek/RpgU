package me.udnek.rpgu.component.ability.vanilla;

import me.udnek.coreu.custom.component.CustomComponent;
import me.udnek.coreu.custom.component.CustomComponentType;
import me.udnek.coreu.custom.equipment.universal.BaseUniversalSlot;
import me.udnek.coreu.custom.equipment.universal.UniversalInventorySlot;
import me.udnek.coreu.custom.item.CustomItem;
import me.udnek.coreu.rpgu.component.RPGUActiveItem;
import me.udnek.coreu.rpgu.component.ability.active.RPGUConstructableActiveAbility;
import me.udnek.rpgu.component.ability.RPGUActiveTriggerableAbility;
import me.udnek.rpgu.component.ability.VanillaAbilities;
import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.player.PlayerInteractEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NonNull;

import java.util.List;
import java.util.Objects;

public class ShieldAbility extends RPGUConstructableActiveAbility<PlayerInteractEvent> implements RPGUActiveTriggerableAbility<PlayerInteractEvent> {

    public static final ShieldAbility DEFAULT = new ShieldAbility();

    @Override
    protected @NotNull ActionResult action(@NotNull CustomItem customItem, @NotNull LivingEntity livingEntity, @NotNull UniversalInventorySlot universalInventorySlot, @NonNull PlayerInteractEvent entityShootBowEvent) {
        System.out.println("ShieldAb" + livingEntity.getName());
        return ActionResult.NO_COOLDOWN;
    }

    @Override
    public void onLeftClick(@NotNull CustomItem customItem, @NotNull PlayerInteractEvent event) {
        activate(customItem, event.getPlayer(), new BaseUniversalSlot(Objects.requireNonNull(event.getHand())), event);
    }

    @Override
    public @Nullable Pair<List<String>, List<String>> getEngAndRuDescription() {
        return Pair.of(List.of("Blocks all attacks up to 90 degrees"), List.of("Блокирует все атаки до 90 градусов"));
    }

    @Override
    public @NotNull CustomComponentType<? super RPGUActiveItem, ? extends CustomComponent<? super RPGUActiveItem>> getType() {
        return VanillaAbilities.SHIELD;
    }
}
