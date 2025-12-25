package me.udnek.rpgu.item.techincal;

import io.papermc.paper.datacomponent.item.CustomModelData;
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
        getComponents().set(Components.TECHNICAL_ITEM.getDefault());
    }
    @Override
    public @Nullable DataSupplier<Key> getItemModel() {
        return DataSupplier.of(null);
    }
    @Override
    public @Nullable DataSupplier<CustomModelData> getCustomModelData() {
        return DataSupplier.of(CustomModelData.custom.ModelData().addColor(Color.WHITE).build());
    }
    @Override
    public @Nullable Boolean getHideTooltip() {return true;}
}
