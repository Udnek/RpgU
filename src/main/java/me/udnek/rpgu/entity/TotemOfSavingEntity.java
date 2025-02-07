package me.udnek.rpgu.entity;

import com.destroystokyo.paper.ParticleBuilder;
import io.papermc.paper.datacomponent.DataComponentTypes;
import io.papermc.paper.datacomponent.item.BundleContents;
import me.udnek.itemscoreu.customentitylike.entity.ConstructableCustomEntity;
import me.udnek.itemscoreu.customentitylike.entity.CustomTickingEntityType;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Piglin;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BundleMeta;
import org.checkerframework.checker.index.qual.Positive;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class TotemOfSavingEntity extends ConstructableCustomEntity<Piglin> implements Listener {

    @Override
    public @Positive int getTickDelay() {
        return 10;
    }

    @Override
    public void delayedTick() {
        if (entity.isOnGround()) return;
        new ParticleBuilder(Particle.GUST).location(entity.getLocation()).offset(0.2, 0, 0.2).spawn();
    }

    public void setItems(@Nullable List<ItemStack> items){
        ItemStack container;
        if (items == null || items.isEmpty()){
            container = null;
        } else {
            container = new ItemStack(Material.BUNDLE);
            container.unsetData(DataComponentTypes.ITEM_MODEL);
            container.setData(DataComponentTypes.BUNDLE_CONTENTS, BundleContents.bundleContents(items));
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
    public @NotNull CustomTickingEntityType<?> getType() {
        return EntityTypes.TOTEM_OF_SAVING;
    }
}

















