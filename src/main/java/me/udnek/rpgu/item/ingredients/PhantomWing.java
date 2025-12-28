package me.udnek.rpgu.item.ingredients;

import me.udnek.coreu.custom.component.instance.TranslatableThing;
import me.udnek.coreu.custom.item.ConstructableCustomItem;
import me.udnek.coreu.nms.Nms;
import me.udnek.coreu.nms.loot.entry.NmsCustomLootEntryBuilder;
import me.udnek.coreu.nms.loot.pool.NmsLootPoolBuilder;
import me.udnek.coreu.nms.loot.util.ItemStackCreator;
import me.udnek.rpgu.item.Items;
import org.bukkit.Material;
import org.bukkit.loot.LootTables;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PhantomWing extends ConstructableCustomItem {
    @Override
    public @NotNull String getRawId() {return "phantom_wing";}

    @Override
    public @Nullable TranslatableThing getTranslations() {
        return TranslatableThing.ofEngAndRu("Phantom Wing", "Крыло фантома");
    }

    @Override
    public void globalInitialization() {
        super.globalInitialization();
        Nms.get().getLootTableContainer(LootTables.PHANTOM.getLootTable()).addPool(new NmsLootPoolBuilder(
                NmsCustomLootEntryBuilder.fromVanilla(
                        LootTables.PHANTOM.getLootTable(),
                        itemStack -> itemStack.getType() == Material.PHANTOM_MEMBRANE,
                        new ItemStackCreator.Custom(Items.PHANTOM_WING)
                ))
        );
    }
}

