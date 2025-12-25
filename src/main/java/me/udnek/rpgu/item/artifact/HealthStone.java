package me.udnek.rpgu.item.artifact;

import me.udnek.coreu.custom.attribute.CustomAttributeModifier;
import me.udnek.coreu.custom.attribute.CustomAttributesContainer;
import me.udnek.coreu.custom.component.instance.CustomItemAttributesComponent;
import me.udnek.coreu.custom.item.ConstructableCustomItem;
import me.udnek.jeiu.component.HiddenItemComponent;
import me.udnek.rpgu.attribute.Attributes;
import me.udnek.rpgu.equipment.slot.EquipmentSlots;
import org.bukkit.attribute.AttributeModifier;
import org.jetbrains.annotations.NotNull;

public class HealthStone extends ConstructableCustomItem {
    @Override
    public @NotNull String getRawId() {return "health_stone";}

    @Override
    public void initializeComponents() {
        super.initializeComponents();

        getComponents().set(HiddenItemComponent.INSTANCE);
        CustomAttributeModifier attribute = new CustomAttributeModifier(3, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlots.ARTIFACTS);
        getComponents().set(new CustomItemAttributesComponent(new CustomAttributesContainer.Builder().add(Attributes.HEALTH_REGENERATION, attribute).build()));
    }
}
