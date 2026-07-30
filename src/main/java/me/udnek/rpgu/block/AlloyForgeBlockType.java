package me.udnek.rpgu.block;

import me.udnek.coreu.custom.component.instance.HopperInteractingBlock;
import me.udnek.coreu.custom.component.instance.RightClickableBlock;
import me.udnek.coreu.custom.entitylike.block.CustomBlockEntity;
import me.udnek.coreu.custom.entitylike.block.CustomBlockEntityType;
import me.udnek.coreu.custom.entitylike.block.CustomBlockType;
import me.udnek.coreu.custom.entitylike.block.constructabletype.RotatableCustomBlockType;
import me.udnek.coreu.custom.item.CustomItem;
import me.udnek.coreu.util.Either;
import me.udnek.rpgu.item.Items;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.inventory.HopperInventorySearchEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.loot.LootTable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

public class AlloyForgeBlockType extends RotatableCustomBlockType implements CustomBlockEntityType {

    @Override
    public @NotNull String getRawId() {
        return "alloy_forge";
    }

    @Override
    public @NotNull CustomBlockEntity createNewClass() {
        return new AlloyForgeBlockEntity();
    }

    @Override
    public @Nullable CustomItem getItem() {
        return Items.ALLOY_FORGE;
    }

    @Override
    public @Nullable Either<LootTable, List<ItemStack>> getLoot() {
        return Either.ofRight(List.of(Items.ALLOY_FORGE.getItem()));
    }

    @Override
    public @NotNull Material getBreakSpeedBaseBlock() {
        return Material.BLAST_FURNACE;
    }

    @Override
    public void initializeComponents() {
        super.initializeComponents();
        getComponents().set(new RightClickableBlock() {
            @Override
            public void onRightClick(@NotNull CustomBlockType customBlockType, @NotNull PlayerInteractEvent event) {
                if (event.getPlayer().isSneaking()) return;
                assert event.getClickedBlock() != null;
                ((AlloyForgeBlockEntity) Objects.requireNonNull(CustomBlockType.getTicking(event.getClickedBlock()))).machine.openInventory(event.getPlayer());
            }
        });
        getComponents().set(new HopperInteractingBlock() {
            @Override
            public void onHopperSearch(@NotNull CustomBlockType customBlockType, @NotNull HopperInventorySearchEvent event) {
                ((AlloyForgeBlockEntity) Objects.requireNonNull(CustomBlockType.getTicking(event.getSearchBlock()))).machine.onHopperSearch(event);
            }

            @Override
            public void onItemMoveInto(@NotNull CustomBlockType customBlockType, @NotNull InventoryMoveItemEvent event) {}

            @Override
            public void onItemMoveFrom(@NotNull CustomBlockType customBlockType, @NotNull InventoryMoveItemEvent event) {}
        });
    }

    @Override
    public void onGenericDestroy(@NotNull Block block) {
        super.onGenericDestroy(block);
        ((AlloyForgeBlockEntity) Objects.requireNonNull(CustomBlockType.getTicking(block))).machine.destroy();
    }
}
