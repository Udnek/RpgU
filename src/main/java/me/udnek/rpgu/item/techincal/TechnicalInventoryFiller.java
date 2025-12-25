package me.udnek.rpgu.item.techincal;

import io.papermc.paper.datacomponent.item.CustomModelData;
import io.papermc.paper.datacomponent.item.TooltipDisplay;
import me.udnek.coreu.custom.item.ConstructableCustomItem;
import me.udnek.jeiu.component.Components;
import net.kyori.adventure.key.Key;
import org.bukkit.Color;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TechnicalInventoryFiller extends ConstructableCustomItem{

    @Override
    public @NotNull String getRawId() {
        return "technical_inventory_filler";
    }
    @Override
    public void initializeComponents() {
        super.initializeComponents();
        getComponents().set(Components.ALWAYS_HIDDEN_ITEM.getDefault());
    }
    @Override
    public @Nullable DataSupplier<Key> getItemModel() {
        return DataSupplier.of(null);
    }
    @Override
    public @Nullable DataSupplier<CustomModelData> getCustomModelData() {
        return DataSupplier.of(CustomModelData.customModelData().addColor(Color.WHITE).build());
    }

    @Override
    public @Nullable DataSupplier<TooltipDisplay> getTooltipDisplay() {
        return DataSupplier.of(TooltipDisplay.tooltipDisplay().hideTooltip(true).build());
    }
}
