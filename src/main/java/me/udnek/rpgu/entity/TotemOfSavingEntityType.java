package me.udnek.rpgu.entity;

import me.udnek.itemscoreu.customentity.CustomEntity;
import me.udnek.itemscoreu.customentity.CustomEntityType;
import me.udnek.rpgu.item.Items;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.List;
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
    public void playerDeath(PlayerDeathEvent event) {
        Player player = event.getPlayer();
        ItemStack foundTotem = null;
        List<ItemStack> drops = event.getDrops();
        for (ItemStack itemStack : drops) {
            if (!Items.TOTEM_OF_SAVING.isThisItem(itemStack)) continue;
            foundTotem = itemStack;
            break;
        }
        if (foundTotem == null) return;
        drops.remove(foundTotem);
        foundTotem.subtract(1);
        if (foundTotem.getAmount() > 0) drops.add(foundTotem);
        EntityTypes.TOTEM_OF_SAVING.spawn(player.getLocation()).setItems(drops);
        drops.clear();
    }

    @EventHandler
    public void onTotemDeath(EntityDeathEvent event){
        CustomEntity customEntity = CustomEntity.get(event.getEntity());
        if (customEntity == null || customEntity.getType() != EntityTypes.TOTEM_OF_SAVING) return;
        ((TotemOfSavingEntity) customEntity).onDeath(event);
    }

    @EventHandler
    public void playerInteractWithTotem(PlayerInteractEntityEvent event){
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
    @EventHandler
    public void onAgr(EntityTargetLivingEntityEvent event){
        LivingEntity target = event.getTarget();
        if (target == null) return;
        CustomEntity customEntity = CustomEntity.get(target);
        if (customEntity instanceof TotemOfSavingEntity) event.setCancelled(true);
    }
}
