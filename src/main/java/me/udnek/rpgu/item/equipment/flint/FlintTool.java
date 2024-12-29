package me.udnek.rpgu.item.equipment.flint;

import io.papermc.paper.datacomponent.DataComponentTypes;
import me.udnek.itemscoreu.customitem.ConstructableCustomItem;
import me.udnek.itemscoreu.customitem.RepairData;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class FlintTool extends ConstructableCustomItem {

    @Override
    public @Nullable DataSupplier<Integer> getMaxDamage() {
        return DataSupplier.of((int) (getMaterial().getDefaultData(DataComponentTypes.MAX_DAMAGE) * 1.3));
    }
    @Override
    public @Nullable List<ItemFlag> getTooltipHides() {return List.of(ItemFlag.HIDE_ATTRIBUTES);}

    @Override
    public boolean addDefaultAttributes() {return true;}


    @Override
    public @Nullable RepairData initializeRepairData() {return new RepairData(Material.FLINT);}
}
