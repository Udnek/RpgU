package me.udnek.rpgu.vanilla;

import io.papermc.paper.registry.keys.StructureKeys;
import me.udnek.coreu.nms.MobCategoryWrapper;
import me.udnek.coreu.nms.Nms;
import me.udnek.coreu.nms.structure.BoundingBoxTypeWrapper;
import me.udnek.coreu.nms.structure.StructureSpawnOverrideWrapper;
import me.udnek.coreu.nms.structure.StructureWrapper;
import net.kyori.adventure.key.Key;
import org.bukkit.entity.EntityType;
import org.jspecify.annotations.NullMarked;

import java.util.List;

@NullMarked
public class StructureManaging {

    public static void run(){
<<<<<<< HEAD
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
    }
=======
        for (Key key: ILLAGER_STRUCTURE_KEYS) {
            StructureWrapper structureWrapper = Nms.get().getStructureWrapper(key);
            if (structureWrapper == null) continue;
            System.out.println("///////////////////////////////////////////////////////////////////////////////");
            System.out.println(key);
            structureWrapper.getSpawnOverrides().forEach((a, b) -> {
                        System.out.println(a + ": " + b.getSpawns());
                    });
            structureWrapper.editSpawnOverrides(
                    (map -> {
                        StructureSpawnOverrideWrapper wrapper = StructureSpawnOverrideWrapper.of(BoundingBoxTypeWrapper.PIECE);
                        wrapper.addSpawn(new StructureSpawnOverrideWrapper.SpawnEntry(1, EntityType.WITCH, 1, 1));
                        wrapper.addSpawn(new StructureSpawnOverrideWrapper.SpawnEntry(5, EntityType.PILLAGER, 20, 1024));
                        map.put(MobCategoryWrapper.MONSTER, wrapper);
                        System.out.println("------------");
                        map.forEach((a, b) -> {
                            System.out.println(a + ": " + b.getSpawns());
                        });
                        return map;
                    }));
>>>>>>> origin/main

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
