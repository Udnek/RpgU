package me.udnek.rpgu.item.equipment.flint;

import io.papermc.paper.datacomponent.DataComponentTypes;
import me.udnek.itemscoreu.customitem.ConstructableCustomItem;
import org.jetbrains.annotations.Nullable;

public abstract class FlintTool extends ConstructableCustomItem {

    @Override
    public @Nullable Integer getMaxDamage() {
        return (int) (getMaterial().getDefaultData(DataComponentTypes.MAX_DAMAGE) * 1.3);
    }

}
