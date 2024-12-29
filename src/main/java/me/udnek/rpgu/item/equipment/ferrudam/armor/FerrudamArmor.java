package me.udnek.rpgu.item.equipment.ferrudam.armor;

import me.udnek.itemscoreu.customattribute.AttributeUtils;
import me.udnek.itemscoreu.customitem.ConstructableCustomItem;
import me.udnek.itemscoreu.customitem.RepairData;
import me.udnek.rpgu.RpgU;
import me.udnek.rpgu.item.Items;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.components.EquippableComponent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class FerrudamArmor extends ConstructableCustomItem {

    @Override
    public ItemFlag[] getTooltipHides() {return new ItemFlag[]{ItemFlag.HIDE_ATTRIBUTES};}
    @Override
    public boolean getAddDefaultAttributes() {return true;}

    @Override
    public @Nullable RepairData getRepairData() {
        return new RepairData(Items.FERRUDAM_INGOT);
    }

    @Override
    @Nullable
    public EquippableComponent getEquippable() {
        EquippableComponent equippable = new ItemStack(getMaterial()).getItemMeta().getEquippable();
        equippable.setSlot(getMaterial().getEquipmentSlot());
        equippable.setModel(new NamespacedKey(RpgU.getInstance(), "ferrudam"));
        return equippable;
    }

    @Override
    public void initializeAttributes(@NotNull ItemMeta itemMeta) {
        super.initializeAttributes(itemMeta);
        AttributeUtils.appendAttribute(itemMeta, Attribute.ATTACK_DAMAGE, new NamespacedKey(RpgU.getInstance(), "base_attack_damage_" + itemMeta.getEquippable().getSlot()),
                0.04, AttributeModifier.Operation.ADD_SCALAR, itemMeta.getEquippable().getSlot().getGroup());
    }
}
