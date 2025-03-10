package me.udnek.rpgu.component.instance;

import com.google.common.base.Preconditions;
import io.papermc.paper.datacomponent.DataComponentTypes;
import me.udnek.itemscoreu.customequipmentslot.slot.CustomEquipmentSlot;
import me.udnek.itemscoreu.customequipmentslot.slot.SingleSlot;
import me.udnek.itemscoreu.customequipmentslot.universal.UniversalInventorySlot;
import me.udnek.itemscoreu.customitem.CustomItem;
import me.udnek.itemscoreu.util.Either;
import me.udnek.rpgu.component.ComponentTypes;
import me.udnek.rpgu.component.ability.passive.ConstructablePassiveAbility;
import me.udnek.rpgu.component.ability.property.AttributeBasedProperty;
import me.udnek.rpgu.lore.ability.PassiveAbilityLorePart;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityToggleGlideEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class GliderComponent extends ConstructablePassiveAbility<EntityToggleGlideEvent> {
    String name;
    CustomEquipmentSlot slot;

    public GliderComponent(@NotNull ItemStack itemStack, int cooldown){
        getComponents().set(new AttributeBasedProperty(cooldown, ComponentTypes.ABILITY_COOLDOWN));
        this.name = itemStack.translationKey();
        this.slot = CustomEquipmentSlot.getFromVanilla(Preconditions.checkNotNull(itemStack.getData(DataComponentTypes.EQUIPPABLE),
                "Item doesn't have EQUIPPABLE Component", itemStack).slot());
    }

    @Override
    public @NotNull CustomEquipmentSlot getSlot() {
        return slot;
    }

    @Override
    public void addLoreLines(@NotNull PassiveAbilityLorePart componentable) {
        componentable.addAbilityDescription(Component.translatable(name +".passive_ability.0"));
        super.addLoreLines(componentable);
    }

    @Override
    public @NotNull ActionResult action(@NotNull CustomItem customItem, @NotNull LivingEntity livingEntity, @NotNull Either<UniversalInventorySlot, SingleSlot> slot,
                                        @NotNull EntityToggleGlideEvent entityToggleGlideEvent) {
        return ActionResult.NO_COOLDOWN;
    }

    @Override
    public void onGlide(@NotNull CustomItem customItem, @NotNull EntityToggleGlideEvent event) {
        activate(customItem, (LivingEntity) event.getEntity(), true, new Either<>(null, (SingleSlot) slot), event);
    }
}
