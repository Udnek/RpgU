package me.udnek.rpgu.item.equipment.flint;

import io.papermc.paper.datacomponent.DataComponentTypes;
import me.udnek.itemscoreu.customitem.ConstructableCustomItem;
import me.udnek.itemscoreu.customitem.RepairData;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.jetbrains.annotations.Nullable;

public abstract class FlintTool extends ConstructableCustomItem {

    @Override
    public @Nullable Integer getMaxDamage() {
        return (int) (getMaterial().getDefaultData(DataComponentTypes.MAX_DAMAGE) * 1.3);
    }
    @Override
    public ItemFlag[] getTooltipHides() {return new ItemFlag[]{ItemFlag.HIDE_ATTRIBUTES};}
    @Override
    public boolean getAddDefaultAttributes() {return true;}

    @Override
    public @Nullable RepairData getRepairData() {return new RepairData(Material.FLINT);}
}
