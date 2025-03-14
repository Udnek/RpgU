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
public class MagicalSword extends ConstructableCustomItem {
    @Override
    public @NotNull String getRawId() {
        return "magical_sword";
    }

    @Override
    public @Nullable DataSupplier<Key> getItemModel() {return null;}

    @Override
    public boolean addDefaultAttributes() {return true;}

    @Override
    public @NotNull Material getMaterial() {
        return Material.IRON_SWORD;
    }

    @Override
    public void initializeComponents() {
        super.initializeComponents();
        getComponents().set(HiddenItemComponent.INSTANCE);
        getComponents().set(new CustomItemAttributesComponent(
                new CustomAttributesContainer.Builder()
                        .add(Attributes.MELEE_MAGICAL_DAMAGE_MULTIPLIER, 0.5, AttributeModifier.Operation.ADD_NUMBER, CustomEquipmentSlot.MAIN_HAND)
                        .add(Attributes.COOLDOWN_TIME, -0.5, AttributeModifier.Operation.MULTIPLY_SCALAR_1, CustomEquipmentSlot.MAIN_HAND)
                        .build()
        ));
    }
}
