package me.udnek.rpgu.item.equipment.magical;

import me.udnek.coreu.custom.attribute.CustomAttributesContainer;
import me.udnek.coreu.custom.component.instance.CustomAttributedItem;
import me.udnek.coreu.custom.equipment.slot.CustomEquipmentSlot;
import me.udnek.coreu.custom.item.ConstructableCustomItem;
import me.udnek.coreu.rpgu.attribute.RPGUAttributes;
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
    public void initializeComponents() {
        super.initializeComponents();
        getComponents().set(HiddenItemComponent.INSTANCE);
        getComponents().set(new CustomAttributedItem(
                new CustomAttributesContainer.Builder()
                        .add(Attributes.MAGICAL_POTENTIAL, 5, AttributeModifier.Operation.ADD_NUMBER, CustomEquipmentSlot.CHEST)
                        .add(Attributes.MAGICAL_DEFENSE_MULTIPLIER, 1, AttributeModifier.Operation.ADD_NUMBER, CustomEquipmentSlot.CHEST)
                        .add(RPGUAttributes.ABILITY_CAST_RANGE, 1, AttributeModifier.Operation.ADD_SCALAR, CustomEquipmentSlot.CHEST)
                        .build()
        ));
    }
}
