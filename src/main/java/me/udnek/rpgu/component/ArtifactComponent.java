package me.udnek.rpgu.component;

import me.udnek.itemscoreu.customequipmentslot.CustomEquipmentSlot;
import me.udnek.itemscoreu.customitem.CustomItem;
import me.udnek.rpgu.attribute.CustomKeyedAttributeModifier;
import me.udnek.rpgu.attribute.VanillaAttributesContainer;
import me.udnek.rpgu.equipment.slot.EquipmentSlots;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;

public interface ArtifactComponent extends EquippableItemComponent{

    @Override
    default boolean isAppropriateSlot(@NotNull CustomEquipmentSlot slot){
        return EquipmentSlots.ARTIFACTS.test(slot);
    }

    @Override
    default void onEquipped(@NotNull CustomItem item, @NotNull Player player, @NotNull CustomEquipmentSlot slot, @NotNull ItemStack itemStack) {
        if (!isAppropriateSlot(slot)) return;
        getAttributes(item, player, new ArtifactComponent.AttributeConsumer() {
            @Override
            public void accept(AttributeInstance attributeInstance, CustomKeyedAttributeModifier modifier) {
                if (attributeInstance.getModifier(modifier.getKey()) == null) {
                    attributeInstance.addModifier(modifier.toVanilla());
                }
            }
        });
    }
    @Override
    default void onUnequipped(@NotNull CustomItem item, @NotNull Player player, @NotNull CustomEquipmentSlot slot, @NotNull ItemStack itemStack) {
        if (!isAppropriateSlot(slot)) return;
        getAttributes(item, player, new ArtifactComponent.AttributeConsumer() {
            @Override
            public void accept(AttributeInstance attributeInstance, CustomKeyedAttributeModifier modifier) {
                if (attributeInstance.getModifier(modifier.getKey()) != null) {
                    attributeInstance.removeModifier(modifier.toVanilla());
                }
            }
        });
    }

    default void getAttributes(@NotNull CustomItem item, Player player, ArtifactComponent.AttributeConsumer consumer){
        VanillaAttributesContainer container = item.getComponentOrDefault(ComponentTypes.VANILLA_ATTRIBUTES_ITEM).getAttributes(item);
        if (container.isEmpty()) return;

        for (Map.Entry<Attribute, List<CustomKeyedAttributeModifier>> entry : container.get(EquipmentSlots.ARTIFACTS).getAll().entrySet()) {
            Attribute attribute = entry.getKey();
            List<CustomKeyedAttributeModifier> modifiers = entry.getValue();
            AttributeInstance attributeInstance = player.getAttribute(attribute);
            if (attributeInstance == null) continue;

            for (CustomKeyedAttributeModifier modifier : modifiers) {
                consumer.accept(attributeInstance, modifier);
            }
        }
    }


    interface AttributeConsumer{
        void accept(AttributeInstance attributeInstance, CustomKeyedAttributeModifier modifier);
    }
}
