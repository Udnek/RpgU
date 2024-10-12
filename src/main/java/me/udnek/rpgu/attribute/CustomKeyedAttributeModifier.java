package me.udnek.rpgu.attribute;

import me.udnek.itemscoreu.customattribute.CustomAttributeModifier;
import me.udnek.itemscoreu.customequipmentslot.CustomEquipmentSlot;
import org.bukkit.Keyed;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.EquipmentSlotGroup;
import org.jetbrains.annotations.NotNull;

public class CustomKeyedAttributeModifier extends CustomAttributeModifier implements Keyed {
    protected final NamespacedKey key;
    public CustomKeyedAttributeModifier(@NotNull NamespacedKey key, double amount, @NotNull AttributeModifier.Operation operation, @NotNull CustomEquipmentSlot equipmentSlot) {
        super(amount, operation, equipmentSlot);
        this.key = key;
    }

    public @NotNull AttributeModifier toVanilla(){
        EquipmentSlotGroup equipmentSlot = this.equipmentSlot.getVanillaAlternative();
        if (equipmentSlot == null) equipmentSlot = EquipmentSlotGroup.ANY;

        return new AttributeModifier(key, amount, operation, equipmentSlot);
    }

    @Override
    public @NotNull NamespacedKey getKey() {
        return key;
    }
}
