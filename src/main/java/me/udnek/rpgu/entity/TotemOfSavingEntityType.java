package me.udnek.rpgu.entity;

import me.udnek.itemscoreu.customentitylike.entity.ConstructableCustomEntityType;
import me.udnek.itemscoreu.customentitylike.entity.CustomEntity;
import me.udnek.itemscoreu.customentitylike.entity.CustomEntityType;
import me.udnek.itemscoreu.customentitylike.entity.CustomTickingEntityType;
import me.udnek.itemscoreu.customitem.ItemUtils;
import me.udnek.rpgu.RpgU;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Piglin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

class TotemOfSavingEntityType extends ConstructableCustomEntityType<Piglin> implements CustomTickingEntityType<TotemOfSavingEntity>, Listener {

    @Override
    public @NotNull String getRawId() {
        return "totem_of_saving";
    }

    @Override
    public @NotNull Piglin spawnNewEntity(@NotNull Location location) {
        Piglin entity = super.spawnNewEntity(location);

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
        Objects.requireNonNull(entity.getAttribute(Attribute.MAX_HEALTH)).setBaseValue(10);
        Objects.requireNonNull(entity.getAttribute(Attribute.KNOCKBACK_RESISTANCE)).setBaseValue(0.8);
        entity.setPersistent(true);
        entity.setRemoveWhenFarAway(false);
        entity.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, PotionEffect.INFINITE_DURATION, 0, false, false, false));
        entity.addPotionEffect(new PotionEffect(PotionEffectType.WATER_BREATHING, PotionEffect.INFINITE_DURATION, 0, true, false, true));
        if (entity.getLocation().getBlock().getLightFromSky() < 13){
            entity.setGlowing(true);
        }

        return entity;
    }

    @EventHandler
    public void onTotemDeath(EntityDeathEvent event){
        CustomEntity customEntity = CustomEntityType.getTicking(event.getEntity());
        if (customEntity == null || customEntity.getType() != EntityTypes.TOTEM_OF_SAVING) return;
        ((TotemOfSavingEntity) customEntity).onDeath(event);
    }

    @EventHandler
    public void playerInteractWithTotem(PlayerInteractEntityEvent event){
        CustomEntity customEntity = CustomEntityType.getTicking(event.getRightClicked());
        if (!(customEntity instanceof TotemOfSavingEntity totemOfSavingEntity)) return;
        PlayerInventory inventory = event.getPlayer().getInventory();
        totemOfSavingEntity.getItems().forEach(itemStack -> {
            EquipmentSlot slot;
            if (itemStack.getItemMeta().hasEquippable()) slot = itemStack.getItemMeta().getEquippable().getSlot();
            else slot = itemStack.getType().getEquipmentSlot();
            if (slot != EquipmentSlot.HAND && inventory.getItem(slot).getType() == Material.AIR){
                inventory.setItem(slot, itemStack);
            } else {
                ItemUtils.giveAndDropLeftover(event.getPlayer(), itemStack);
            }
        });
        customEntity.remove();
    }
    @EventHandler
    public void onAgr(EntityTargetLivingEntityEvent event){
        LivingEntity target = event.getTarget();
        if (target == null) return;
        CustomEntityType type = CustomEntityType.get(target);
        if (type == EntityTypes.TOTEM_OF_SAVING) event.setCancelled(true);
    }

    @Override
    public @NotNull EntityType getVanillaType() {
        return EntityType.PIGLIN;
    }

    @Override
    public void load(@NotNull Entity entity) {}

    @Override
    public void unload(@NotNull Entity entity) {}

    @Override
    public @NotNull TotemOfSavingEntity createNewClass() {
        return new TotemOfSavingEntity();
    }
}
