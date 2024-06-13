package me.udnek.rpgu.item.abstraction;

import me.udnek.itemscoreu.customattribute.CustomAttributesContainer;
import me.udnek.itemscoreu.customattribute.DefaultCustomAttributeHolder;
import me.udnek.rpgu.damaging.DamageEvent;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public abstract class EquippableItem extends RpgUCustomItem implements DefaultCustomAttributeHolder {
    // TODO: 6/13/2024 ADD INIT DEFAULT DefaultCustomAttributeHolder

    public abstract boolean isEquippedInAppropriateSlot(Player player);
    public void onEquipped(Player player, ItemStack itemStack){}
    public void onUnequipped(Player player, ItemStack itemStack){}
    public void tickBeingEquipped(Player player){}
    public void onPlayerAttacksWhenEquipped(Player player, DamageEvent event){}
    public void onPlayerReceivesDamageWhenEquipped(Player player, DamageEvent event){}

    @Override
    public CustomAttributesContainer getDefaultCustomAttributes() {return null;}
}
