package me.udnek.rpgu.item.abstraction;

import me.udnek.rpgu.equipment.PlayerEquipmentDatabase;
import org.bukkit.entity.Player;

public abstract class ArmorItem extends EquippableItem{
    @Override
    public boolean isEquippedInAppropriateSlot(Player player) {
        return PlayerEquipmentDatabase.get(player).isEquippedAsArmor(this);
    }
}
