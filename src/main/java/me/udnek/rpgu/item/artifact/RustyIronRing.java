package me.udnek.rpgu.item.artifact;

import me.udnek.coreu.custom.attribute.CustomKeyedAttributeModifier;
import me.udnek.coreu.custom.attribute.VanillaAttributesContainer;
import me.udnek.coreu.custom.component.instance.TranslatableThing;
import me.udnek.coreu.custom.component.instance.VanillaAttributedItem;
import me.udnek.coreu.custom.item.ConstructableCustomItem;
import me.udnek.coreu.custom.item.ItemUtils;
import me.udnek.coreu.nms.Nms;
import me.udnek.coreu.nms.loot.entry.NmsCustomEntry;
import me.udnek.coreu.nms.loot.pool.PoolWrapper;
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
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class RustyIronRing extends ConstructableCustomItem {
    @Override
    public @NotNull String getRawId() {
        return "rusty_iron_ring";
    }

    @Override
    public @Nullable TranslatableThing getTranslations() {
        return TranslatableThing.ofEngAndRu("Rusty Iron Ring", "Ржавое железное кольцо");
    }

    @Override
    public void globalInitialization() {
        super.globalInitialization();
        PoolWrapper pool = new PoolWrapper.Builder(
                new NmsCustomEntry.Builder(new ItemStackCreator.Custom(Items.RUSTY_IRON_RING)).fromVanilla(
                        LootTables.ZOMBIFIED_PIGLIN.getLootTable(),
                        stack -> ItemUtils.isVanillaMaterial(stack, Material.GOLD_INGOT)
                ).buildAndWrap()
        ).build();
        Nms.get().getLootTableWrapper(LootTables.ZOMBIE.getLootTable()).addPool(pool);
        Nms.get().getLootTableWrapper(LootTables.HUSK.getLootTable()).addPool(pool);
        Nms.get().getLootTableWrapper(LootTables.DROWNED.getLootTable()).addPool(pool);
        Nms.get().getLootTableWrapper(LootTables.STRAY.getLootTable()).addPool(pool);
        Nms.get().getLootTableWrapper(Objects.requireNonNull(Bukkit.getLootTable(NamespacedKey.minecraft("entities/bogged")))).addPool(pool);
    }

    @Override
    public void initializeComponents() {
        super.initializeComponents();

        CustomKeyedAttributeModifier baseHp = new CustomKeyedAttributeModifier(
                new NamespacedKey(RpgU.getInstance(), "base_max_health_rusty_iron_ring"),
                1, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlots.ARTIFACTS);
        CustomKeyedAttributeModifier baseArmor = new CustomKeyedAttributeModifier(
                new NamespacedKey(RpgU.getInstance(), "base_armor_rusty_iron_ring"),
                1, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlots.ARTIFACTS);

        getComponents().set(new VanillaAttributedItem(new VanillaAttributesContainer.Builder()
                .add(Attribute.MAX_HEALTH, baseHp)
                .add(Attribute.ARMOR, baseArmor).build()));
    }
}
