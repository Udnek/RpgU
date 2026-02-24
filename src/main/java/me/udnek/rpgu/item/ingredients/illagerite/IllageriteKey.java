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
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.Recipe;
import org.bukkit.loot.LootTables;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NullMarked;

import java.util.function.Consumer;

import static org.bukkit.loot.LootTables.*;

@NullMarked
public class IllageriteKey extends ConstructableCustomItem {

    public static LootTables[] ILLAGER_LOOT_TABLES =  new LootTables[]{ PILLAGER, EVOKER, ILLUSIONER, VINDICATOR, WITCH };

    @Override
    public String getRawId() {
        return "illagerite_key";
    }
    @Override
    public @Nullable TranslatableThing getTranslations() {
        return TranslatableThing.ofEngAndRu("Illagerite Key", "Злодеянитовый ключь");
    }

    @Override
    protected void generateRecipes(Consumer<Recipe> consumer) {
        //TODO
    }

    @Override
    public void globalInitialization() {
        super.globalInitialization();

        for (LootTables illagerLootTable : ILLAGER_LOOT_TABLES) {
            Nms.get().getLootTableWrapper(illagerLootTable.getLootTable()).addPool(new PoolWrapper.Builder(
                    new NmsCustomEntry.Builder(new ItemStackCreator.Custom(this))
                            .addCondition(LootConditionWrapper.randomChange(0.05f))
                            .addCondition(LootConditionWrapper.structure(StructureManaging.ILLAGER_STRUCTURE_KEYS))
                            .buildAndWrap())
                    .build());
        }

        for (Key key : StructureManaging.ILLAGER_STRUCTURE_KEYS) {
            Nms.get().convertContainerWithVaultInStructure(new NamespacedKey(key.namespace(), key.value()), lootTable -> this.getItem());
        }
    }
}
