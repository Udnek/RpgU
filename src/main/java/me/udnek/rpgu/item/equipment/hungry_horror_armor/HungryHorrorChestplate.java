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

public class HungryHorrorChestplate extends HungryHorrorArmor {
    @Override
    public @NotNull Material getMaterial() {return Material.DIAMOND_CHESTPLATE;}

    @Override
    public @NotNull String getRawId() {return "hungry_horror_chestplate";}

    @Override
    public @Nullable LoreBuilder getLoreBuilder() {return getLoreBuilder(CustomEquipmentSlot.CHEST);}


    @Override
    protected void modifyFinalItemMeta(@NotNull ItemMeta itemMeta) {
        super.modifyFinalItemMeta(itemMeta);
        itemMeta.removeAttributeModifier(Attribute.ARMOR);
        AttributeUtils.addAttribute(itemMeta, Attribute.ARMOR, new NamespacedKey(RpgU.getInstance(), "base_armor_helmet"), 2, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.CHEST);
        itemMeta.removeAttributeModifier(Attribute.ARMOR_TOUGHNESS);
    }

    @Override
    public void initializeComponents() {
        super.initializeComponents();

        setComponent(new HungryHorrorComponent(PotionEffectType.ABSORPTION));
    }
}
