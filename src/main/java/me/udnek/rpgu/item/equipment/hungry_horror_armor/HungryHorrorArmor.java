package me.udnek.rpgu.item.equipment.hungry_horror_armor;

import io.papermc.paper.datacomponent.item.Equippable;
import me.udnek.coreu.custom.component.CustomComponent;
import me.udnek.coreu.custom.component.CustomComponentType;
import me.udnek.coreu.custom.equipment.slot.CustomEquipmentSlot;
import me.udnek.coreu.custom.equipment.universal.BaseUniversalSlot;
import me.udnek.coreu.custom.equipment.universal.UniversalInventorySlot;
import me.udnek.coreu.custom.item.ConstructableCustomItem;
import me.udnek.coreu.custom.item.CustomItem;
import me.udnek.coreu.rpgu.component.RPGUPassiveItem;
import me.udnek.coreu.rpgu.component.ability.passive.RPGUConstructablePassiveAbility;
import me.udnek.jeiu.component.HiddenItemComponent;
import me.udnek.rpgu.RpgU;
import me.udnek.rpgu.component.ability.Abilities;
import me.udnek.rpgu.mechanic.damaging.DamageEvent;
import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class HungryHorrorArmor extends ConstructableCustomItem {

    @Override
    public @Nullable List<ItemFlag> getTooltipHides() {return List.of(ItemFlag.HIDE_ATTRIBUTES);}

    @Override
    public @Nullable DataSupplier<Equippable> getEquippable() {
        Equippable build = Equippable.equippable(getMaterial().getEquipmentSlot()).assetId(new NamespacedKey(RpgU.getInstance(), "hungry_horror")).build();
        return DataSupplier.of(build);
    }

    @Override
    public void initializeComponents() {
        super.initializeComponents();
        getComponents().set(HiddenItemComponent.INSTANCE);
    }

    public static class Passive extends RPGUConstructablePassiveAbility<DamageEvent> {
        public static final Passive DEFAULT = new Passive(PotionEffectType.HUNGER, CustomEquipmentSlot.CHEST);

        protected final PotionEffectType effectType;
        protected final CustomEquipmentSlot slot;

        public Passive(@NotNull PotionEffectType effectType, @NotNull CustomEquipmentSlot slot){
            this.effectType = effectType;
            this.slot = slot;
        }

        @Override
        public @NotNull CustomComponentType<? super RPGUPassiveItem, ? extends CustomComponent<? super RPGUPassiveItem>> getType() {
            return Abilities.HUNGRY_HORROR;
        }

        @Override
        protected @NotNull ActionResult action(@NotNull CustomItem customItem, @NotNull LivingEntity livingEntity, @NotNull UniversalInventorySlot universalInventorySlot, @NotNull DamageEvent damageEvent) {
            if (!damageEvent.getDamageInstance().isCritical()) return ActionResult.NO_COOLDOWN;
            Entity abstractDamager = damageEvent.getDamageInstance().getDamager();
            if (!(abstractDamager instanceof LivingEntity damager)) return ActionResult.NO_COOLDOWN;

            PotionEffect potionEffect = damager.getPotionEffect(effectType);
            int applied;
            if (potionEffect == null) {
                applied = -1;
            }
            else {
                applied = potionEffect.getAmplifier();
            }
            damager.addPotionEffect(new PotionEffect(effectType, 40, Math.min(applied+1, 4), false, true));
            return ActionResult.FULL_COOLDOWN;
        }

        @Override
        public @Nullable Pair<List<String>, List<String>> getEngAndRuDescription() {
            return null;
        }

        @Override
        public @NotNull CustomEquipmentSlot getSlot() {
            return slot;
        }

        @Override
        public void tick(@NotNull CustomItem customItem, @NotNull Player player, @NotNull BaseUniversalSlot baseUniversalSlot, int i) {}
    }
}
