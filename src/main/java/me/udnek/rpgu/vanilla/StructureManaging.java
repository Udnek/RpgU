package me.udnek.rpgu.vanilla;

import io.papermc.paper.registry.keys.StructureKeys;
import me.udnek.coreu.nms.MobCategoryWrapper;
import me.udnek.coreu.nms.Nms;
import me.udnek.coreu.nms.structure.BoundingBoxTypeWrapper;
import me.udnek.coreu.nms.structure.StructureSpawnOverrideWrapper;
import me.udnek.coreu.nms.structure.StructureWrapper;
import me.udnek.rpgu.item.Items;
import net.kyori.adventure.key.Key;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.EntityType;
import org.jspecify.annotations.NullMarked;

import java.util.List;
import java.util.Set;

@NullMarked
public class StructureManaging {

    public static Set<Key> ILLAGER_STRUCTURE_KEYS = Set.of(
            StructureKeys.MANSION, StructureKeys.PILLAGER_OUTPOST,  Key.key("nova_structures:mangrove_witch_hut"), Key.key("nova_structures:witch_villa"),
            Key.key("nova_structures:village_swamp"), Key.key("nova_structures:illager_camp"), Key.key("nova_structures:illager_hideout"),
            Key.key("nova_structures:illager_manor"), Key.key("nova_structures:badlands_miner_outpost")
    );

    public static void run(){
        addPoolToStructureSpawnOverrides(StructureKeys.MANSION, List.of(
                new StructureSpawnOverrideWrapper.SpawnEntry(1, EntityType.ILLUSIONER, 1, 1),
                new StructureSpawnOverrideWrapper.SpawnEntry(2, EntityType.WITCH, 1, 2),
                new StructureSpawnOverrideWrapper.SpawnEntry(3, EntityType.PILLAGER, 2, 3),
                new StructureSpawnOverrideWrapper.SpawnEntry(3, EntityType.VINDICATOR, 1, 3)
        ));
        addPoolToStructureSpawnOverrides(Key.key("nova_structures:illager_camp"), List.of(
                new StructureSpawnOverrideWrapper.SpawnEntry(1, EntityType.PILLAGER, 1, 1)
        ));
        addPoolToStructureSpawnOverrides(Key.key("nova_structures:illager_hideout"), List.of(
                new StructureSpawnOverrideWrapper.SpawnEntry(1, EntityType.ILLUSIONER, 1, 1),
                new StructureSpawnOverrideWrapper.SpawnEntry(2, EntityType.WITCH, 1, 1),
                new StructureSpawnOverrideWrapper.SpawnEntry(3, EntityType.PILLAGER, 2, 3),
                new StructureSpawnOverrideWrapper.SpawnEntry(3, EntityType.VINDICATOR, 1, 3)
        ));
        addPoolToStructureSpawnOverrides(Key.key("nova_structures:village_swamp"), List.of(
                new StructureSpawnOverrideWrapper.SpawnEntry(1, EntityType.WITCH, 1, 3)
        ));
        addPoolToStructureSpawnOverrides(Key.key("nova_structures:witch_villa"), List.of(
                new StructureSpawnOverrideWrapper.SpawnEntry(3, EntityType.WITCH, 1, 3),
                new StructureSpawnOverrideWrapper.SpawnEntry(1, EntityType.SLIME, 1, 1)
        ));
        addPoolToStructureSpawnOverrides(Key.key("nova_structures:illager_manor"), List.of(
                new StructureSpawnOverrideWrapper.SpawnEntry(1, EntityType.PILLAGER, 2, 3),
                new StructureSpawnOverrideWrapper.SpawnEntry(1, EntityType.VINDICATOR, 1, 3)
        ));
        addPoolToStructureSpawnOverrides(Key.key("nova_structures:badlands_miner_outpost"), List.of(
                new StructureSpawnOverrideWrapper.SpawnEntry(1, EntityType.ILLUSIONER, 1, 1),
                new StructureSpawnOverrideWrapper.SpawnEntry(2, EntityType.WITCH, 1, 1),
                new StructureSpawnOverrideWrapper.SpawnEntry(3, EntityType.PILLAGER, 2, 3),
                new StructureSpawnOverrideWrapper.SpawnEntry(3, EntityType.VINDICATOR, 1, 3)
        ));

        /*System.out.println("///////////////////////////////////////");
        for (NamespacedKey lootTableId : StructureCache.getInstance().getLootTablesForStructure(new NamespacedKey(key.namespace(), key.value()) )) {
            System.out.println(lootTableId);
        }*/
        Key mansionKey = StructureKeys.MANSION.key();
        Nms.get().convertContainerWithVaultInStructure(new NamespacedKey(mansionKey.namespace(), mansionKey.value()), lootTable -> {
            System.out.println(lootTable.key());
            return Items.ILLAGERITE_KEY.getItem();
        });
    }

    public static void addPoolToStructureSpawnOverrides(Key structureKey, List<StructureSpawnOverrideWrapper.SpawnEntry> entries) {
        addPoolToStructureSpawnOverrides(structureKey, entries, MobCategoryWrapper.MONSTER);
    }

    public static void addPoolToStructureSpawnOverrides(Key structureKey, List<StructureSpawnOverrideWrapper.SpawnEntry> entries, MobCategoryWrapper category) {
        StructureWrapper structureWrapper = Nms.get().getStructureWrapper(structureKey);
        if (structureWrapper == null) throw new RuntimeException("Structure not found!");
        structureWrapper.editSpawnOverrides(
                (map -> {
                    StructureSpawnOverrideWrapper wrapper = StructureSpawnOverrideWrapper.of(BoundingBoxTypeWrapper.PIECE);
                    for(StructureSpawnOverrideWrapper.SpawnEntry entry: entries){
                        wrapper.addSpawn(entry);
                    }
                    map.put(category, wrapper);
                    return map;
                }));
    }
}
