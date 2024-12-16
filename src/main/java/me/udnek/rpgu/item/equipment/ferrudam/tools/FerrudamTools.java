package me.udnek.rpgu.item.equipment.ferrudam.tools;

import me.udnek.itemscoreu.customitem.ConstructableCustomItem;
import me.udnek.itemscoreu.customitem.RepairData;
import me.udnek.rpgu.item.Items;
import org.bukkit.inventory.ItemFlag;
import org.jetbrains.annotations.Nullable;

public abstract class FerrudamTools extends ConstructableCustomItem {
    @Override
    public @Nullable RepairData getRepairData() {
        return new RepairData(Items.FERRUDAM_INGOT);
    }
    @Override
    public ItemFlag[] getTooltipHides() {return new ItemFlag[]{ItemFlag.HIDE_ATTRIBUTES};}
    @Override
    public boolean getAddDefaultAttributes() {return true;}
}
