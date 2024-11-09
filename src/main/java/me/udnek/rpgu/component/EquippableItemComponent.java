package me.udnek.rpgu.component;

import me.udnek.itemscoreu.customattribute.CustomKeyedAttributeModifier;
import me.udnek.itemscoreu.customattribute.VanillaAttributesContainer;
import me.udnek.itemscoreu.customcomponent.CustomComponent;
import me.udnek.itemscoreu.customcomponent.CustomComponentType;
import me.udnek.itemscoreu.customequipmentslot.CustomEquipmentSlot;
import me.udnek.itemscoreu.customitem.CustomItem;
import me.udnek.rpgu.equipment.slot.EquipmentSlots;
import me.udnek.rpgu.mechanic.damaging.DamageInstance;
import net.kyori.adventure.text.Component;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;

public interface EquippableItemComponent extends CustomComponent<CustomItem> {

    EquippableItemComponent DEFAULT = new EquippableItemComponent() {
        @Override
        public boolean isAppropriateSlot(@NotNull CustomEquipmentSlot slot) {
            return false;
        }
    };

    boolean isAppropriateSlot(@NotNull CustomEquipmentSlot slot);
    default void onEquipped(@NotNull CustomItem item, @NotNull Player player, @NotNull CustomEquipmentSlot slot, @NotNull ItemStack itemStack){
        if (!isAppropriateSlot(slot)) return;

        getAttributes(item, player, slot, (attributeInstance, customModifier) -> {
            AttributeModifier modifier = customModifier.toVanillaWitAdjustedKey("_" + slot.getKey().asString().replace(':', '_'));
            if (attributeInstance.getModifier(modifier.getKey()) == null) {
                attributeInstance.addModifier(modifier);
            }
        });
    }
    default void onUnequipped(@NotNull CustomItem item, @NotNull Player player, @NotNull CustomEquipmentSlot slot, @NotNull ItemStack itemStack) {
        if (!isAppropriateSlot(slot)) return;
        getAttributes(item, player, slot, (attributeInstance, customModifier) -> {
            AttributeModifier modifier = customModifier.toVanillaWitAdjustedKey("_" + slot.getKey().asString().replace(':', '_'));
            if (attributeInstance.getModifier(modifier.getKey()) != null) {
                attributeInstance.removeModifier(modifier);
            }
        });
    }
    default void tickBeingEquipped(@NotNull CustomItem item, @NotNull Player player, @NotNull CustomEquipmentSlot slot){}
    default void onPlayerAttacksWhenEquipped(@NotNull CustomItem item, @NotNull Player player, @NotNull CustomEquipmentSlot slot, @NotNull DamageInstance event){}
    default void onPlayerReceivesDamageWhenEquipped(@NotNull CustomItem item, @NotNull Player player, @NotNull CustomEquipmentSlot slot, @NotNull DamageInstance system){}
    default void onPlayerHitsWithProjectileWhenEquipped(@NotNull CustomItem item, @NotNull Player player, @NotNull CustomEquipmentSlot slot, @NotNull DamageInstance system){}
    default @Nullable Component getHudImage(@NotNull CustomItem item, @NotNull Player player){return null;}


    default void getAttributes(@NotNull CustomItem item, @NotNull Player player, @NotNull CustomEquipmentSlot slot, @NotNull AttributeConsumer consumer){
        VanillaAttributesContainer container = item.getComponentOrDefault(CustomComponentType.VANILLA_ATTRIBUTES_ITEM).getAttributes(item);
        if (container.isEmpty()) return;

        for (Map.Entry<Attribute, List<CustomKeyedAttributeModifier>> entry : container.get(slot).getAll().entrySet()) {
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


    @Override
    @NotNull
    default CustomComponentType<CustomItem, ?> getType(){
        return ComponentTypes.EQUIPPABLE_ITEM;
    }
}
