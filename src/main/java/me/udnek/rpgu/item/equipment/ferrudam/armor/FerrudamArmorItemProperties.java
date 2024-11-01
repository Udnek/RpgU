package me.udnek.rpgu.item.equipment.ferrudam.armor;

import me.udnek.itemscoreu.customitem.CustomItemProperties;
import me.udnek.rpgu.RpgU;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.components.EquippableComponent;
import org.jetbrains.annotations.Nullable;

public interface FerrudamArmorItemProperties extends CustomItemProperties {

    @Override
    default ItemFlag[] getTooltipHides() {return new ItemFlag[]{ItemFlag.HIDE_ATTRIBUTES};}
    @Override
    default boolean getAddDefaultAttributes() {return true;}

    @Override
    default @Nullable EquippableComponent getEquippable() {
        EquippableComponent equippable = new ItemStack(getMaterial()).getItemMeta().getEquippable();
        equippable.setSlot(getMaterial().getEquipmentSlot());
        equippable.setModel(new NamespacedKey(RpgU.getInstance(), "ferrudam"));
        return equippable;
    }
}
