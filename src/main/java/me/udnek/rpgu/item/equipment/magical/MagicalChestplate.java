package me.udnek.rpgu.item.equipment.magical;

import me.udnek.itemscoreu.customattribute.CustomAttributesContainer;
import me.udnek.itemscoreu.customcomponent.instance.CustomItemAttributesComponent;
import me.udnek.itemscoreu.customequipmentslot.slot.CustomEquipmentSlot;
import me.udnek.itemscoreu.customitem.ConstructableCustomItem;
import me.udnek.jeiu.component.HiddenItemComponent;
import me.udnek.rpgu.attribute.Attributes;
import net.kyori.adventure.key.Key;
import org.bukkit.Material;
import org.bukkit.attribute.AttributeModifier;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@ApiStatus.ScheduledForRemoval
public class MagicalChestplate extends ConstructableCustomItem {
    @Override
    public @NotNull String getRawId() {
        return "magical_chestplate";
    }

    @Override
    public @Nullable DataSupplier<Key> getItemModel() {return null;}

    @Override
    public @NotNull Material getMaterial() {
        return Material.IRON_CHESTPLATE;
    }

    @Override
    public boolean addDefaultAttributes() {return true;}

    @Override
    public void initializeComponents() {
        super.initializeComponents();
        getComponents().set(HiddenItemComponent.INSTANCE);
        getComponents().set(new CustomItemAttributesComponent(
                new CustomAttributesContainer.Builder()
                        .add(Attributes.MAGICAL_POTENTIAL, 5, AttributeModifier.Operation.ADD_NUMBER, CustomEquipmentSlot.CHEST)
                        .add(Attributes.MAGICAL_DEFENSE_MULTIPLIER, 1, AttributeModifier.Operation.ADD_NUMBER, CustomEquipmentSlot.CHEST)
                        .add(Attributes.CAST_RANGE, 1, AttributeModifier.Operation.ADD_SCALAR, CustomEquipmentSlot.CHEST)
                        .build()
        ));
    }
}
