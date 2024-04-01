package me.udnek.rpgu.equipment;

import io.papermc.paper.event.player.PlayerInventorySlotChangeEvent;
import me.udnek.itemscoreu.utils.CustomItemUtils;
import me.udnek.itemscoreu.utils.SelfRegisteringListener;
import me.udnek.rpgu.item.abstracts.ArmorItem;
import me.udnek.rpgu.item.abstracts.ArtifactItem;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class EquipmentListener extends SelfRegisteringListener {
    public EquipmentListener(JavaPlugin plugin) {
        super(plugin);
    }

    @EventHandler
    public void onPlayerEquipsItem(PlayerInventorySlotChangeEvent event){
        int slot = event.getSlot();

        switch (slot){
            case 9:
            case 10:
            case 11:
            case 39:
            case 38:
            case 37:
            case 36:
                break;

            default:
                return;
        }

        if (CustomItemUtils.isSimilar(event.getOldItemStack(), event.getNewItemStack())) return;

        ItemStack itemStack;
        Player player = event.getPlayer();

        switch (slot){
            case 9:
            case 10:
            case 11:
                ArtifactItem artifactItem;

                itemStack = event.getOldItemStack();
                artifactItem = EquipmentUtils.artifactItem(itemStack);
                if (artifactItem != null){
                    artifactItem.onBeingUnequipped(player, itemStack);
                    PlayersEquipmentDatabase.get(player).setArtifact(slot, null);
                }


                itemStack = event.getNewItemStack();
                artifactItem = EquipmentUtils.artifactItem(itemStack);
                if (artifactItem != null){
                    artifactItem.onBeingEquipped(player, itemStack);
                    PlayersEquipmentDatabase.get(player).setArtifact(slot, artifactItem);
                }
                return;


            default:
                ArmorItem armorItem;

                itemStack = event.getOldItemStack();
                armorItem = EquipmentUtils.armorItem(itemStack);
                if (armorItem != null){
                    armorItem.onBeingUnequipped(player, itemStack);
                    PlayersEquipmentDatabase.get(player).setArmor(slot, null);
                }

                itemStack = event.getNewItemStack();
                armorItem = EquipmentUtils.armorItem(itemStack);
                if (armorItem != null){
                    armorItem.onBeingEquipped(player, itemStack);
                    PlayersEquipmentDatabase.get(player).setArmor(slot, armorItem);
                }
        }


    }
}
