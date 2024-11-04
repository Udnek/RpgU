package me.udnek.rpgu.entity;

import me.udnek.itemscoreu.customentity.CustomEntity;
import me.udnek.itemscoreu.customentity.CustomEntityType;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.function.Consumer;

class TotemOfSavingEntityType extends CustomEntityType<TotemOfSavingEntity> implements Listener {
    public TotemOfSavingEntityType() {
        super("totem_of_saving");
    }

    @Override
    protected TotemOfSavingEntity getNewCustomEntityClass() {
        return new TotemOfSavingEntity();
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        EntityTypes.TOTEM_OF_SAVING.spawn(event.getPlayer().getLocation()).setItems(event.getDrops());
        event.getDrops().clear();
    }

    @EventHandler
    public void playerInteract(PlayerInteractAtEntityEvent event){
        CustomEntity customEntity = CustomEntity.get(event.getRightClicked());
        if (!(customEntity instanceof TotemOfSavingEntity totemOfSavingEntity)) return;
        PlayerInventory inventory = event.getPlayer().getInventory();
        Location location = event.getPlayer().getLocation();
        totemOfSavingEntity.getItems().forEach(new Consumer<ItemStack>() {
            @Override
            public void accept(ItemStack itemStack) {
                EquipmentSlot slot;
                if (itemStack.getItemMeta().hasEquippable()) slot = itemStack.getItemMeta().getEquippable().getSlot();
                else slot = itemStack.getType().getEquipmentSlot();
                if (slot != EquipmentSlot.HAND && inventory.getItem(slot).getType() == Material.AIR){
                    inventory.setItem(slot, itemStack);
                } else if (inventory.firstEmpty() != -1){
                    inventory.addItem(itemStack);
                } else {
                    location.getWorld().dropItem(location, itemStack);
                }

            }
        });
        customEntity.remove();
    }
}
