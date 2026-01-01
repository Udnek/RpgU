package me.udnek.rpgu.component.ability.vanilla;

import io.papermc.paper.event.player.PlayerShieldDisableEvent;
import me.udnek.coreu.custom.component.CustomComponent;
import me.udnek.coreu.custom.component.CustomComponentType;
import me.udnek.coreu.custom.equipment.universal.BaseUniversalSlot;
import me.udnek.coreu.custom.equipment.universal.UniversalInventorySlot;
import me.udnek.coreu.custom.item.CustomItem;
import me.udnek.coreu.rpgu.component.RPGUActiveItem;
import me.udnek.coreu.rpgu.component.ability.active.RPGUConstructableActiveAbility;
import me.udnek.rpgu.component.ability.RPGUActiveTriggerableAbility;
import me.udnek.rpgu.component.ability.VanillaAbilities;
import net.kyori.adventure.text.Component;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.util.TriConsumer;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.EquipmentSlot;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NonNull;

import java.util.List;

public class ShieldCrashingAbility extends RPGUConstructableActiveAbility<PlayerShieldDisableEvent> implements RPGUActiveTriggerableAbility<PlayerShieldDisableEvent> {

    public static final ShieldCrashingAbility DEFAULT = new ShieldCrashingAbility();

    @Override
    protected @NotNull ActionResult action(@NotNull CustomItem customItem, @NotNull LivingEntity livingEntity, @NotNull UniversalInventorySlot universalInventorySlot, @NonNull PlayerShieldDisableEvent playerShieldDisableEvent) {
        return ActionResult.NO_COOLDOWN;
    }

    @Override
    public void onShieldBreak(@NotNull CustomItem customItem, @NotNull PlayerShieldDisableEvent event) {
        activate(customItem, (LivingEntity) event.getDamager(), new BaseUniversalSlot(EquipmentSlot.HAND), event);
    }

    @Override
    public @Nullable Pair<List<String>, List<String>> getEngAndRuDescription() {
        return Pair.of(List.of("Charged attacks disable the shield"), List.of("Заряженные атаки отключает щит"));
    }

    @Override
    public void getEngAndRuProperties(TriConsumer<@NotNull String, @NotNull String, @NotNull List<Component>> Eng_Ru_Args) {
        super.getEngAndRuProperties(Eng_Ru_Args);
        Eng_Ru_Args.accept("Disable time: %s seconds", "Сокрушительные удары: %s секунд", List.of(Component.text(5)));
    }

    @Override
    public @NotNull CustomComponentType<? super RPGUActiveItem, ? extends CustomComponent<? super RPGUActiveItem>> getType() {
        return VanillaAbilities.SHIELD_CRASHING;
    }
}