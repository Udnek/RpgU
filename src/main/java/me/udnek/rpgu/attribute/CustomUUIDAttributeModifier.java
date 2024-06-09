package me.udnek.rpgu.attribute;

import me.udnek.itemscoreu.customattribute.CustomAttributeModifier;
import me.udnek.itemscoreu.customattribute.equipmentslot.CustomEquipmentSlot;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.EquipmentSlotGroup;

import java.util.UUID;

public class CustomUUIDAttributeModifier extends CustomAttributeModifier {
    protected final UUID uuid;
    public CustomUUIDAttributeModifier(UUID uuid, double amount, AttributeModifier.Operation operation, CustomEquipmentSlot equipmentSlot) {
        super(amount, operation, equipmentSlot);
        this.uuid = uuid;
    }
    public UUID getUniqueId() {return uuid;}

    public AttributeModifier toVanilla(){
        EquipmentSlotGroup equipmentSlot = this.equipmentSlot.getVanillaAlternative();
        if (equipmentSlot == null) equipmentSlot = EquipmentSlotGroup.ANY;

        return new AttributeModifier(uuid, "CustomUUIDAttributeModifier", amount, operation, equipmentSlot);
    }
}
