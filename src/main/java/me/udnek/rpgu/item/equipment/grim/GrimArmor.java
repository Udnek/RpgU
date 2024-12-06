package me.udnek.rpgu.item.equipment.grim;

import me.udnek.itemscoreu.customattribute.AttributeUtils;
import me.udnek.itemscoreu.customattribute.CustomAttributeModifier;
import me.udnek.itemscoreu.customattribute.CustomAttributesContainer;
import me.udnek.itemscoreu.customcomponent.instance.CustomItemAttributesComponent;
import me.udnek.itemscoreu.customequipmentslot.CustomEquipmentSlot;
import me.udnek.itemscoreu.customitem.ConstructableCustomItem;
import me.udnek.rpgu.RpgU;
import me.udnek.rpgu.attribute.Attributes;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.EquipmentSlotGroup;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemRarity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.components.EquippableComponent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class GrimArmor extends ConstructableCustomItem {

    @Override
    public ItemFlag[] getTooltipHides() {return new ItemFlag[]{ItemFlag.HIDE_ATTRIBUTES};}

    @Override
    public @NotNull EquippableComponent getEquippable() {
        EquippableComponent equippable = new ItemStack(getMaterial()).getItemMeta().getEquippable();
        equippable.setSlot(getMaterial().getEquipmentSlot());
        equippable.setModel(new NamespacedKey(RpgU.getInstance(), "grim"));
        return equippable;
    }
    public abstract @NotNull Stats getStats();

    @Override
    public @Nullable ItemRarity getRarity() {
        return ItemRarity.UNCOMMON;
    }

    @Override
    public void initializeAttributes(@NotNull ItemMeta itemMeta) {
        super.initializeAttributes(itemMeta);
        Stats stats = getStats();
        EquipmentSlotGroup slot = getEquippable().getSlot().getGroup();
        AttributeUtils.addAttribute(itemMeta, Attribute.MAX_HEALTH, new NamespacedKey(RpgU.getInstance(), slot+"_max_health"), stats.maxHp, AttributeModifier.Operation.ADD_NUMBER, slot);
        AttributeUtils.addAttribute(itemMeta, Attribute.ARMOR, new NamespacedKey(RpgU.getInstance(), slot+"_armor"), stats.maxHp, AttributeModifier.Operation.ADD_NUMBER, slot);
    }

    @Override
    public void initializeComponents() {
        super.initializeComponents();

        Stats stats = getStats();

        CustomEquipmentSlot slot = CustomEquipmentSlot.getFromVanilla(getEquippable().getSlot().getGroup());
        CustomAttributeModifier MP = new CustomAttributeModifier(stats.magicalPotential, AttributeModifier.Operation.ADD_NUMBER, slot);
        CustomAttributeModifier MD = new CustomAttributeModifier(stats.magicalDefense, AttributeModifier.Operation.ADD_NUMBER, slot);
        CustomAttributeModifier DM = new CustomAttributeModifier(stats.damageMultiplier, AttributeModifier.Operation.ADD_NUMBER, slot);
        getComponents().set(new CustomItemAttributesComponent(new CustomAttributesContainer.Builder()
                .add(Attributes.MAGICAL_POTENTIAL, MP)
                .add(Attributes.MAGICAL_DEFENSE_MULTIPLIER, MD)
                .add(Attributes.MELEE_MAGICAL_DAMAGE_MULTIPLIER, DM)
                .build())
        );
    }



    public record Stats(double magicalPotential, double magicalDefense, double damageMultiplier, double maxHp, double armor){}

}
