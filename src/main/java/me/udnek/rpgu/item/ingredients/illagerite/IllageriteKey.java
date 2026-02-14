package me.udnek.rpgu.item.ingredients.illagerite;

import me.udnek.coreu.custom.component.instance.TranslatableThing;
import me.udnek.coreu.custom.item.ConstructableCustomItem;
import me.udnek.coreu.nms.Nms;
import me.udnek.coreu.nms.loot.condition.LootConditionWrapper;
import me.udnek.coreu.nms.loot.entry.NmsCustomEntry;
import me.udnek.coreu.nms.loot.pool.PoolWrapper;
import me.udnek.coreu.nms.loot.util.ItemStackCreator;
import me.udnek.rpgu.vanilla.StructureManaging;
import net.kyori.adventure.key.Key;
import org.bukkit.inventory.Recipe;
import org.bukkit.loot.LootTables;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NullMarked;

import java.util.function.Consumer;

@NullMarked
public class IllageriteKey extends ConstructableCustomItem {



    @Override
    public String getRawId() {return "illagerite_key";
    }
    @Override
    public @Nullable TranslatableThing getTranslations() {return TranslatableThing.ofEngAndRu("Illagerite Key", "Злодеянитовый ключь");}

    @Override
    protected void generateRecipes(Consumer<Recipe> consumer) {
        //TODO
    }

    @Override
    public void globalInitialization() {
        super.globalInitialization();

        for (LootTables illagerLootTable: StructureManaging.ILLAGER_LOOT_TABLES) {
            Nms.get().getLootTableWrapper(illagerLootTable.getLootTable()).addPool(new PoolWrapper.Builder(
                    new NmsCustomEntry.Builder(new ItemStackCreator.Custom(this))
                        .addCondition(LootConditionWrapper.randomChange(0.05f))
                        .addCondition(LootConditionWrapper.structure(StructureManaging.ILLAGER_STRUCTURE_KEYS))
                        .buildAndWrap())
                    .build());
        }
    }
}
