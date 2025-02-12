package me.udnek.rpgu.item.equipment.flint;

import io.papermc.paper.datacomponent.DataComponentTypes;
import me.udnek.itemscoreu.customcomponent.instance.AutoGeneratingFilesItem;
import me.udnek.itemscoreu.customitem.ConstructableCustomItem;
import me.udnek.itemscoreu.customitem.RepairData;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.inventory.ItemFlag;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class FlintTool extends ConstructableCustomItem {

    @Override
    public @Nullable DataSupplier<Integer> getMaxDamage() {
        return DataSupplier.of((int) (getMaterial().getDefaultData(DataComponentTypes.MAX_DAMAGE) * 1.3));
    }
    @Override
    public @Nullable List<ItemFlag> getTooltipHides() {return List.of(ItemFlag.HIDE_ATTRIBUTES);}


    @Override
    public void initializeComponents() {
        super.initializeComponents();
        getComponents().set(AutoGeneratingFilesItem.HANDHELD);
    }

    @Override
    public boolean addDefaultAttributes() {return true;}


    @Override
    public @Nullable RepairData initializeRepairData() {
        HashSet<Material> materials = new HashSet<>(Set.of(Material.FLINT));
        materials.addAll(Tag.ITEMS_STONE_TOOL_MATERIALS.getValues());
        return new RepairData(Set.of(), materials);
    }
}
