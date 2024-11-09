package me.udnek.rpgu.item.equipment.hungry_horror_armor;

import me.udnek.itemscoreu.customattribute.CustomAttribute;
import me.udnek.itemscoreu.customequipmentslot.CustomEquipmentSlot;
import me.udnek.itemscoreu.customitem.ConstructableCustomItem;
import me.udnek.itemscoreu.util.LoreBuilder;
import me.udnek.rpgu.RpgU;
import me.udnek.rpgu.lore.AttributesLorePart;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.components.EquippableComponent;
import org.jetbrains.annotations.Nullable;

public abstract class HungryHorrorArmor extends ConstructableCustomItem {

    @Override
    public ItemFlag[] getTooltipHides() {return new ItemFlag[]{ItemFlag.HIDE_ATTRIBUTES};}
    @Override
    public boolean getAddDefaultAttributes() {return true;}

    @Override
    public @Nullable EquippableComponent getEquippable() {
        EquippableComponent equippable = new ItemStack(getMaterial()).getItemMeta().getEquippable();
        equippable.setSlot(getMaterial().getEquipmentSlot());
        equippable.setModel(new NamespacedKey(RpgU.getInstance(), "hungry_horror"));
        return equippable;
    }

    public @Nullable LoreBuilder getLoreBuilder(CustomEquipmentSlot slot) {
        LoreBuilder loreBuilder = new LoreBuilder();
        AttributesLorePart attributesLorePart = new AttributesLorePart();
        loreBuilder.set(LoreBuilder.Position.ATTRIBUTES, attributesLorePart);
        attributesLorePart.addAttribute(slot, Component.translatable(getRawItemName()+".description.0").color(CustomAttribute.PLUS_COLOR));
        attributesLorePart.addAttribute(slot, Component.translatable(getRawItemName()+".description.1").color(NamedTextColor.GRAY));
        attributesLorePart.addAttribute(slot, Component.translatable(getRawItemName()+".description.2").color(NamedTextColor.GRAY));
        attributesLorePart.addAttribute(slot, Component.translatable(getRawItemName()+".description.3").color(NamedTextColor.GRAY));
        attributesLorePart.addAttribute(slot, Component.translatable(getRawItemName()+".description.4").color(NamedTextColor.GRAY));
        return loreBuilder;
    }
}
