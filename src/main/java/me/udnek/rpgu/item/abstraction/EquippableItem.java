package me.udnek.rpgu.item.abstraction;

import me.udnek.itemscoreu.customattribute.CustomAttributesContainer;
import me.udnek.itemscoreu.customattribute.DefaultCustomAttributeHolder;
import me.udnek.rpgu.damaging.DamageEvent;
import me.udnek.rpgu.equipment.Equippable;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public abstract class EquippableItem extends RpgUCustomItem implements Equippable, DefaultCustomAttributeHolder {
    // TODO: 6/13/2024 ADD INIT DEFAULT DefaultCustomAttributeHolder
    abstract boolean isEquippedInAppropriateSlot(Player player);
    @Override
    public CustomAttributesContainer getDefaultCustomAttributes() {return null;}
}
