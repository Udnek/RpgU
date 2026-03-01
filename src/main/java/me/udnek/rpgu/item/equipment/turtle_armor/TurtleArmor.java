package me.udnek.rpgu.item.equipment.turtle_armor;

import me.udnek.coreu.custom.item.RepairData;
import me.udnek.rpgu.item.AbstractArmor;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class TurtleArmor extends AbstractArmor {

    public TurtleArmor(@NotNull Material material, @NotNull String id, @NotNull String engName, @NotNull String ruName) {
        super(material, id, "turtle", engName, ruName);
    }

    @Override
    public @Nullable RepairData initializeRepairData() {
        return new RepairData(Material.TURTLE_SCUTE);
    }
}
