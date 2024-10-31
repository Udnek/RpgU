package me.udnek.rpgu.item.equipment.magical;

import me.udnek.itemscoreu.customattribute.CustomAttributesContainer;
import me.udnek.itemscoreu.customcomponent.instance.CustomItemAttributesComponent;
import me.udnek.itemscoreu.customequipmentslot.CustomEquipmentSlot;
import me.udnek.itemscoreu.customitem.ConstructableCustomItem;
import me.udnek.rpgu.attribute.Attributes;
import org.bukkit.Material;
import org.bukkit.attribute.AttributeModifier;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

@ApiStatus.ScheduledForRemoval
public class MagicalChestplate extends ConstructableCustomItem {
    @Override
    public @NotNull String getRawId() {
        return "magical_chestplate";
    }

    @Override
    public @NotNull Material getMaterial() {
        return Material.IRON_CHESTPLATE;
    }

    @Override
    public void afterInitialization() {
        super.afterInitialization();
        setComponent(new CustomItemAttributesComponent(
                new CustomAttributesContainer.Builder()
                        .add(Attributes.MAGICAL_POTENTIAL, 5, AttributeModifier.Operation.ADD_NUMBER, CustomEquipmentSlot.CHEST)
                        .add(Attributes.MAGICAL_DEFENSE_MULTIPLIER, 1, AttributeModifier.Operation.ADD_NUMBER, CustomEquipmentSlot.CHEST)
                        .build()
                ));
    }
}
