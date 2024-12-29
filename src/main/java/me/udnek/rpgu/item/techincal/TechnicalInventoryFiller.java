package me.udnek.rpgu.item.techincal;

import io.papermc.paper.datacomponent.item.DyedItemColor;
import me.udnek.itemscoreu.customitem.ConstructableCustomItem;
import me.udnek.jeiu.component.ComponentTypes;
import me.udnek.rpgu.RpgU;
import net.kyori.adventure.key.Key;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TechnicalInventoryFiller extends ConstructableCustomItem{
    @Override
    public void initializeComponents() {
        super.initializeComponents();
        getComponents().set(ComponentTypes.TECHNICAL_ITEM.getDefault());
    }

    @Override
    public @Nullable DataSupplier<Key> getItemModel() {
        return DataSupplier.of(new NamespacedKey(RpgU.getInstance(), "empty"));
    }

    @Override
    public @Nullable DataSupplier<DyedItemColor> getDyedColor() {
        return DataSupplier.of(DyedItemColor.dyedItemColor(Color.WHITE, false));
    }

    @Override
    public @NotNull String getRawId() {
        return "technical_inventory_filler";
    }
    @Override
    public @Nullable Boolean getHideTooltip() {return true;}
    @Override
    public @NotNull Material getMaterial() {
        return Material.LEATHER_HELMET;
    }
}
