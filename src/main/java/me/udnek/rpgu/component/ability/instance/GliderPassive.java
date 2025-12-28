package me.udnek.rpgu.component.ability.instance;

import com.google.common.base.Preconditions;
import io.papermc.paper.datacomponent.DataComponentTypes;
import me.udnek.coreu.custom.component.CustomComponent;
import me.udnek.coreu.custom.component.CustomComponentType;
import me.udnek.coreu.custom.equipment.slot.CustomEquipmentSlot;
import me.udnek.coreu.custom.equipment.universal.BaseUniversalSlot;
import me.udnek.coreu.custom.equipment.universal.UniversalInventorySlot;
import me.udnek.coreu.custom.item.CustomItem;
import me.udnek.coreu.rpgu.component.RPGUComponents;
import me.udnek.coreu.rpgu.component.RPGUPassiveItem;
import me.udnek.coreu.rpgu.component.ability.passive.RPGUConstructablePassiveAbility;
import me.udnek.coreu.rpgu.component.ability.property.AttributeBasedProperty;
import me.udnek.rpgu.component.ability.Abilities;
import me.udnek.rpgu.component.ability.RPGUPassiveTriggerableAbility;
import me.udnek.rpgu.mechanic.damaging.DamageEvent;
import me.udnek.rpgu.mechanic.damaging.DamageInstance;
import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityToggleGlideEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NonNull;

import java.util.List;
import java.util.Objects;

public class GliderPassive extends RPGUConstructablePassiveAbility<DamageEvent> implements RPGUPassiveTriggerableAbility<DamageEvent> {

    public static final GliderPassive DEFAULT = new GliderPassive(CustomEquipmentSlot.CHEST, 0);

    public static @NotNull GliderPassive of(@NotNull ItemStack itemStack, int cooldown){
        return new GliderPassive( CustomEquipmentSlot.getFromVanilla(Preconditions.checkNotNull(itemStack.getData(DataComponentTypes.EQUIPPABLE),
                "Item doesn't have EQUIPPABLE Component", itemStack).slot()), cooldown);
    }

    protected CustomEquipmentSlot slot;

    public GliderPassive(@NotNull CustomEquipmentSlot slot, int cooldown){
        this.slot = slot;
        getComponents().set(new AttributeBasedProperty(cooldown, RPGUComponents.ABILITY_COOLDOWN_TIME));
    }

    @Override
    public @NotNull CustomEquipmentSlot getSlot() {
        return slot;
    }

    @Override
    public void tick(@NotNull CustomItem customItem, @NotNull Player player, @NotNull BaseUniversalSlot baseUniversalSlot, int i) {}

    @Override
    protected @NotNull ActionResult action(@NotNull CustomItem customItem, @NotNull LivingEntity livingEntity, @NotNull UniversalInventorySlot universalInventorySlot, @NonNull DamageEvent damageEvent) {
        DamageInstance damageInstance = damageEvent.getDamageInstance();
        if (!(damageInstance.getDamager() instanceof Player || damageInstance.getCausingDamager() instanceof Player)) return ActionResult.NO_COOLDOWN;
        if (livingEntity.isGliding()) livingEntity.setGliding(false);
        return ActionResult.FULL_COOLDOWN;
    }

    @Override
    public void onDamageReceived(@NotNull CustomItem customItem, @NotNull UniversalInventorySlot slot, @NotNull DamageEvent event) {
        activate(customItem, (LivingEntity) Objects.requireNonNull(event.getDamageInstance().getVictim()), slot, event);
    }

    public void onToggleGlide(@NotNull CustomItem customItem, @NotNull UniversalInventorySlot slot, @NotNull EntityToggleGlideEvent event) {
        if (!event.isGliding()) return;
        Player player = (Player) event.getEntity();
        if (customItem.getCooldown(player) != 0) event.setCancelled(true);
    }

    @Override
    public @Nullable Pair<List<String>, List<String>> getEngAndRuDescription() {
        return null;
    }

    @Override
    public @NotNull CustomComponentType<? super RPGUPassiveItem, ? extends CustomComponent<? super RPGUPassiveItem>> getType() {
        return Abilities.GLIDER;
    }
}
