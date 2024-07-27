package me.udnek.rpgu.item.abstraction;

import me.udnek.itemscoreu.customattribute.equipmentslot.CustomEquipmentSlot;
import me.udnek.rpgu.attribute.CustomUUIDAttributeModifier;
import me.udnek.rpgu.attribute.VanillaAttributeContainer;
import me.udnek.rpgu.equipment.PlayerEquipmentDatabase;
import me.udnek.rpgu.equipment.slot.EquipmentSlots;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Map;

public interface ArtifactItem extends EquippableItem {
/*    protected Component artifactImage;
    @Override
    public VanillaAttributeContainer getDefaultVanillaAttributes() {return null;}
    public Component getArtifactImage(){
        if (artifactImage == null) {
            artifactImage = initArtifactImage();
            if (artifactImage == null){
                ComponentU.textWithNoSpace(1, Component.translatable(TranslationKeys.unknownArtifactImage), 14);
            }
        }
        return artifactImage;
    }

    public Component initArtifactImage() {
        int imageSize = getArtifactImageSize();
        int offset = (16-imageSize)/2;
        return ComponentU.textWithNoSpace(offset, Component.translatable(TranslationKeys.itemPrefix+getRawId()+".image"), imageSize);
    }
    public int getArtifactImageSize(){return 16;};

    public float getHudCooldownProgress(Player player) {return 0;}*/

    @Override
    default boolean isAppropriateSlot(CustomEquipmentSlot slot){
        return slot == EquipmentSlots.ARTIFACT;
    }

    @Override
    default boolean isEquippedInAppropriateSlot(Player player) {
        return PlayerEquipmentDatabase.get(player).isEquippedAsArtifact(this);
    }
    @Override
    default void onEquipped(Player player, CustomEquipmentSlot slot, ItemStack itemStack) {
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
    default void onUnequipped(Player player, CustomEquipmentSlot slot, ItemStack itemStack) {
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
