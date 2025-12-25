package me.udnek.rpgu.item.equipment.hungry_horror_armor;

import me.udnek.coreu.custom.equipmentslot.slot.CustomEquipmentSlot;
import me.udnek.coreu.custom.equipmentslot.slot.SingleSlot;
import me.udnek.coreu.custom.item.CustomItem;
import me.udnek.rpgu.component.ConstructableEquippableItemComponent;
import me.udnek.rpgu.mechanic.damaging.DamageInstance;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

public class HungryHorrorComponent extends ConstructableEquippableItemComponent {
    protected final PotionEffectType effectType;
    protected final CustomEquipmentSlot slot;

    public HungryHorrorComponent(@NotNull PotionEffectType effectType, @NotNull CustomEquipmentSlot slot){
        this.effectType = effectType;
        this.slot = slot;
    }

    @Override
    public boolean isAppropriateSlot(@NotNull CustomEquipmentSlot slot) {
        return this.slot.intersects(slot);
    }

    @Override
    public void onPlayerAttacksWhenEquipped(@NotNull CustomItem item, @NotNull Player player, @NotNull SingleSlot slot, @NotNull DamageInstance damageInstance) {
        if (!damageInstance.isCritical()) return;

        PotionEffect potionEffect = player.getPotionEffect(effectType);
        int applied;
        if (potionEffect == null) {applied = -1;}
        else { applied = potionEffect.getAmplifier();}
        player.addPotionEffect(new PotionEffect(effectType, 40, Math.min(applied+1, 4), false, true));
    }
}