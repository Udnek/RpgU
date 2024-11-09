package me.udnek.rpgu.item.equipment.hungry_horror_armor;

import me.udnek.itemscoreu.customequipmentslot.CustomEquipmentSlot;
import me.udnek.itemscoreu.customitem.CustomItem;
import me.udnek.rpgu.component.EquippableItemComponent;
import me.udnek.rpgu.mechanic.damaging.DamageInstance;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

public class HungryHorrorComponent implements EquippableItemComponent {
    protected final PotionEffectType effectType;
    protected final CustomEquipmentSlot slot;

    public HungryHorrorComponent(PotionEffectType effectType, CustomEquipmentSlot slot){
        this.effectType = effectType;
        this.slot = slot;
    }

    @Override
    public boolean isAppropriateSlot(@NotNull CustomEquipmentSlot slot) {
        return slot.test(slot);
    }

    @Override
    public void onPlayerAttacksWhenEquipped(@NotNull CustomItem item, @NotNull Player player, @NotNull CustomEquipmentSlot slot, @NotNull DamageInstance damageInstance) {
        if (!damageInstance.isCritical()) return;

        PotionEffect potionEffect = player.getPotionEffect(effectType);
        int applied;
        if (potionEffect == null) {applied = -1;}
        else { applied = potionEffect.getAmplifier();}
        player.addPotionEffect(new PotionEffect(effectType, 40, Math.min(applied+1, 4), false, true));
    }
}