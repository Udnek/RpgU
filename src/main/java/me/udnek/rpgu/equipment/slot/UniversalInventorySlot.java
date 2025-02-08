package me.udnek.rpgu.equipment.slot;

import com.google.common.base.Preconditions;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class UniversalInventorySlot {
    private Integer slot = null;
    private EquipmentSlot equipmentSlot = null;

    public UniversalInventorySlot(int slot){
        this.slot = slot;
    }

    public UniversalInventorySlot(@NotNull EquipmentSlot slot){
        this.equipmentSlot = slot;
    }

    public @Nullable ItemStack getItem(@NotNull LivingEntity entity) {
        if (slot != null && entity instanceof InventoryHolder inventoryHolder && 0 <= slot && slot <= inventoryHolder.getInventory().getSize()){
            return inventoryHolder.getInventory().getItem(slot);
        } else if (equipmentSlot != null) {
            if (entity instanceof Player player){
                return player.getInventory().getItem(equipmentSlot);
            }else  {
                EntityEquipment equipment = entity.getEquipment();
                if (equipment == null) return null;
                return equipment.getItem(equipmentSlot);
            }
        }
        return null;
    }

    public void setItem(@Nullable ItemStack itemStack, @NotNull LivingEntity entity) {
        if (slot != null){
            if (entity instanceof InventoryHolder inventoryHolder){
                if (0 <= slot && slot <= inventoryHolder.getInventory().getSize()) {
                    inventoryHolder.getInventory().setItem(slot, itemStack);
                }else {
                    throw new RuntimeException("Incorrect slot for the inventoryHolder");
                }
            }else {
                throw new RuntimeException("The integer slot is only available to the inventoryHolder");

            }
        } else if (equipmentSlot != null) {
            if (entity instanceof Player player){
                player.getInventory().setItem(equipmentSlot, itemStack);
            }else {
                EntityEquipment equipment = entity.getEquipment();
                Preconditions.checkNotNull(equipment, "Equipment for ", entity, " - null");
                equipment.setItem(equipmentSlot, itemStack);
            }
        }else {
            throw new RuntimeException("No slot added");
        }
    }

    public void changeItem(@NotNull Consumer<ItemStack> consumer, @NotNull LivingEntity entity) {
        ItemStack itemStack = getItem(entity);
        consumer.accept(itemStack);
        setItem(itemStack, entity);
    }

    public void addItem(int count, @NotNull LivingEntity entity) {
        changeItem(itemStack -> itemStack.add(count), entity);
    }



}
