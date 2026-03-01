package me.udnek.rpgu.item.equipment.nether_star_armor;

import me.udnek.rpgu.item.AbstractArmor;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;

public abstract class NetherStarArmor extends AbstractArmor {

    public NetherStarArmor(@NotNull Material material, @NotNull String id, @NotNull String engName, @NotNull String ruName) {
        super(material, id, "nether_star", engName, ruName);
    }

    /*@Override TODO
    public @Nullable RepairData initializeRepairData() {
        return new RepairData(Items.COBALT_INGOT);
    }*/
}
