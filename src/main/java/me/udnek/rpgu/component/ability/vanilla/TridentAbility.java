package me.udnek.rpgu.component.ability.vanilla;

import com.destroystokyo.paper.event.player.PlayerLaunchProjectileEvent;
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
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.player.PlayerRiptideEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NonNull;

import java.util.List;

public class TridentAbility extends RPGUConstructableActiveAbility<PlayerLaunchProjectileEvent> implements RPGUActiveTriggerableAbility<PlayerLaunchProjectileEvent> {

    public static final TridentAbility DEFAULT = new TridentAbility();

    @Override
    protected @NotNull ActionResult action(@NotNull CustomItem customItem, @NotNull LivingEntity livingEntity, @NotNull UniversalInventorySlot universalInventorySlot, @NonNull PlayerLaunchProjectileEvent entityShootBowEvent) {
        return ActionResult.NO_COOLDOWN;
    }

    @Override
    public void onLaunchTrident(@NotNull CustomItem customItem, @NotNull PlayerLaunchProjectileEvent event) {
        Player player = event.getPlayer();
        if (player.getInventory().getItemInMainHand().equals(event.getItemStack())) {
            activate(customItem, player, new BaseUniversalSlot(EquipmentSlot.HAND), event);
        } else {
            activate(customItem, player, new BaseUniversalSlot(EquipmentSlot.OFF_HAND), event);
        }
    }

    @Override
    public @Nullable Pair<List<String>, List<String>> getEngAndRuDescription() {
        return Pair.of(List.of("Throws this item"), List.of("Метает этот предмет"));

    }

    @Override
    public void getEngAndRuProperties(TriConsumer<@NotNull String, @NotNull String, @NotNull List<Component>> Eng_Ru_Args) {
        super.getEngAndRuProperties(Eng_Ru_Args);
        Eng_Ru_Args.accept("Flight speed: %s blocks per second", "Скорость полёта: %s блоков в секунду", List.of(Component.text(50)));
        Eng_Ru_Args.accept("Damage when shot: %s", "Урон при выстреле: %s", List.of(Component.text(8)));
    }

    @Override
    public @NotNull CustomComponentType<? super RPGUActiveItem, ? extends CustomComponent<? super RPGUActiveItem>> getType() {
        return VanillaAbilities.TRIDENT;
    }
}
