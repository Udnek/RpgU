package me.udnek.rpgu.mechanic.enchanting.upgrade;

import me.udnek.coreu.custom.registry.AbstractRegistrable;
import me.udnek.rpgu.mechanic.enchanting.EnchantingTableInventory;
import me.udnek.rpgu.mechanic.enchanting.Utils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class BlockUpgrade extends AbstractRegistrable implements EnchantingTableUpgrade {

    protected @NotNull Material material;
    protected @NotNull String rawId;

    public BlockUpgrade(@NotNull String rawId, @NotNull Material material){
        this.material =  material;
        this.rawId = rawId;
    }

    public @NotNull List<Block> getBlocks(@NotNull Location location){
        return Utils.getBlocksInCuboid(
                location,
                EnchantingTableInventory.UPGRADE_RADIUS_XZ,
                EnchantingTableInventory.UPGRADE_RADIUS_Y,
                EnchantingTableInventory.UPGRADE_RADIUS_XZ,
                block -> block.getType() == material);
    }

    @Override
    public boolean test(@NotNull Location tableLocation) {
        return !getBlocks(tableLocation).isEmpty();
    }

    @Override
    public @NotNull String getRawId() {
        return rawId;
    }

    @Override
    public @NotNull ItemStack getIcon(){
        return new ItemStack(material);
    }
}
