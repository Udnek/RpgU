package me.udnek.rpgu.item.equipment.melee;

import me.udnek.itemscoreu.customattribute.CustomAttributeModifier;
import me.udnek.itemscoreu.customattribute.CustomAttributesContainer;
import me.udnek.itemscoreu.customcomponent.instance.CustomItemAttributesComponent;
import me.udnek.itemscoreu.customequipmentslot.CustomEquipmentSlot;
import me.udnek.itemscoreu.customitem.ConstructableCustomItem;
import me.udnek.rpgu.RpgU;
import me.udnek.rpgu.attribute.Attributes;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.components.EquippableComponent;
import org.jetbrains.annotations.NotNull;

public abstract class MeleeArmor extends ConstructableCustomItem {

    @Override
    public ItemFlag[] getTooltipHides() {return new ItemFlag[]{ItemFlag.HIDE_ATTRIBUTES};}

    @Override
    public @NotNull EquippableComponent getEquippable() {
        EquippableComponent equippable = new ItemStack(getMaterial()).getItemMeta().getEquippable();
        equippable.setSlot(getMaterial().getEquipmentSlot());
        equippable.setModel(new NamespacedKey(RpgU.getInstance(), "melee"));
        return equippable;
    }

    @Override
    public void initializeComponents() {
        super.initializeComponents();

        CustomAttributeModifier MP = new CustomAttributeModifier(3, AttributeModifier.Operation.ADD_NUMBER, CustomEquipmentSlot.getFromVanilla(getEquippable().getSlot().getGroup()));
        CustomAttributeModifier MD = new CustomAttributeModifier(1, AttributeModifier.Operation.ADD_NUMBER, CustomEquipmentSlot.getFromVanilla(getEquippable().getSlot().getGroup()));
        getComponents().set(new CustomItemAttributesComponent(new CustomAttributesContainer.Builder().add(Attributes.MAGICAL_POTENTIAL, MP).add(Attributes.MAGICAL_DEFENSE_MULTIPLIER, MD).build()));
    }


}
