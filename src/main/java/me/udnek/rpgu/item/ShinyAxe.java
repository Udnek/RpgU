package me.udnek.rpgu.item;

import me.udnek.itemscoreu.customattribute.AttributeUtils;
import me.udnek.itemscoreu.customitem.ConstructableCustomItem;
import me.udnek.rpgu.attribute.RpgUAttributeUtils;
import me.udnek.rpgu.item.abstraction.RpgUCustomItem;
import me.udnek.rpgu.lore.LoreUtils;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemRarity;
import org.bukkit.inventory.ItemStack;

public class ShinyAxe extends ConstructableCustomItem implements RpgUCustomItem {
    @Override
    public String getRawId() {return "shiny_axe";}
    @Override
    public Material getMaterial() {return Material.DIAMOND_AXE;}
    @Override
    public Integer getMaxDamage() {return 300;}
    @Override
    public Integer getCustomModelData() {return 3100;}
    @Override
    public ItemFlag[] getTooltipHides() {return new ItemFlag[]{ItemFlag.HIDE_ATTRIBUTES};}
    @Override
    public ItemRarity getItemRarity() {return ItemRarity.UNCOMMON;}

    @Override
    protected void modifyFinalItemStack(ItemStack itemStack) {
        super.modifyFinalItemStack(itemStack);
        AttributeUtils.addDefaultAttributes(itemStack);
        RpgUAttributeUtils.addSuitableAttribute(itemStack, Attribute.GENERIC_ATTACK_DAMAGE, "a", 2);
        RpgUAttributeUtils.addSuitableAttribute(itemStack, Attribute.GENERIC_ATTACK_SPEED, "a", -0.3);
        LoreUtils.generateFullLoreAndApply(itemStack);
    }
}
