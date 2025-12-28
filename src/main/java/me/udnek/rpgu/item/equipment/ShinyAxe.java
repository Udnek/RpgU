package me.udnek.rpgu.item.equipment;

import me.udnek.coreu.custom.attribute.AttributeUtils;
import me.udnek.coreu.custom.component.instance.AutoGeneratingFilesItem;
import me.udnek.coreu.custom.item.ConstructableCustomItem;
import me.udnek.coreu.custom.item.RepairData;
import me.udnek.coreu.nms.Nms;
import me.udnek.coreu.nms.loot.entry.NmsCustomLootEntryBuilder;
import me.udnek.coreu.nms.loot.pool.NmsLootPoolBuilder;
import me.udnek.coreu.nms.loot.util.ItemStackCreator;
import me.udnek.rpgu.item.Items;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.EquipmentSlotGroup;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemRarity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.loot.LootTables;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ShinyAxe extends ConstructableCustomItem {
    @Override
    public @NotNull String getRawId() {return "shiny_axe";}
    @Override
    public @NotNull Material getMaterial() {return Material.DIAMOND_AXE;}
    @Override
    public @Nullable DataSupplier<Integer> getMaxDamage() {return DataSupplier.of(300);}
    @Override
    public @Nullable List<ItemFlag> getTooltipHides() {return List.of(ItemFlag.HIDE_ATTRIBUTES);}
    @Override
    public @Nullable DataSupplier<ItemRarity> getRarity() {return DataSupplier.of(ItemRarity.UNCOMMON);}

    @Override
    public void initializeAdditionalAttributes(@NotNull ItemStack itemStack) {
        super.initializeAdditionalAttributes(itemStack);
        AttributeUtils.appendAttribute(itemStack, Attribute.ATTACK_DAMAGE, null, 2, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.MAINHAND);
        AttributeUtils.appendAttribute(itemStack, Attribute.ATTACK_SPEED, null, -0.3, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.MAINHAND);
    }

    @Override
    public void initializeComponents() {
        super.initializeComponents();
        getComponents().set(AutoGeneratingFilesItem.HANDHELD);
    }

    @Override
    public void globalInitialization() {
        super.globalInitialization();
        Nms.get().getLootTableContainer(LootTables.PIGLIN_BRUTE.getLootTable())
                        .addPool(new NmsLootPoolBuilder(
                                NmsCustomLootEntryBuilder.fromVanilla(
                                        LootTables.ZOMBIFIED_PIGLIN.getLootTable(),
                                        itemStack -> itemStack.getType() == Material.GOLD_INGOT,
                                        new ItemStackCreator.Custom(Items.SHINY_AXE)
                                )
                        ));
    }

    @Override
    public @Nullable RepairData initializeRepairData() {
        return new RepairData(Material.GOLD_INGOT);
    }
}
