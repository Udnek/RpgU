package me.udnek.rpgu.item.equipment.ferrudam.tool;

import me.udnek.coreu.custom.component.instance.AutoGeneratingFilesItem;
import me.udnek.coreu.custom.item.ConstructableCustomItem;
import me.udnek.coreu.custom.item.RepairData;
import me.udnek.rpgu.item.Items;
import org.jetbrains.annotations.Nullable;

public abstract class FerrudamTool extends ConstructableCustomItem {
    @Override
    public @Nullable RepairData initializeRepairData() {
        return new RepairData(Items.FERRUDAM_INGOT);
    }

    @Override
    public void initializeComponents() {
        super.initializeComponents();
        getComponents().set(AutoGeneratingFilesItem.HANDHELD);
    }
}
