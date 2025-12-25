package me.udnek.rpgu.item.ingredients;

import me.udnek.coreu.custom.item.ConstructableCustomItem;
import me.udnek.coreu.nms.Nms;
import me.udnek.coreu.nms.loot.entry.NmsCustomLootEntryBuilder;
import me.udnek.coreu.nms.loot.pool.NmsLootPoolBuilder;
import me.udnek.coreu.nms.loot.util.ItemStackCreator;
import me.udnek.rpgu.item.Items;
import org.bukkit.Material;
import org.bukkit.loot.LootTables;
import org.jetbrains.annotations.NotNull;

public class WolfPelt extends ConstructableCustomItem {
    @Override
    public @NotNull String getRawId() {return "wolf_pelt";}

    @Override
    public void afterInitialization() {
        super.afterInitialization();

        NmsLootPoolBuilder lootPoolBuilder = new NmsLootPoolBuilder(
                NmsCustomLootEntryBuilder.fromVanilla(
                        LootTables.COW.getLootTable(),
                        itemStack -> itemStack.getType() == Material.LEATHER,
                        new ItemStackCreator.Custom(Items.WOLF_PELT)
                )
        );

        Nms.get().getLootTableContainer(LootTables.WOLF.getLootTable()).addPool(lootPoolBuilder);
    }
}
