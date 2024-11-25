package me.udnek.rpgu.item.equipment;

import me.udnek.itemscoreu.customattribute.AttributeUtils;
import me.udnek.itemscoreu.customitem.ConstructableCustomItem;
import me.udnek.itemscoreu.nms.Nms;
import me.udnek.itemscoreu.nms.loot.entry.NmsCustomLootEntryBuilder;
import me.udnek.itemscoreu.nms.loot.pool.NmsLootPoolBuilder;
import me.udnek.itemscoreu.nms.loot.util.ItemStackCreator;
import me.udnek.rpgu.item.Items;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.EquipmentSlotGroup;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemRarity;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.loot.LootTables;
import org.jetbrains.annotations.NotNull;

public class ShinyAxe extends ConstructableCustomItem {
    @Override
    public @NotNull String getRawId() {return "shiny_axe";}
    @Override
    public @NotNull Material getMaterial() {return Material.DIAMOND_AXE;}
    @Override
    public Integer getMaxDamage() {return 300;}
    @Override
    public ItemFlag[] getTooltipHides() {return new ItemFlag[]{ItemFlag.HIDE_ATTRIBUTES};}
    @Override
    public ItemRarity getRarity() {return ItemRarity.UNCOMMON;}
    @Override
    public boolean getAddDefaultAttributes() {return true;}

    @Override
    public void initializeAttributes(@NotNull ItemMeta itemMeta) {
        super.initializeAttributes(itemMeta);
        AttributeUtils.appendAttribute(itemMeta, Attribute.ATTACK_DAMAGE, null, 2, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.MAINHAND);
        AttributeUtils.appendAttribute(itemMeta, Attribute.ATTACK_SPEED, null, -0.3, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.MAINHAND);
    }

    @Override
    public void afterInitialization() {
        super.afterInitialization();
        Nms.get().getLootTableContainer(LootTables.PIGLIN_BRUTE.getLootTable())
                        .addPool(new NmsLootPoolBuilder(
                                NmsCustomLootEntryBuilder.fromVanilla(
                                        LootTables.ZOMBIFIED_PIGLIN.getLootTable(),
                                        itemStack -> itemStack.getType() == Material.GOLD_INGOT,
                                        new ItemStackCreator.Custom(Items.SHINY_AXE)
                                )
                        ));
    }
}
