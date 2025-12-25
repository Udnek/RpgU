package me.udnek.rpgu.component.ability.instance;

import me.udnek.coreu.custom.component.CustomComponent;
import me.udnek.coreu.custom.component.CustomComponentMap;
import me.udnek.coreu.custom.component.CustomComponentType;
import me.udnek.coreu.custom.equipmentslot.slot.CustomEquipmentSlot;
import me.udnek.coreu.custom.equipmentslot.universal.BaseUniversalSlot;
import me.udnek.coreu.custom.equipmentslot.universal.UniversalInventorySlot;
import me.udnek.coreu.custom.item.CustomItem;
import me.udnek.coreu.rpgu.component.RPGUComponents;
import me.udnek.coreu.rpgu.component.RPGUPassiveItem;
import me.udnek.coreu.rpgu.component.ability.passive.RPGUConstructablePassiveAbility;
import me.udnek.coreu.rpgu.component.ability.property.AttributeBasedProperty;
import me.udnek.coreu.rpgu.component.ability.property.EffectsProperty;
import me.udnek.rpgu.component.ability.Abilities;
import me.udnek.rpgu.effect.Effects;
import me.udnek.rpgu.util.Utils;
import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Tameable;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.List;

public class AllyBuffingAuraPassive extends RPGUConstructablePassiveAbility<Integer> {

    public static final AllyBuffingAuraPassive DEFAULT = new AllyBuffingAuraPassive(CustomEquipmentSlot.CHEST){
        @Override
        public void tick(@NotNull CustomItem customItem, @NotNull Player player, @NotNull BaseUniversalSlot baseUniversalSlot, int i) {}
    };

    public static void applyGoldenArmorBuff(@NotNull Material material, @NotNull CustomItem customItem){
        EquipmentSlot equipmentSlot = material.getEquipmentSlot();
        CustomComponentMap<CustomItem> components = customItem.getComponents();
        int duration = 3 * 20;

        if (equipmentSlot == EquipmentSlot.HEAD) {
            AllyBuffingAuraPassive passiveAbility = new AllyBuffingAuraPassive(CustomEquipmentSlot.HEAD);
            passiveAbility.getComponents().set(new EffectsProperty(new EffectsProperty.PotionData(
                    Effects.MIRE_TOUCH.getBukkitType(),
                    duration,
                    1
            )));
            components.getOrCreateDefault(RPGUComponents.PASSIVE_ABILITY_ITEM).getComponents().set(passiveAbility);
        }
        else if (equipmentSlot == EquipmentSlot.CHEST) {
            AllyBuffingAuraPassive passiveAbility = new AllyBuffingAuraPassive(CustomEquipmentSlot.CHEST);
            passiveAbility.getComponents().set(new EffectsProperty(new EffectsProperty.PotionData(
                    PotionEffectType.REGENERATION,
                    duration,
                    0
            )));
            components.getOrCreateDefault(RPGUComponents.PASSIVE_ABILITY_ITEM).getComponents().set(passiveAbility);
        }
        else if (equipmentSlot == EquipmentSlot.LEGS) {
            AllyBuffingAuraPassive passiveAbility = new AllyBuffingAuraPassive(CustomEquipmentSlot.LEGS);
            passiveAbility.getComponents().set(new EffectsProperty(new EffectsProperty.PotionData(
                    Effects.MAGIC_RESISTANCE.getBukkitType(),
                    duration,
                    1
            ),new EffectsProperty.PotionData(
                    PotionEffectType.RESISTANCE,
                    duration,
                    1
            )));
            components.getOrCreateDefault(RPGUComponents.PASSIVE_ABILITY_ITEM).getComponents().set(passiveAbility);
        }
        else if (equipmentSlot == EquipmentSlot.FEET) {
            AllyBuffingAuraPassive passiveAbility = new AllyBuffingAuraPassive(CustomEquipmentSlot.FEET);
            passiveAbility.getComponents().set(new EffectsProperty(new EffectsProperty.PotionData(
                    PotionEffectType.SPEED,
                    duration,
                    1
            )));
            components.getOrCreateDefault(RPGUComponents.PASSIVE_ABILITY_ITEM).getComponents().set(passiveAbility);
        }
    }

    protected CustomEquipmentSlot slot;

    public AllyBuffingAuraPassive(@NotNull CustomEquipmentSlot slot){
        this.slot = slot;
        getComponents().set(new AttributeBasedProperty(10, RPGUComponents.ABILITY_AREA_OF_EFFECT));
    }

    @Override
    public @NotNull CustomEquipmentSlot getSlot() {return slot;}

    @Override
    protected @NotNull ActionResult action(@NotNull CustomItem customItem, @NotNull LivingEntity livingEntity, @NotNull UniversalInventorySlot universalInventorySlot, @NotNull Integer integer) {
        Collection<LivingEntity> livingEntitiesInRadius = Utils.livingEntitiesInRadius(
                livingEntity.getLocation(),
                getComponents().getOrException(RPGUComponents.ABILITY_AREA_OF_EFFECT).get(livingEntity)
        );
        for (LivingEntity livingEntityInRadius: livingEntitiesInRadius){
            if (!(livingEntityInRadius instanceof Tameable tameable)) continue;
            if (livingEntity == tameable.getOwner()) {
                getComponents().getOrException(RPGUComponents.ABILITY_EFFECTS).applyOn(livingEntity, tameable);
            }
        }
        return ActionResult.FULL_COOLDOWN;
    }

    @Override
    public @Nullable Pair<List<String>, List<String>> getEngAndRuDescription() {
        return null;
    }

    @Override
    public void tick(@NotNull CustomItem customItem, @NotNull Player player, @NotNull BaseUniversalSlot baseUniversalSlot, int i) {
        activate(customItem, player, baseUniversalSlot, i);
    }

    @Override
    public @NotNull CustomComponentType<? super RPGUPassiveItem, ? extends CustomComponent<? super RPGUPassiveItem>> getType() {
        return Abilities.ALLY_BUFFING_AURA;
    }
}