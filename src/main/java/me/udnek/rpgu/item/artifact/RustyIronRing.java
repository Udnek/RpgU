package me.udnek.rpgu.item.artifact;

import me.udnek.coreu.custom.attribute.CustomKeyedAttributeModifier;
import me.udnek.coreu.custom.attribute.VanillaAttributesContainer;
import me.udnek.coreu.custom.component.instance.VanillaAttributesComponent;
import me.udnek.coreu.custom.item.ConstructableCustomItem;
import me.udnek.coreu.nms.Nms;
import me.udnek.coreu.nms.loot.entry.NmsCustomLootEntryBuilder;
import me.udnek.coreu.nms.loot.pool.NmsLootPoolBuilder;
import me.udnek.coreu.nms.loot.util.ItemStackCreator;
import me.udnek.rpgu.RpgU;
import me.udnek.rpgu.equipment.slot.EquipmentSlots;
import me.udnek.rpgu.item.Items;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.loot.LootTables;
import org.jetbrains.annotations.NotNull;

public class RustyIronRing extends ConstructableCustomItem {
    @Override
    public @NotNull String getRawId() {return "rusty_iron_ring";}

    @Override
    public void afterInitialization() {
        super.afterInitialization();
        NmsLootPoolBuilder lootPoolBuilder = new NmsLootPoolBuilder(
                NmsCustomLootEntryBuilder.fromVanilla(
                        LootTables.ZOMBIFIED_PIGLIN.getLootTable(),
                        itemStack -> itemStack.getType() == Material.GOLD_INGOT,
                        new ItemStackCreator.Custom(Items.RUSTY_IRON_RING)
                )
        );
        Nms.get().getLootTableContainer(LootTables.ZOMBIE.getLootTable()).addPool(lootPoolBuilder);
        Nms.get().getLootTableContainer(LootTables.HUSK.getLootTable()).addPool(lootPoolBuilder);
        Nms.get().getLootTableContainer(LootTables.DROWNED.getLootTable()).addPool(lootPoolBuilder);
        Nms.get().getLootTableContainer(LootTables.STRAY.getLootTable()).addPool(lootPoolBuilder);
        Nms.get().getLootTableContainer(Bukkit.getLootTable(NamespacedKey.minecraft("entities/bogged"))).addPool(lootPoolBuilder);
    }

    @Override
    public void initializeComponents() {
        super.initializeComponents();

        CustomKeyedAttributeModifier baseHp = new CustomKeyedAttributeModifier(new NamespacedKey(RpgU.getInstance(), "base_max_health_rusty_iron_ring"), 1, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlots.ARTIFACTS);
        CustomKeyedAttributeModifier baseArmor = new CustomKeyedAttributeModifier(new NamespacedKey(RpgU.getInstance(), "base_armor_rusty_iron_ring"), 1, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlots.ARTIFACTS);

        getComponents().set(new VanillaAttributesComponent(new VanillaAttributesContainer.Builder().add(Attribute.MAX_HEALTH, baseHp).add(Attribute.ARMOR, baseArmor).build()));
    }
}
