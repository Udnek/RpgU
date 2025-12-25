package me.udnek.rpgu.component.instance;

import com.google.common.base.Preconditions;
import io.papermc.paper.datacomponent.DataComponentTypes;
import me.udnek.coreu.custom.equipmentslot.slot.CustomEquipmentSlot;
import me.udnek.coreu.custom.equipmentslot.slot.CustomEquipmentSlot.Single;
import me.udnek.coreu.custom.equipmentslot.universal.UniversalInventorySlot;
import me.udnek.coreu.custom.item.CustomItem;
import me.udnek.coreu.util.Either;
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
    public @NotNull ActionResult action(@NotNull CustomItem custom.Item, @NotNull LivingEntity livingEntity, @NotNull Either<UniversalInventorySlot, CustomEquipmentSlot.Single> slot,
                                        @NotNull EntityToggleGlideEvent entityToggleGlideEvent) {
        return ActionResult.NO_COOLDOWN;
    }

    @Override
    public void onGlide(@NotNull CustomItem custom.Item, @NotNull EntityToggleGlideEvent event) {
        activate(custom.Item, (LivingEntity) event.getEntity(), true, new Either<>(null, (CustomEquipmentSlot.Single) slot), event);
    }
}
