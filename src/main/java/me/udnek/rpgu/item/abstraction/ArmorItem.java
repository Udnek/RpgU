package me.udnek.rpgu.item.abstraction;

import me.udnek.rpgu.equipment.PlayersEquipmentDatabase;
import org.bukkit.entity.Player;

public abstract class ArmorItem extends EquippableItem{
    @Override
    public boolean isEquippedInAppropriateSlot(Player player) {
        return PlayersEquipmentDatabase.get(player).isEquippedAsArmor(this);
    }
}
