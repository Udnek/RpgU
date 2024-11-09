package me.udnek.rpgu.item.equipment.hungry_horror_armor;

import me.udnek.itemscoreu.customattribute.AttributeUtils;
import me.udnek.itemscoreu.customequipmentslot.CustomEquipmentSlot;
import me.udnek.itemscoreu.util.LoreBuilder;
import me.udnek.rpgu.RpgU;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.EquipmentSlotGroup;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class HungryHorrorHelmet extends HungryHorrorArmor {
    @Override
    public @NotNull String getRawId() {return "hungry_horror_helmet";}

    @Override
    public @NotNull Material getMaterial() {return Material.DIAMOND_HELMET;}

    @Override
    public @Nullable LoreBuilder getLoreBuilder() {return getLoreBuilder(CustomEquipmentSlot.CHEST);}

    @Override
    protected void modifyFinalItemMeta(@NotNull ItemMeta itemMeta) {
        super.modifyFinalItemMeta(itemMeta);
        AttributeUtils.appendAttribute(itemMeta, Attribute.MAX_HEALTH, new NamespacedKey(RpgU.getInstance(), "base_max_health_helmet"), 2, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.HEAD);
        itemMeta.removeAttributeModifier(Attribute.ARMOR);
        AttributeUtils.addAttribute(itemMeta, Attribute.ARMOR, new NamespacedKey(RpgU.getInstance(), "base_armor_helmet"), 2, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.HEAD);
        itemMeta.removeAttributeModifier(Attribute.ARMOR_TOUGHNESS);
        AttributeUtils.appendAttribute(itemMeta, Attribute.ATTACK_DAMAGE, new NamespacedKey(RpgU.getInstance(), "attack_damage_helmet"), -0.5, AttributeModifier.Operation.MULTIPLY_SCALAR_1, EquipmentSlotGroup.HEAD);
    }

    @Override
    public void initializeComponents() {
        super.initializeComponents();

        setComponent(new HungryHorrorComponent(PotionEffectType.STRENGTH));
    }
}