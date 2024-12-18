package me.udnek.rpgu.mechanic.enchanting.upgrade;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class BookShelfUpgrade extends BlockUpgrade{

    protected int amount;
    public BookShelfUpgrade(@NotNull String rawId, int amount) {
        super(rawId, Material.BOOKSHELF);
        this.amount = amount;
    }


    @Override
    public boolean test(@NotNull Location tableLocation) {
        return getBlocks(tableLocation).size() >= amount;
    }

    @Override
    public @NotNull ItemStack getIcon() {
        return super.getIcon().asQuantity(amount);
    }
}
