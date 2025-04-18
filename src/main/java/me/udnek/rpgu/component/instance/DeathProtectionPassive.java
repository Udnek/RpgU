package me.udnek.rpgu.component.instance;

import io.papermc.paper.datacomponent.item.DeathProtection;
import io.papermc.paper.datacomponent.item.consumable.ConsumeEffect;
import me.udnek.itemscoreu.customequipmentslot.slot.CustomEquipmentSlot;
import me.udnek.itemscoreu.customequipmentslot.slot.SingleSlot;
import me.udnek.itemscoreu.customequipmentslot.universal.UniversalInventorySlot;
import me.udnek.itemscoreu.customitem.CustomItem;
import me.udnek.itemscoreu.util.Either;
import me.udnek.rpgu.component.ComponentTypes;
import me.udnek.rpgu.component.ability.passive.ConstructablePassiveAbility;
import me.udnek.rpgu.component.ability.property.AttributeBasedProperty;
import me.udnek.rpgu.component.ability.property.EffectsProperty;
import me.udnek.rpgu.lore.ability.PassiveAbilityLorePart;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityResurrectEvent;
import org.bukkit.potion.PotionEffect;
import org.jetbrains.annotations.NotNull;

public class DeathProtectionPassive extends ConstructablePassiveAbility<EntityResurrectEvent> {

    public DeathProtectionPassive(DeathProtection deathProtection){
        for (ConsumeEffect consumeEffect : deathProtection.deathEffects()) {
            if (!(consumeEffect instanceof ConsumeEffect.ApplyStatusEffects statusEffects)) continue;
            EffectsProperty effectsProperty = new EffectsProperty();
            for (PotionEffect effect : statusEffects.effects()) {
                effectsProperty.add(new EffectsProperty.PotionData(effect));
            }
            getComponents().set(effectsProperty);
        }
        getComponents().set(new AttributeBasedProperty(40 * 20, ComponentTypes.ABILITY_COOLDOWN));
    }

    @Override
    public @NotNull CustomEquipmentSlot getSlot() {
        return CustomEquipmentSlot.DUMB_INVENTORY;
    }

    @Override
    public void addLoreLines(@NotNull PassiveAbilityLorePart componentable) {
        componentable.addAbilityDescription(Component.translatable("item.minecraft.totem_of_undying.passive_ability.0"));
        super.addLoreLines(componentable);
    }


    @Override
    public @NotNull ActionResult action(@NotNull CustomItem customItem, @NotNull LivingEntity livingEntity, @NotNull Either<UniversalInventorySlot, SingleSlot> slot,
                                        @NotNull EntityResurrectEvent entityResurrectEvent) {
        if (entityResurrectEvent.isCancelled()) {slot.getLeft().addItem(-1, livingEntity);}
        entityResurrectEvent.setCancelled(false);
        return ActionResult.FULL_COOLDOWN;
    }

    @Override
    public void onResurrect(@NotNull CustomItem customItem, @NotNull UniversalInventorySlot slot, boolean activatedBefore,
                            @NotNull EntityResurrectEvent event) {
        if (!activatedBefore) activate(customItem, event.getEntity(), true, new Either<>(slot, null), event);
    }
}
