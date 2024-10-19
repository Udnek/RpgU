package me.udnek.rpgu.item.equipment;

import me.udnek.itemscoreu.customitem.ConstructableCustomItem;
import me.udnek.itemscoreu.nms.Nms;
import me.udnek.itemscoreu.nms.loot.ItemStackCreator;
import me.udnek.itemscoreu.nms.loot.entry.NmsCustomLootEntryBuilder;
import me.udnek.itemscoreu.nms.loot.pool.NmsLootPoolBuilder;
import me.udnek.rpgu.attribute.RpgUAttributeUtils;
import me.udnek.rpgu.item.Items;
import me.udnek.rpgu.item.RpgUCustomItem;
import me.udnek.rpgu.lore.LoreUtils;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemRarity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.loot.LootTables;
import org.jetbrains.annotations.NotNull;

public class ShinyAxe extends ConstructableCustomItem implements RpgUCustomItem {
    @Override
    public @NotNull String getRawId() {return "shiny_axe";}
    @Override
    public @NotNull Material getMaterial() {return Material.DIAMOND_AXE;}
    @Override
    public Integer getMaxDamage() {return 300;}
    @Override
    public Integer getCustomModelData() {return 3100;}
    @Override
    public ItemFlag[] getTooltipHides() {return new ItemFlag[]{ItemFlag.HIDE_ATTRIBUTES};}
    @Override
    public ItemRarity getItemRarity() {return ItemRarity.UNCOMMON;}
    @Override
    public boolean getAddDefaultAttributes() {return true;}

    @Override
    protected void modifyFinalItemStack(ItemStack itemStack) {
        super.modifyFinalItemStack(itemStack);
        RpgUAttributeUtils.addSuitableAttribute(itemStack, Attribute.GENERIC_ATTACK_DAMAGE, null, 2);
        RpgUAttributeUtils.addSuitableAttribute(itemStack, Attribute.GENERIC_ATTACK_SPEED, null, -0.3);
        LoreUtils.generateFullLoreAndApply(itemStack);
    }

    @Override
    public void afterInitialization() {
        super.afterInitialization();
        Nms.get().addLootPool(
                LootTables.PIGLIN_BRUTE.getLootTable(),
                new NmsLootPoolBuilder(
                        NmsCustomLootEntryBuilder.fromVanilla(
                                LootTables.ZOMBIFIED_PIGLIN.getLootTable(),
                                itemStack -> itemStack.getType() == Material.GOLD_NUGGET,
                                new ItemStackCreator.Custom(Items.SHINY_AXE)
                        )
                )
        );
    }
}
