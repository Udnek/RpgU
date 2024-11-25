package me.udnek.rpgu.item.ingredients;

import me.udnek.itemscoreu.customitem.ConstructableCustomItem;
import me.udnek.itemscoreu.nms.Nms;
import me.udnek.itemscoreu.nms.loot.entry.NmsCustomLootEntryBuilder;
import me.udnek.itemscoreu.nms.loot.pool.NmsLootPoolBuilder;
import me.udnek.itemscoreu.nms.loot.util.ItemStackCreator;
import me.udnek.rpgu.item.Items;
import org.bukkit.Material;
import org.bukkit.loot.LootTables;
import org.jetbrains.annotations.NotNull;

public class PhantomWing extends ConstructableCustomItem {
    @Override
    public @NotNull String getRawId() {return "phantom_wing";}

    @Override
    public @NotNull Material getMaterial() {return Material.GUNPOWDER;}

    @Override
    public void afterInitialization() {
        super.afterInitialization();
        Nms.get().getLootTableContainer(LootTables.PHANTOM.getLootTable()).addPool(new NmsLootPoolBuilder(
                NmsCustomLootEntryBuilder.fromVanilla(
                        LootTables.PHANTOM.getLootTable(),
                        itemStack -> itemStack.getType() == Material.PHANTOM_MEMBRANE,
                        new ItemStackCreator.Custom(Items.PHANTOM_WING)
                ))
        );
    }
}

