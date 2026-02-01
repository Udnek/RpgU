package me.udnek.rpgu.item.equipment.cobalt_armor;

import me.udnek.coreu.custom.item.RepairData;
import me.udnek.rpgu.item.AbstractArmor;
import me.udnek.rpgu.item.Items;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class CobaltArmor extends AbstractArmor {

    public CobaltArmor(@NotNull Material material, @NotNull String id, @NotNull String engName, @NotNull String ruName) {
        super(material, id, "cobalt", engName, ruName);
    }

    @Override
    public @Nullable RepairData initializeRepairData() {
        return new RepairData(Items.COBALT_INGOT);
    }
}
