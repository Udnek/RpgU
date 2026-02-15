package me.udnek.rpgu.vanilla;

import io.papermc.paper.registry.keys.StructureKeys;
import me.udnek.coreu.nms.MobCategoryWrapper;
import me.udnek.coreu.nms.Nms;
import me.udnek.coreu.nms.structure.BoundingBoxTypeWrapper;
import me.udnek.coreu.nms.structure.StructureSpawnOverrideWrapper;
import me.udnek.coreu.nms.structure.StructureWrapper;
import net.kyori.adventure.key.Key;
import org.bukkit.entity.EntityType;
import org.bukkit.loot.LootTables;

import java.util.Set;

import static org.bukkit.loot.LootTables.*;
import static org.bukkit.loot.LootTables.VINDICATOR;
import static org.bukkit.loot.LootTables.WITCH;

public class StructureManaging {

    public static LootTables[] ILLAGER_LOOT_TABLES =  new LootTables[]{ PILLAGER, EVOKER, ILLUSIONER, VINDICATOR, WITCH };
    public static Set<Key> ILLAGER_STRUCTURE_KEYS = Set.of(
            StructureKeys.MANSION, StructureKeys.PILLAGER_OUTPOST,  Key.key("nova_structures:mangrove_witch_hut"), Key.key("nova_structures:witch_villa"),
            Key.key("nova_structures:village_swamp"), Key.key("nova_structures:illager_camp"), Key.key("nova_structures:illager_hideout"),
            Key.key("nova_structures:illager_manor"), Key.key("nova_structures:badlands_miner_outpost")
    );

    public static void run(){
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

            System.out.println("------------");
            structureWrapper.getSpawnOverrides().forEach((a, b) -> {
                System.out.println(a + ": " + b.getSpawns());
            });
        }
    }
}
