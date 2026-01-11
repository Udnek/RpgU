package me.udnek.rpgu.component.ability.vanilla;

import io.papermc.paper.datacomponent.item.DeathProtection;
import io.papermc.paper.datacomponent.item.consumable.ConsumeEffect;
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
import me.udnek.coreu.rpgu.component.ability.property.EffectsProperty;
import me.udnek.rpgu.component.ability.RPGUPassiveTriggerableAbility;
import me.udnek.rpgu.component.ability.VanillaAbilities;
import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityResurrectEvent;
import org.bukkit.potion.PotionEffect;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class DeathProtectionPassive extends RPGUConstructablePassiveAbility<EntityResurrectEvent> implements RPGUPassiveTriggerableAbility<EntityResurrectEvent> {

    public static final DeathProtectionPassive DEFAULT = new DeathProtectionPassive(DeathProtection.deathProtection().build());

    public DeathProtectionPassive(@NotNull DeathProtection deathProtection){
        for (ConsumeEffect consumeEffect : deathProtection.deathEffects()) {
            if (!(consumeEffect instanceof ConsumeEffect.ApplyStatusEffects statusEffects)) continue;
            EffectsProperty effectsProperty = new EffectsProperty();
            for (PotionEffect effect : statusEffects.effects()) {
                effectsProperty.add(new EffectsProperty.PotionData(effect));
            }
            getComponents().set(effectsProperty);
        }
        getComponents().set(new AttributeBasedProperty(40 * 20, RPGUComponents.ABILITY_COOLDOWN_TIME));
    }

    @Override
    public @NotNull CustomEquipmentSlot getSlot() {
        return CustomEquipmentSlot.DUMB_INVENTORY;
    }

    @Override
    protected @NotNull ActionResult action(@NotNull CustomItem customItem, @NotNull LivingEntity livingEntity, @NotNull UniversalInventorySlot slot, @NotNull EntityResurrectEvent entityResurrectEvent) {
        if (entityResurrectEvent.isCancelled()) {slot.addItem(-1, livingEntity);}
        entityResurrectEvent.setCancelled(false);
        return ActionResult.FULL_COOLDOWN;
    }

    @Override
    public void onResurrect(@NotNull CustomItem customItem, @NotNull UniversalInventorySlot slot, boolean activatedBefore,
                            @NotNull EntityResurrectEvent event) {
        if (!activatedBefore) activate(customItem, event.getEntity(), true, slot, event);
    }

    @Override
    public void tick(@NotNull CustomItem customItem, @NotNull Player player, @NotNull BaseUniversalSlot slot, int tickDelay) {}

    @Override
    public @Nullable Pair<List<String>, List<String>> getEngAndRuDescription() {
        return Pair.of(List.of("Saves from death"), List.of("Спасает от смерти"));
    }

    @Override
    public @NotNull CustomComponentType<? super RPGUPassiveItem, ? extends CustomComponent<? super RPGUPassiveItem>> getType() {
        return VanillaAbilities.DEATH_PROTECTION;
    }
}
