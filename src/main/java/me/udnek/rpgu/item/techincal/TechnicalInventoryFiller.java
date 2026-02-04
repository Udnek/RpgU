package me.udnek.rpgu.item.techincal;

import io.papermc.paper.datacomponent.DataComponentTypes;
import io.papermc.paper.datacomponent.item.TooltipDisplay;
import me.udnek.coreu.custom.item.ConstructableCustomItem;
import me.udnek.jeiu.component.Components;
import net.kyori.adventure.key.Key;
import org.bukkit.Material;
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
        return DataSupplier.of(Material.GRAY_STAINED_GLASS_PANE.getDefaultData(DataComponentTypes.ITEM_MODEL));
    }

    @Override
    public @Nullable DataSupplier<TooltipDisplay> getTooltipDisplay() {
        return DataSupplier.of(TooltipDisplay.tooltipDisplay().hideTooltip(true).build());
    }
}
