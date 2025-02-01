package me.udnek.rpgu.component.instance;

import me.udnek.itemscoreu.customequipmentslot.CustomEquipmentSlot;
import me.udnek.itemscoreu.customitem.CustomItem;
import me.udnek.rpgu.component.ability.passive.ConstructablePassiveAbilityComponent;
import me.udnek.rpgu.lore.ability.PassiveAbilityLorePart;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityToggleGlideEvent;
import org.jetbrains.annotations.NotNull;

public class GliderComponent extends ConstructablePassiveAbilityComponent<EntityToggleGlideEvent> {

    @Override
    public @NotNull CustomEquipmentSlot getSlot() {
        return CustomEquipmentSlot.CHEST;
    }

    @Override
    public void addLoreLines(@NotNull PassiveAbilityLorePart componentable) {
        componentable.addAbilityDescription(Component.translatable("item.minecraft.elytra.passive_ability.0"));
        super.addLoreLines(componentable);
    }

    @Override
    public @NotNull ActionResult action(@NotNull CustomItem customItem, @NotNull LivingEntity livingEntity, @NotNull EntityToggleGlideEvent entityToggleGlideEvent) {
        return ActionResult.NO_COOLDOWN;
    }

    @Override
    public void onGlide(@NotNull CustomItem customItem, @NotNull EntityToggleGlideEvent event) {
        activate(customItem, (LivingEntity) event.getEntity(), event, true);
    }
}
