package me.udnek.rpgu.vanilla;

import io.papermc.paper.registry.keys.StructureKeys;
import me.udnek.coreu.custom.item.CustomItem;
import me.udnek.coreu.nms.MobCategoryWrapper;
import me.udnek.coreu.nms.Nms;
import me.udnek.coreu.nms.loot.condition.LootConditionWrapper;
import me.udnek.coreu.nms.loot.entry.NmsCustomEntry;
import me.udnek.coreu.nms.loot.pool.PoolWrapper;
import me.udnek.coreu.nms.loot.util.ItemStackCreator;
import me.udnek.coreu.nms.structure.BoundingBoxTypeWrapper;
import me.udnek.coreu.nms.structure.StructureSpawnOverrideWrapper;
import me.udnek.coreu.nms.structure.StructureWrapper;
import me.udnek.rpgu.item.Items;
import net.kyori.adventure.key.Key;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.EntityType;
import org.jspecify.annotations.NullMarked;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@NullMarked
public class StructureManaging {

    public static Set<Key> ILLAGER_STRUCTURE_KEYS = Set.of(
            StructureKeys.MANSION, StructureKeys.PILLAGER_OUTPOST,  Key.key("nova_structures:mangrove_witch_hut"), Key.key("nova_structures:witch_villa"),
            Key.key("nova_structures:village_swamp"), Key.key("nova_structures:illager_camp"), Key.key("nova_structures:illager_hideout"),
            Key.key("nova_structures:illager_manor"), Key.key("nova_structures:badlands_miner_outpost")
    );

    public static void run(){
        editSpawnOverrides();

        editContainers();

        editLootTables();
    }

    private static void editSpawnOverrides() {
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

    private static void editContainers() {
        for (Key key : ILLAGER_STRUCTURE_KEYS) {
            Nms.get().convertContainerWithVaultInStructure(new NamespacedKey(key.namespace(), key.value()), lootTable -> Items.ILLAGERITE_KEY.getItem());
        }
    }

    private static void editLootTables() {
        SingleValueMap<String, Float> map = new SingleValueMap<>();
        //nova_structures:mangrove_witch_hut
        map.put("nova_structures:chests/witch_villa/potion_brewing", 0.1f);
        map.put("nova_structures:chests/undead_crypts_grave", 0.04f);
        map.put("nova_structures:chests/witch_villa/general_storage", 0.3f);
        map.put("nova_structures:chests/witch_villa/bedroom", 0.4f);
        //StructureKeys.MANSION
        map.put("nova_structures:chests/mansion_overhaul/supply", 0.03f);
        map.put("nova_structures:chests/mansion_overhaul/mansion_overhaul_generic", 0.05f);
        map.put("minecraft:chests/woodland_mansion", 0.16f);
        //StructureKeys.PILLAGER_OUTPOST
        map.put("nova_structures:chests/pillager_outpost_treasure", 0.18f);
        map.put("nova_structures:chests/pillager_outpost_storage", 0.22f);
        map.put("nova_structures:chests/pillager_outpost_small_treasure", 0.23f);
        //nova_structures:witch_villa
        //nova_structures:village_swamp
        map.put("minecraft:chests/village/village_temple", 0.12f);
        map.put("minecraft:chests/village/village_toolsmith", 0.4f);
        map.put("minecraft:chests/village/village_weaponsmith", 0.5f);
        //nova_structures:illager_camp
        map.put("nova_structures:chests/illager_camp", 0.37f);
        //nova_structures:illager_hideout
        map.put("nova_structures:chests/illager_hideout_weaponry", 0.57f);
        map.put("nova_structures:chests/illager_hideout_heart_loot", 0.57f);
        map.put("nova_structures:chests/illager_hideout_random", 0.01f);
        //nova_structures:illager_manor
        map.put("minecraft:chests/illager_mansion/ravager_chest", 0.57f);
        map.put("minecraft:chests/illager_mansion/ancient_city_raid_chest", 0.33f);
        map.put("nova_structures:chests/dungeon_6", 0.02f);
        map.put("nova_structures:chests/dungeon_7", 0.02f);
        map.put("minecraft:chests/illager_mansion/secret_room", 0.59f);
        map.put("minecraft:chests/illager_mansion/vindicator_chest", 0.46f);
        map.put("minecraft:chests/illager_mansion/evoker_chest", 0.42f);
        map.put("minecraft:chests/illager_mansion/generic", 0.54f);
        //nova_structures:badlands_miner_outpost
        map.put("nova_structures:chests/illager_hideout_utility", 0.55f);
        map.put("nova_structures:chests/dungeon_2", 0.3f);
        map.put("nova_structures:chests/dungeon_4", 0.3f);
        map.put("nova_structures:chests/badland_miner_outpost_towers", 0.37f);
        map.put("nova_structures:chests/badland_miner_outpost_forge", 0.44f);
        map.put("nova_structures:chests/badland_miner_outpost", 0.29f);

        for (String key : map.keySet()) {
            addPoolWithItemToLootTable(Objects.requireNonNull(NamespacedKey.fromString(key)), map.get(key), Items.ILLAGERITE_INGOT);
        }
    }

    public static void addPoolWithItemToLootTable(NamespacedKey key, float chance, CustomItem customItem) {
        Nms.get().getLootTableWrapper(Objects.requireNonNull(Nms.get().getLootTable(key))).addPool(
                new PoolWrapper.Builder(
                        new NmsCustomEntry.Builder(new ItemStackCreator.Custom(customItem))
                                .addCondition(LootConditionWrapper.randomChange(chance))
                                .buildAndWrap())
                        .build());

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

    public static class SingleValueMap<K, V> extends HashMap<K, V> {

        @Override
        public V put(K key, V value) {
            if (containsKey(key)) {
                throw new IllegalStateException("The key already exists: " + key);
            }
            return super.put(key, value);
        }
    }

}
