package me.udnek.rpgu.entity;

import me.udnek.itemscoreu.customentitylike.entity.ConstructableCustomEntityType;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.TextDisplay;
import org.jetbrains.annotations.NotNull;

public class DamageDisplayEntityType extends ConstructableCustomEntityType<TextDisplay> {
    @Override
    public @NotNull EntityType getVanillaType() {
        return EntityType.TEXT_DISPLAY;
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
