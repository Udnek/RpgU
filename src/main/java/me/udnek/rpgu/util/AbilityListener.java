package me.udnek.rpgu.util;

import io.papermc.paper.datacomponent.DataComponentTypes;
import io.papermc.paper.event.player.PlayerStopUsingItemEvent;
import me.udnek.itemscoreu.customitem.CustomItem;
import me.udnek.itemscoreu.util.SelfRegisteringListener;
import me.udnek.rpgu.component.ComponentTypes;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityResurrectEvent;
import org.bukkit.event.entity.EntityToggleGlideEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public class AbilityListener extends SelfRegisteringListener {
    public AbilityListener(@NotNull Plugin plugin) {super(plugin);}

    @EventHandler
    public void abilityRightClick(PlayerInteractEvent event){
        if (!event.getAction().isRightClick()) return;
        CustomItem.consumeIfCustom(event.getItem(), customItem ->
                customItem.getComponents().getOrDefault(ComponentTypes.ACTIVE_ABILITY_ITEM).onRightClick(customItem, event));
    }

    @EventHandler
    public void abilityStopUsing(PlayerStopUsingItemEvent event){
        CustomItem.consumeIfCustom(event.getItem(), customItem ->
                customItem.getComponents().getOrDefault(ComponentTypes.ACTIVE_ABILITY_ITEM).onStopUsing(customItem, event));
    }

    @EventHandler
    public void abilityConsume(PlayerItemConsumeEvent event){
        CustomItem.consumeIfCustom(event.getItem(), customItem ->
                customItem.getComponents().getOrDefault(ComponentTypes.ACTIVE_ABILITY_ITEM).onConsume(customItem, event));
    }

    @EventHandler
    public void entityResurrect(EntityResurrectEvent event){
        EquipmentSlot hand = event.getHand();
        if (hand == null) return;
        ItemStack item = event.getEntity().getEquipment().getItem(hand);
        CustomItem.consumeIfCustom(item, customItem ->
                customItem.getComponents().getOrDefault(ComponentTypes.PASSIVE_ABILITY_ITEM).onDeath(customItem, event));
    }

    @EventHandler
    public void abilityGlide(EntityToggleGlideEvent event){
        if (!(event.isGliding())) return;
        if (!(event.getEntity() instanceof LivingEntity livingEntity)) return;
        ItemStack item = null;
        EntityEquipment equipment = livingEntity.getEquipment();
        if (equipment == null) return;
        for (ItemStack armorContent : equipment.getArmorContents()) {
            if (armorContent == null) continue;
            if (armorContent.hasData(DataComponentTypes.GLIDER)){
                item = armorContent;
                break;
            }
        }
        if (item == null) return;
        CustomItem.consumeIfCustom(item, customItem ->
                customItem.getComponents().getOrDefault(ComponentTypes.PASSIVE_ABILITY_ITEM).onGlide(customItem, event));
    }
}
