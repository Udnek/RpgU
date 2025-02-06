package me.udnek.rpgu.item.equipment.ferrudam.tool;

import me.udnek.itemscoreu.customcomponent.instance.AutoGeneratingFilesItem;
import me.udnek.itemscoreu.customitem.ConstructableCustomItem;
import me.udnek.itemscoreu.customitem.RepairData;
import me.udnek.rpgu.item.Items;
import org.bukkit.inventory.ItemFlag;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class FerrudamTool extends ConstructableCustomItem {
    @Override
    public @Nullable RepairData initializeRepairData() {return new RepairData(Items.FERRUDAM_INGOT);}

    @Override
    public void initializeComponents() {
        super.initializeComponents();
        getComponents().set(AutoGeneratingFilesItem.HANDHELD);
    }

    @Override
    public @Nullable List<ItemFlag> getTooltipHides() {return List.of(ItemFlag.HIDE_ATTRIBUTES);}

    @Override
    public boolean addDefaultAttributes() {return true;}
}
