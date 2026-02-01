package me.udnek.rpgu.item.equipment.Illagerite.armor;

import me.udnek.coreu.custom.item.RepairData;
import me.udnek.rpgu.item.AbstractArmor;
import me.udnek.rpgu.item.Items;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class IllageriteArmor extends AbstractArmor {

    public IllageriteArmor(@NotNull Material material, @NotNull String id, @NotNull String engName, @NotNull String ruName) {
        super(material, id, "illagerite", engName, ruName);
    }

    @Override
    public @Nullable RepairData initializeRepairData() {
        return new RepairData(Items.ILLAGERITE_INGOT);
    }
}
