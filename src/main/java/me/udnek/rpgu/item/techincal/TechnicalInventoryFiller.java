package me.udnek.rpgu.item.techincal;

import me.udnek.itemscoreu.customitem.ConstructableCustomItem;
import me.udnek.jeiu.component.ComponentTypes;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TechnicalInventoryFiller extends ConstructableCustomItem{
    @Override
    public void afterInitialization() {
        setComponent(ComponentTypes.TECHNICAL_ITEM.getDefault());
    }

    @Override
    public @NotNull String getRawId() {
        return "technical_inventory_filler";
    }
    @Override
    public @Nullable Integer getCustomModelData() {return 5000;}
    @Override
    public boolean getHideTooltip() {return true;}
    @Override
    public @NotNull Material getMaterial() {
        return Material.FEATHER;
    }
}
