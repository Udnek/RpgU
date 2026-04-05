package me.udnek.rpgu.entity;

import me.udnek.coreu.custom.entitylike.entity.ConstructableCustomEntityType;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.TextDisplay;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DamageDisplayEntityType extends ConstructableCustomEntityType<TextDisplay> {
    @Override
    public @NotNull EntityType getVanillaType() {
        return EntityType.TEXT_DISPLAY;
    }

    @Override
    protected @Nullable ItemStack getSpawnEgg() {
        return new ItemStack(Material.AIR);
    }

    @Override
    public void load(@NotNull Entity entity) {}

    @Override
    public void unload(@NotNull Entity entity) {
        entity.remove();
    }

    @Override
    public @NotNull String getRawId() {
        return "damage_display";
    }
}
