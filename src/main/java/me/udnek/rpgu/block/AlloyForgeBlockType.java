package me.udnek.rpgu.block;

import me.udnek.coreu.custom.component.instance.HopperInteractingBlock;
import me.udnek.coreu.custom.component.instance.RightClickableBlock;
import me.udnek.coreu.custom.entitylike.block.CustomBlockEntity;
import me.udnek.coreu.custom.entitylike.block.CustomBlockEntityType;
import me.udnek.coreu.custom.entitylike.block.CustomBlockType;
import me.udnek.coreu.custom.entitylike.block.constructabletype.DisplayBasedConstructableBlockType;
import me.udnek.coreu.custom.item.CustomItem;
import me.udnek.rpgu.mechanic.alloying.AlloyForgeInventory;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.inventory.HopperInventorySearchEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class AlloyForgeBlockType extends DisplayBasedConstructableBlockType implements CustomBlockEntityType {
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
                CustomBlockEntity ticking = CustomBlockType.getTicking(event.getClickedBlock());
                if (ticking instanceof AlloyForgeBlockEntity alloyForgeBlock){
                    alloyForgeBlock.machine.openInventory(event.getPlayer());
                }

            }
        });
        getComponents().set(new HopperInteractingBlock() {
            @Override
            public void onHopperSearch(@NotNull CustomBlockType customBlockType, @NotNull HopperInventorySearchEvent event) {
                CustomBlockEntity ticking = CustomBlockType.getTicking(event.getBlock());
                if (ticking instanceof AlloyForgeBlockEntity alloyForgeBlock){
                    alloyForgeBlock.machine.onHopperSearch(event);
                }
            }

            @Override
            public void onItemMoveInto(@NotNull CustomBlockType customBlockType, @NotNull InventoryMoveItemEvent event) {
                if (event.getDestination() instanceof AlloyForgeInventory alloyForgeInventory){
                    alloyForgeInventory.onHopperGivesItem(event);
                }
            }

            @Override
            public void onItemMoveFrom(@NotNull CustomBlockType customBlockType, @NotNull InventoryMoveItemEvent event) {
                if (event.getDestination() instanceof AlloyForgeInventory alloyForgeInventory){
                    alloyForgeInventory.onHopperTakesItem(event);
                }
            }
        });
    }

    @Override
    public void onGenericDestroy(@NotNull Block block) {
        super.onGenericDestroy(block);
        if (CustomBlockType.getTicking(block) instanceof AlloyForgeBlockEntity alloyForgeBlock){
            alloyForgeBlock.machine.destroy();
        }
    }

    @Override
    public @Nullable CustomItem getItem() {
        return null;
    }

    @Override
    public @NotNull String getRawId() {
        return "alloy_forge";
    }

    @Override
    public @NotNull CustomBlockEntity createNewClass() {
        return new AlloyForgeBlockEntity();
    }
}
