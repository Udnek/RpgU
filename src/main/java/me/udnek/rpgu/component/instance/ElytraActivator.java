package me.udnek.rpgu.component.instance;

import me.udnek.itemscoreu.customequipmentslot.slot.CustomEquipmentSlot;
import me.udnek.itemscoreu.customequipmentslot.slot.SingleSlot;
import me.udnek.itemscoreu.customitem.CustomItem;
import me.udnek.rpgu.mechanic.damaging.DamageInstance;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ElytraActivator extends CloseActivator{
    public ElytraActivator(CustomEquipmentSlot equipmentSlot, int cooldown) {
        super(equipmentSlot, cooldown);
    }

    @Override
    public void onPlayerReceivesDamageWhenEquipped(@NotNull CustomItem item, @NotNull Player player, @NotNull SingleSlot slot, @NotNull DamageInstance damageInstance) {
        if (!(damageInstance.getDamager() instanceof Player || damageInstance.getCausingDamager() instanceof Player)) return;
        super.onPlayerReceivesDamageWhenEquipped(item, player, slot, damageInstance);
        if (player.isGliding()){
            player.setGliding(false);
        }
    }
}
