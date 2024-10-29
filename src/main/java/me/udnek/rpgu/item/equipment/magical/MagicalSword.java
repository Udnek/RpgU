package me.udnek.rpgu.item.equipment.magical;

import me.udnek.itemscoreu.customattribute.CustomAttributesContainer;
import me.udnek.itemscoreu.customcomponent.instance.CustomItemAttributesComponent;
import me.udnek.itemscoreu.customequipmentslot.CustomEquipmentSlot;
import me.udnek.itemscoreu.customitem.ConstructableCustomItem;
import me.udnek.rpgu.attribute.Attributes;
import org.bukkit.Material;
import org.bukkit.attribute.AttributeModifier;
import org.jetbrains.annotations.NotNull;

public class MagicalSword extends ConstructableCustomItem {
    @Override
    public @NotNull String getRawId() {
        return "magical_sword";
    }

    @Override
    public @NotNull Material getMaterial() {
        return Material.IRON_SWORD;
    }

    @Override
    public void afterInitialization() {
        super.afterInitialization();
        setComponent(new CustomItemAttributesComponent(
                new CustomAttributesContainer.Builder()
                        .add(Attributes.MELEE_MAGICAL_DAMAGE_MULTIPLIER, 0.5, AttributeModifier.Operation.ADD_NUMBER, CustomEquipmentSlot.MAIN_HAND)
                        .build()
        ));
    }
}
