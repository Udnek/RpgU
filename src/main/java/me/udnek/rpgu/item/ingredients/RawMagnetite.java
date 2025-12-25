package me.udnek.rpgu.item.ingredients;

import me.udnek.coreu.custom.item.ConstructableCustomItem;
import me.udnek.coreu.nms.Nms;
import me.udnek.coreu.nms.loot.entry.NmsCompositeEntryContainer;
import me.udnek.coreu.nms.loot.entry.NmsCustomLootEntryBuilder;
import me.udnek.coreu.nms.loot.entry.NmsNestedEntryContainer;
import me.udnek.coreu.nms.loot.entry.NmsSingletonEntryContainer;
import me.udnek.coreu.nms.loot.pool.NmsLootPoolBuilder;
import me.udnek.coreu.nms.loot.table.NmsLootTableContainer;
import me.udnek.coreu.nms.loot.util.ItemStackCreator;
import org.jetbrains.annotations.NotNull;

public class RawMagnetite extends ConstructableCustomItem {
    @Override
    public @NotNull String getRawId() {return "raw_magnetite";}

    @Override
    public void globalInitialization() {
        super.globalInitialization();

        NmsLootTableContainer lootTable = Nms.get().getLootTableContainer(Nms.get().getLootTable("minecraft:blocks/iron_ore"));
        NmsCompositeEntryContainer mainEntry = (NmsCompositeEntryContainer) lootTable.getPool(0).getEntry(0);

        NmsSingletonEntryContainer rawIron = (NmsSingletonEntryContainer) mainEntry.getChild(1);

        // CREATING MAGNETITE
        NmsCustomLootEntryBuilder rawGold = new NmsCustomLootEntryBuilder(new ItemStackCreator.Custom(this));
        rawGold.setConditions(rawIron.getConditions());
        rawGold.setFunctions(rawIron.getFunctions());
        // MERGING MAGNETITE AND IRON IN LOOTTABLE
        NmsLootTableContainer newSubLootTable = lootTable.copy();
        newSubLootTable.removePool(0);
        newSubLootTable.addPool(new NmsLootPoolBuilder(rawIron));
        newSubLootTable.addPool(new NmsLootPoolBuilder(rawGold));

        // REMOVING OLD RAW IRON
        mainEntry.removeChild(1);
        // ADDING ORES
        mainEntry.addChild(NmsNestedEntryContainer.newFrom(newSubLootTable));
    }
}
