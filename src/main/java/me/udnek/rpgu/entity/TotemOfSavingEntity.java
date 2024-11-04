package me.udnek.rpgu.entity;

import me.udnek.itemscoreu.customentity.ConstructableCustomEntity;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.event.Listener;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BundleMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class TotemOfSavingEntity extends ConstructableCustomEntity<ArmorStand> implements Listener {
    @Override
    public EntityType getVanillaEntityType() {
        return EntityType.ARMOR_STAND;
    }

    @Override
    public void onSpawn() {
        entity.getEquipment().setItem(EquipmentSlot.HEAD, new ItemStack(Material.PUMPKIN));
        entity.setDisabledSlots(EquipmentSlot.values());
    }

    public void setItems(@Nullable List<ItemStack> items){
        ItemStack container;
        if (items == null || items.isEmpty()){
            container = null;
        } else {
            container = new ItemStack(Material.BUNDLE);
            container.editMeta(BundleMeta.class, bundleMeta -> {
                bundleMeta.setItems(items);
            });
        }

        entity.setItem(EquipmentSlot.HAND, container);
    }
    public @NotNull List<ItemStack> getItems(){
        ItemStack stack = entity.getItem(EquipmentSlot.HAND);
        if (!(stack.getItemMeta() instanceof BundleMeta bundleMeta)) return List.of();
        return bundleMeta.getItems();
    }

    @Override
    public void unload() {}
    @Override
    public void tick() {}
}

















