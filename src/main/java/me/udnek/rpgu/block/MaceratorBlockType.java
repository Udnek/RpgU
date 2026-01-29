package me.udnek.rpgu.block;

import me.udnek.coreu.custom.entitylike.block.CustomBlockEntity;
import me.udnek.coreu.custom.entitylike.block.CustomBlockEntityType;
import me.udnek.coreu.custom.entitylike.block.constructabletype.DisplayBasedConstructableBlockType;
import me.udnek.coreu.custom.item.CustomItem;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NonNull;

public class MaceratorBlockType extends DisplayBasedConstructableBlockType implements CustomBlockEntityType {
    @Override
    public @NonNull CustomBlockEntity createNewClass() {
        return new MaceratorBlockEntity();
    }

    @Override
    public @NotNull Material getBreakSpeedBaseBlock() {
        return null;
    }

    @Override
    public @Nullable CustomItem getItem() {
        return null;
    }

    @NotNull
    @Override
    public String getRawId() {
        return "";
    }
}
