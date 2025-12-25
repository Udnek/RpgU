package me.udnek.rpgu.component.ability.instance;

import com.google.common.base.Preconditions;
import io.papermc.paper.datacomponent.DataComponentTypes;
import me.udnek.coreu.custom.component.CustomComponent;
import me.udnek.coreu.custom.component.CustomComponentType;
import me.udnek.coreu.custom.equipmentslot.slot.CustomEquipmentSlot;
import me.udnek.coreu.custom.equipmentslot.universal.BaseUniversalSlot;
import me.udnek.coreu.custom.equipmentslot.universal.UniversalInventorySlot;
import me.udnek.coreu.custom.item.CustomItem;
import me.udnek.coreu.rpgu.component.RPGUComponents;
import me.udnek.coreu.rpgu.component.RPGUPassiveItem;
import me.udnek.coreu.rpgu.component.ability.passive.RPGUConstructablePassiveAbility;
import me.udnek.coreu.rpgu.component.ability.property.AttributeBasedProperty;
import me.udnek.rpgu.component.ability.Abilities;
import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityToggleGlideEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class GliderPassive extends RPGUConstructablePassiveAbility<EntityToggleGlideEvent> {

    public static final GliderPassive DEFAULT = new GliderPassive(CustomEquipmentSlot.CHEST, 0);

    public static @NotNull GliderPassive of(@NotNull ItemStack itemStack, int cooldown){
        return new GliderPassive( CustomEquipmentSlot.getFromVanilla(Preconditions.checkNotNull(itemStack.getData(DataComponentTypes.EQUIPPABLE),
                "Item doesn't have EQUIPPABLE Component", itemStack).slot()), cooldown);
    }

    protected CustomEquipmentSlot slot;

    public GliderPassive(@NotNull CustomEquipmentSlot slot, int cooldown){
        this.slot = slot;
        getComponents().set(new AttributeBasedProperty(cooldown, RPGUComponents.ABILITY_COOLDOWN_TIME));
    }

    @Override
    public @NotNull CustomEquipmentSlot getSlot() {
        return slot;
    }

    @Override
    public void tick(@NotNull CustomItem customItem, @NotNull Player player, @NotNull BaseUniversalSlot baseUniversalSlot, int i) {}

    @Override
    public void activate(@NotNull CustomItem customItem, @NotNull LivingEntity livingEntity, @NotNull UniversalInventorySlot slot, @NotNull EntityToggleGlideEvent entityToggleGlideEvent) {
        super.activate(customItem, livingEntity, slot, entityToggleGlideEvent);
    }

    @Override
    public @NotNull CustomComponentType<? super RPGUPassiveItem, ? extends CustomComponent<? super RPGUPassiveItem>> getType() {
        return Abilities.GLIDER;
    }

    @Override
    protected @NotNull ActionResult action(@NotNull CustomItem customItem, @NotNull LivingEntity livingEntity, @NotNull UniversalInventorySlot universalInventorySlot, @NotNull EntityToggleGlideEvent entityToggleGlideEvent) {
        return null;
    }

    @Override
    public @Nullable Pair<List<String>, List<String>> getEngAndRuDescription() {
        return null;
    }
}
