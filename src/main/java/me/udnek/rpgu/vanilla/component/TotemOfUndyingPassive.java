package me.udnek.rpgu.vanilla.component;

import io.papermc.paper.datacomponent.item.DeathProtection;
import io.papermc.paper.datacomponent.item.consumable.ConsumeEffect;
import me.udnek.itemscoreu.customequipmentslot.CustomEquipmentSlot;
import me.udnek.itemscoreu.customitem.CustomItem;
import me.udnek.rpgu.component.ComponentTypes;
import me.udnek.rpgu.component.ability.passive.ConstructablePassiveAbilityComponent;
import me.udnek.rpgu.component.ability.property.AttributeBasedProperty;
import me.udnek.rpgu.component.ability.property.EffectsProperty;
import me.udnek.rpgu.lore.ability.PassiveAbilityLorePart;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityResurrectEvent;
import org.bukkit.potion.PotionEffect;
import org.jetbrains.annotations.NotNull;

public class TotemOfUndyingPassive extends ConstructablePassiveAbilityComponent<EntityResurrectEvent> {

    public TotemOfUndyingPassive(DeathProtection deathProtection){
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
        return CustomEquipmentSlot.ANY_VANILLA;///TODO ВСЕ СЛОТЫ
    }

    @Override
    public void addLoreLines(@NotNull PassiveAbilityLorePart componentable) {
        componentable.addAbilityDescription(Component.translatable("item.minecraft.totem_of_undying.passive_ability.0"));
        super.addLoreLines(componentable);
    }


    @Override
    public @NotNull ActionResult action(@NotNull CustomItem customItem, @NotNull LivingEntity livingEntity, @NotNull EntityResurrectEvent entityResurrectEvent) {
        return ActionResult.FULL_COOLDOWN;
    }

    @Override
    public void onDeath(@NotNull CustomItem customItem, @NotNull EntityResurrectEvent event) {
        activate(customItem, event.getEntity(), event, true);
    }
}
