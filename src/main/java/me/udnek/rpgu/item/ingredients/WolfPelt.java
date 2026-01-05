package me.udnek.rpgu.item.ingredients;

import me.udnek.coreu.custom.component.instance.TranslatableThing;
import me.udnek.coreu.custom.item.ConstructableCustomItem;
import me.udnek.coreu.nms.Nms;
import me.udnek.coreu.nms.loot.entry.NmsCustomEntry;
import me.udnek.coreu.nms.loot.pool.PoolWrapper;
import me.udnek.coreu.nms.loot.util.ItemStackCreator;
import me.udnek.rpgu.item.Items;
import org.bukkit.Material;
import org.bukkit.loot.LootTables;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class WolfPelt extends ConstructableCustomItem {
    @Override
    public @NotNull String getRawId() {return "wolf_pelt";}

    @Override
    public @Nullable TranslatableThing getTranslations() {
        return TranslatableThing.ofEngAndRu("Wolf Pelt", "Волчья шкура");
    }

    @Override
    public void globalInitialization() {
        super.globalInitialization();

        PoolWrapper lootPoolBuilder = new PoolWrapper.Builder(
                new NmsCustomEntry.Builder( new ItemStackCreator.Custom(Items.WOLF_PELT)).fromVanilla(
                        LootTables.COW.getLootTable(),
                        itemStack -> itemStack.getType() == Material.LEATHER
                ).buildAndWrap()
        ).build();

        Nms.get().getLootTableWrapper(LootTables.WOLF.getLootTable()).addPool(lootPoolBuilder);
    }
}
