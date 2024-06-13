package me.udnek.rpgu.equipment;

import me.udnek.itemscoreu.customitem.CustomItem;
import me.udnek.rpgu.item.abstraction.ArmorItem;
import me.udnek.rpgu.item.abstraction.ArtifactItem;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class EquipmentUtils {
    public static ArmorItem armorItem(ItemStack itemStack){
        CustomItem customItem = CustomItem.get(itemStack);
        if (customItem instanceof ArmorItem) return (ArmorItem) customItem;
        return null;
    }

    public static ArtifactItem artifactItem(ItemStack itemStack){
        CustomItem customItem = CustomItem.get(itemStack);
        if (customItem instanceof ArtifactItem) return (ArtifactItem) customItem;
        return null;
    }

    public static boolean isItemEquippedAsArtifact(Player player, ArtifactItem artifactItem){
        return PlayerEquipmentDatabase.get(player).isEquippedAsArtifact(artifactItem);
    }
    public static boolean isItemEquippedAsArmor(Player player, ArmorItem armorItem){
        return PlayerEquipmentDatabase.get(player).isEquippedAsArmor(armorItem);
    }
}
