package me.udnek.rpgu.item.artifact;

import me.udnek.coreu.custom.attribute.CustomAttributeModifier;
import me.udnek.coreu.custom.attribute.CustomAttributesContainer;
import me.udnek.coreu.custom.component.instance.CustomAttributedItem;
import me.udnek.coreu.custom.item.ConstructableCustomItem;
import me.udnek.jeiu.component.HiddenItemComponent;
import me.udnek.rpgu.attribute.Attributes;
import me.udnek.rpgu.equipment.slot.EquipmentSlots;
import org.bukkit.attribute.AttributeModifier;
import org.jetbrains.annotations.NotNull;

public class CriticalStone extends ConstructableCustomItem {
    @Override
    public @NotNull String getRawId() {
        return "critical_stone";
    }


    @Override
    public void initializeComponents() {
        super.initializeComponents();

        getComponents().set(HiddenItemComponent.INSTANCE);
        CustomAttributeModifier attribute = new CustomAttributeModifier(0.2, AttributeModifier.Operation.ADD_SCALAR, EquipmentSlots.ARTIFACTS);
        getComponents().set(new CustomAttributedItem(new CustomAttributesContainer.Builder().add(Attributes.CRITICAL_DAMAGE, attribute).build()));
    }
}
