package me.udnek.rpgu.item.ingredients;

import io.papermc.paper.registry.keys.StructureKeys;
import me.udnek.coreu.custom.component.instance.TranslatableThing;
import me.udnek.coreu.custom.item.ConstructableCustomItem;
import me.udnek.coreu.nms.Nms;
import me.udnek.coreu.nms.loot.condition.LootConditionWrapper;
import me.udnek.coreu.nms.loot.entry.*;
import me.udnek.coreu.nms.loot.pool.PoolWrapper;
import me.udnek.coreu.nms.loot.table.LootTableWrapper;
import me.udnek.coreu.nms.loot.util.ItemStackCreator;
import me.udnek.rpgu.item.Items;
import org.bukkit.NamespacedKey;
import org.bukkit.loot.LootTables;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class RawMagnetite extends ConstructableCustomItem {
    @Override
    public @NotNull String getRawId() {return "raw_magnetite";}

    @Override
    public @Nullable TranslatableThing getTranslations() {
        return TranslatableThing.ofEngAndRu("Raw Magnetite", "Рудный магнетит");
    }

    @Override
    public void globalInitialization() {
        super.globalInitialization();

        LootTableWrapper lootTable = Nms.get().getLootTableWrapper(
                Objects.requireNonNull(Nms.get().getLootTable(NamespacedKey.minecraft("blocks/iron_ore")))
        );
        CompositeEntryWrapper mainEntry = (CompositeEntryWrapper) lootTable.getPool(0).getEntry(0);

        SingletonEntryWrapper ironEntry = (SingletonEntryWrapper) mainEntry.getChild(1);

        EntryWrapper magnetiteEntry = new NmsCustomEntry.Builder(new ItemStackCreator.Custom(this))
                .setFunctions(ironEntry.getFunctions())
                .setConditions(ironEntry.getConditions()).buildAndWrap();


        LootTableWrapper ironAndMag = lootTable.copy();
        // clears
        ironAndMag.removePool(0);
        // adding new
        ironAndMag.addPool(new PoolWrapper.Builder(ironEntry).build());
        ironAndMag.addPool(new PoolWrapper.Builder(magnetiteEntry).build());

        // removing old
        mainEntry.removeChild(1);
        // adding new
        mainEntry.addChild(NestedEntryWrapper.createFromLootTable(ironAndMag));



        Nms.get().getLootTableWrapper(LootTables.SKELETON.getLootTable()).addPool(
                new PoolWrapper.Builder(
                        new NmsCustomEntry.Builder(new ItemStackCreator.Custom(Items.SPHERE_OF_DISCORD))
                                .addCondition(LootConditionWrapper.structure(StructureKeys.MANSION.key()))
                                .buildAndWrap()
                ).build()
        );
    }
}
