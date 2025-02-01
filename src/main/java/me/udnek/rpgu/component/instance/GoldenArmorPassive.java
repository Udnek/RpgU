package me.udnek.rpgu.component.instance;

import me.udnek.itemscoreu.customcomponent.CustomComponentMap;
import me.udnek.itemscoreu.customequipmentslot.CustomEquipmentSlot;
import me.udnek.itemscoreu.customitem.CustomItem;
import me.udnek.rpgu.component.ComponentTypes;
import me.udnek.rpgu.component.PassiveAbilityActivatorComponent;
import me.udnek.rpgu.component.ability.passive.ConstructablePassiveAbilityComponent;
import me.udnek.rpgu.component.ability.passive.EquippableActivatablePassiveComponent;
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
            components.set(new Ability(CustomEquipmentSlot.HEAD));
            PassiveAbilityGoldenArmor passiveAbility = new PassiveAbilityGoldenArmor(CustomEquipmentSlot.HEAD);
            components.set(passiveAbility);
            passiveAbility.getComponents().set(new EffectsProperty(new EffectsProperty.PotionData(
                    Effects.MIRE_TOUCH.getBukkitType(),
                    duration,
                    1
            )));
        }
        if (equipmentSlot == EquipmentSlot.CHEST) {
            components.set(new Ability(CustomEquipmentSlot.CHEST));
            PassiveAbilityGoldenArmor passiveAbility = new PassiveAbilityGoldenArmor(CustomEquipmentSlot.CHEST);
            components.set(passiveAbility);
            passiveAbility.getComponents().set(new EffectsProperty(new EffectsProperty.PotionData(
                    PotionEffectType.REGENERATION,
                    duration,
                    0
            )));
        }
        if (equipmentSlot == EquipmentSlot.LEGS) {
            components.set(new Ability(CustomEquipmentSlot.LEGS));
            PassiveAbilityGoldenArmor passiveAbility = new PassiveAbilityGoldenArmor(CustomEquipmentSlot.LEGS);
            components.set(passiveAbility);
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
            components.set(new Ability(CustomEquipmentSlot.FEET));
            PassiveAbilityGoldenArmor passiveAbility = new PassiveAbilityGoldenArmor(CustomEquipmentSlot.FEET);
            components.set(passiveAbility);
            passiveAbility.getComponents().set(new EffectsProperty(new EffectsProperty.PotionData(
                    PotionEffectType.SPEED,
                    duration,
                    1
            )));
        }
    }

    static class Ability extends PassiveAbilityActivatorComponent{
        CustomEquipmentSlot equipmentSlot;
        Ability(CustomEquipmentSlot equipmentSlot) {this.equipmentSlot = equipmentSlot;}

        @Override
        public int getTickRate() {return 10;}

        @Override
        public boolean isAppropriateSlot(@NotNull CustomEquipmentSlot slot) {return equipmentSlot.test(slot);}
    }

    static class PassiveAbilityGoldenArmor extends ConstructablePassiveAbilityComponent<Object> implements EquippableActivatablePassiveComponent {
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
        public @NotNull ActionResult action(@NotNull CustomItem customItem, @NotNull LivingEntity livingEntity, @NotNull Object object) {
            Collection<LivingEntity> livingEntitiesInRadius = Utils.livingEntitiesInRadius(livingEntity.getLocation(), getComponents().getOrException(ComponentTypes.ABILITY_AREA_OF_EFFECT).get(livingEntity));
            for (LivingEntity livingEntityInRadius: livingEntitiesInRadius){
                if (!(livingEntityInRadius instanceof Tameable tameable)) continue;
                if (livingEntity == tameable.getOwner()) {
                    getComponents().getOrException(ComponentTypes.ABILITY_EFFECTS).applyOn(livingEntity, tameable);
                }
            }
            return ActionResult.FULL_COOLDOWN;
        }
    }
}