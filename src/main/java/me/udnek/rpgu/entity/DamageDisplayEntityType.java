package me.udnek.rpgu.entity;

import me.udnek.coreu.custom.entitylike.entity.ConstructableCustomEntityType;
import me.udnek.coreu.custom.item.CustomItem;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.TextDisplay;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DamageDisplayEntityType extends ConstructableCustomEntityType<TextDisplay> {
    @Override
    public @NotNull EntityType getVanillaType() {
        return EntityType.TEXT_DISPLAY;
    }

    @Override
    protected @Nullable CustomItem getSpawnEgg() {
        return null;
    }//TODO delite

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
