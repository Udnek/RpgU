package me.udnek.rpgu.item.abstraction;

import me.udnek.itemscoreu.utils.LogUtils;
import me.udnek.rpgu.attribute.CustomUUIDAttributeModifier;
import me.udnek.rpgu.attribute.DefaultVanillaAttributeHolder;
import me.udnek.rpgu.attribute.VanillaAttributeContainer;
import me.udnek.rpgu.attribute.equipmentslot.EquipmentSlots;
import me.udnek.rpgu.equipment.PlayersEquipmentDatabase;
import org.apache.logging.log4j.core.util.UuidUtil;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public abstract class ArtifactItem extends EquippableItem implements DefaultVanillaAttributeHolder {
    @Override
    public VanillaAttributeContainer getDefaultVanillaAttributes() {return null;}

    @Override
    public boolean isEquippedInAppropriateSlot(Player player) {
        return PlayersEquipmentDatabase.get(player).isEquippedAsArtifact(this);
    }
    @Override
    public void onEquipped(Player player, ItemStack itemStack) {
        VanillaAttributeContainer defaultVanillaAttributes = getDefaultVanillaAttributes();
        if (defaultVanillaAttributes == null) return;
        for (Map.Entry<Attribute, List<CustomUUIDAttributeModifier>> entry : defaultVanillaAttributes.get(EquipmentSlots.ARTIFACT).getAll().entrySet()) {
            Attribute attribute = entry.getKey();
            AttributeInstance attributeInstance = player.getAttribute(attribute);
            if (attributeInstance == null) continue;
            for (CustomUUIDAttributeModifier customModifier : entry.getValue()) {
                if (attributeInstance.getModifier(customModifier.getUniqueId()) != null) continue;
                attributeInstance.addModifier(customModifier.toVanilla());
            }
        }
    }

    @Override
    public void onUnequipped(Player player, ItemStack itemStack) {
        if (isEquippedInAppropriateSlot(player)) return;
        VanillaAttributeContainer defaultVanillaAttributes = getDefaultVanillaAttributes();
        if (defaultVanillaAttributes == null) return;
        for (Map.Entry<Attribute, List<CustomUUIDAttributeModifier>> entry : defaultVanillaAttributes.get(EquipmentSlots.ARTIFACT).getAll().entrySet()) {
            Attribute attribute = entry.getKey();
            AttributeInstance attributeInstance = player.getAttribute(attribute);
            if (attributeInstance == null) continue;
            for (CustomUUIDAttributeModifier customModifier : entry.getValue()) {
                if (attributeInstance.getModifier(customModifier.getUniqueId()) == null) continue;
                attributeInstance.removeModifier(customModifier.getUniqueId());
            }
        }
    }
}
