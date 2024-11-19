package me.udnek.rpgu.entity;

import me.udnek.itemscoreu.customentity.ConstructableCustomEntity;
import me.udnek.itemscoreu.customentity.CustomEntityType;
import me.udnek.rpgu.RpgU;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.*;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BundleMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class TotemOfSavingEntity extends ConstructableCustomEntity<Piglin> implements Listener {
    @Override
    public EntityType getVanillaEntityType() {
        return EntityType.PIGLIN;
    }

    @Override
    public void onSpawn() {
        ItemStack head = new ItemStack(Material.GUNPOWDER);
        head.editMeta(itemMeta -> itemMeta.setItemModel(new NamespacedKey(RpgU.getInstance(), "entity/totem_of_saving")));
        EntityEquipment equipment = entity.getEquipment();
        equipment.clear();
        equipment.setItem(EquipmentSlot.HEAD, head);
        entity.setImmuneToZombification(true);
        entity.setAdult();
        entity.setSilent(true);
        entity.setInvisible(true);
        entity.setAware(false);
        entity.clearLootTable();
        entity.setRotation(entity.getYaw(), 0);
        entity.getAttribute(Attribute.MAX_HEALTH).setBaseValue(10);
        entity.getAttribute(Attribute.KNOCKBACK_RESISTANCE).setBaseValue(0.9);
        entity.setPersistent(true);
    }

    public void setItems(@Nullable List<ItemStack> items){
        ItemStack container;
        if (items == null || items.isEmpty()){
            container = null;
        } else {
            container = new ItemStack(Material.BUNDLE);
            container.editMeta(BundleMeta.class, bundleMeta -> {
                bundleMeta.setItems(items);
                bundleMeta.setItemModel(new NamespacedKey(RpgU.getInstance(), "empty"));
            });
        }

        entity.getEquipment().setItem(EquipmentSlot.HAND, container);
    }
    public @NotNull List<ItemStack> getItems(){
        ItemStack stack = entity.getEquipment().getItem(EquipmentSlot.HAND);
        if (!(stack.getItemMeta() instanceof BundleMeta bundleMeta)) return List.of();
        return bundleMeta.getItems();
    }

    public void onDeath(@NotNull EntityDeathEvent event) {
        List<ItemStack> drops = event.getDrops();
        drops.clear();
        drops.addAll(getItems());
    }

    @Override
    public void unload() {}
    @Override
    public void tick() {}

    @Override
    public @NotNull CustomEntityType<?> getType() {
        return EntityTypes.TOTEM_OF_SAVING;
    }


}

















