package me.udnek.rpgu.item.artifact;

import me.udnek.itemscoreu.customattribute.CustomKeyedAttributeModifier;
import me.udnek.itemscoreu.customattribute.VanillaAttributesContainer;
import me.udnek.itemscoreu.customcomponent.instance.VanillaAttributesComponent;
import me.udnek.itemscoreu.customitem.ConstructableCustomItem;
import me.udnek.itemscoreu.nms.Nms;
import me.udnek.itemscoreu.nms.loot.entry.NmsCustomLootEntryBuilder;
import me.udnek.itemscoreu.nms.loot.pool.NmsLootPoolBuilder;
import me.udnek.itemscoreu.nms.loot.util.ItemStackCreator;
import me.udnek.rpgu.RpgU;
import me.udnek.rpgu.equipment.slot.EquipmentSlots;
import me.udnek.rpgu.item.Items;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.loot.LootTables;
import org.jetbrains.annotations.NotNull;

public class RustyIronRing extends ConstructableCustomItem {
    @Override
    public @NotNull Material getMaterial() {return Material.GUNPOWDER;}
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
    }

    @Override
    public void initializeComponents() {
        super.initializeComponents();

        CustomKeyedAttributeModifier baseHp = new CustomKeyedAttributeModifier(new NamespacedKey(RpgU.getInstance(), "base_max_health_rusty_iron_ring"), 2, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlots.ARTIFACTS);
        CustomKeyedAttributeModifier baseArmor = new CustomKeyedAttributeModifier(new NamespacedKey(RpgU.getInstance(), "base_armor_rusty_iron_ring"), 1, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlots.ARTIFACTS);

        getComponents().set(new VanillaAttributesComponent(new VanillaAttributesContainer.Builder().add(Attribute.MAX_HEALTH, baseHp).add(Attribute.ARMOR, baseArmor).build()));
    }
}
