package me.udnek.rpgu.component.instance;

import me.udnek.itemscoreu.customcomponent.CustomComponentMap;
import me.udnek.itemscoreu.customequipmentslot.CustomEquipmentSlot;
import me.udnek.itemscoreu.customequipmentslot.SingleSlot;
import me.udnek.itemscoreu.customequipmentslot.UniversalInventorySlot;
import me.udnek.itemscoreu.customitem.CustomItem;
import me.udnek.itemscoreu.util.Either;
import me.udnek.rpgu.component.ComponentTypes;
import me.udnek.rpgu.component.ability.passive.ConstructablePassiveAbility;
import me.udnek.rpgu.component.ability.property.AttributeBasedProperty;
import me.udnek.rpgu.component.ability.property.EffectsProperty;
import me.udnek.rpgu.effect.Effects;
import me.udnek.rpgu.lore.ability.PassiveAbilityLorePart;
import me.udnek.rpgu.util.Utils;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Tameable;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

public class GoldenArmorPassive {

    public static void applyPassive(@NotNull Material material, @NotNull CustomItem customItem){
        EquipmentSlot equipmentSlot = material.getEquipmentSlot();
        CustomComponentMap<CustomItem> components = customItem.getComponents();
        int duration = 3 * 20;

        if (equipmentSlot == EquipmentSlot.HEAD) {
            PassiveAbilityGoldenArmor passiveAbility = new PassiveAbilityGoldenArmor(CustomEquipmentSlot.HEAD);
            components.getOrCreateDefault(ComponentTypes.EQUIPPABLE_ITEM).addPassive(passiveAbility);
            passiveAbility.getComponents().set(new EffectsProperty(new EffectsProperty.PotionData(
                    Effects.MIRE_TOUCH.getBukkitType(),
                    duration,
                    1
            )));
        }
        if (equipmentSlot == EquipmentSlot.CHEST) {
            PassiveAbilityGoldenArmor passiveAbility = new PassiveAbilityGoldenArmor(CustomEquipmentSlot.CHEST);
            components.getOrCreateDefault(ComponentTypes.EQUIPPABLE_ITEM).addPassive(passiveAbility);
            passiveAbility.getComponents().set(new EffectsProperty(new EffectsProperty.PotionData(
                    PotionEffectType.REGENERATION,
                    duration,
                    0
            )));
        }
        if (equipmentSlot == EquipmentSlot.LEGS) {
            PassiveAbilityGoldenArmor passiveAbility = new PassiveAbilityGoldenArmor(CustomEquipmentSlot.LEGS);
            components.getOrCreateDefault(ComponentTypes.EQUIPPABLE_ITEM).addPassive(passiveAbility);
            passiveAbility.getComponents().set(new EffectsProperty(new EffectsProperty.PotionData(
                    Effects.MAGICAL_RESISTANCE.getBukkitType(),
                    duration,
                    1
            ),new EffectsProperty.PotionData(
                    PotionEffectType.RESISTANCE,
                    duration,
                    1
            )));
        }
        if (equipmentSlot == EquipmentSlot.FEET) {
            PassiveAbilityGoldenArmor passiveAbility = new PassiveAbilityGoldenArmor(CustomEquipmentSlot.FEET);
            components.getOrCreateDefault(ComponentTypes.EQUIPPABLE_ITEM).addPassive(passiveAbility);
            passiveAbility.getComponents().set(new EffectsProperty(new EffectsProperty.PotionData(
                    PotionEffectType.SPEED,
                    duration,
                    1
            )));
        }
    }

    static class PassiveAbilityGoldenArmor extends ConstructablePassiveAbility<Object>  {
        CustomEquipmentSlot slot;

        PassiveAbilityGoldenArmor(@NotNull CustomEquipmentSlot slots){
            this.slot = slots;

            getComponents().set(new AttributeBasedProperty(10, ComponentTypes.ABILITY_AREA_OF_EFFECT));
        }

        @Override
        public void addLoreLines(@NotNull PassiveAbilityLorePart componentable) {
            componentable.addAbilityDescription(Component.translatable("item.minecraft.golden_armor.passive_ability.0"));
            super.addLoreLines(componentable);
        }

        @Override
        public @NotNull CustomEquipmentSlot getSlot() {return slot;}

        @Override
        public @NotNull ActionResult action(@NotNull CustomItem customItem, @NotNull LivingEntity livingEntity, @NotNull Either<UniversalInventorySlot, SingleSlot> slot,
                                            @NotNull Object object) {
            Collection<LivingEntity> livingEntitiesInRadius = Utils.livingEntitiesInRadius(livingEntity.getLocation(), getComponents().getOrException(ComponentTypes.ABILITY_AREA_OF_EFFECT).get(livingEntity));
            for (LivingEntity livingEntityInRadius: livingEntitiesInRadius){
                if (!(livingEntityInRadius instanceof Tameable tameable)) continue;
                if (livingEntity == tameable.getOwner()) {
                    getComponents().getOrException(ComponentTypes.ABILITY_EFFECTS).applyOn(livingEntity, tameable);
                }
            }
            return ActionResult.FULL_COOLDOWN;
        }

        @Override
        public void tick(@NotNull CustomItem customItem, @NotNull LivingEntity livingEntity, @NotNull SingleSlot slot) {
            activate(customItem, livingEntity, new Either<>(null,  slot), new Object());
        }
    }
}