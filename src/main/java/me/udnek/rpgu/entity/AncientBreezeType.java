package me.udnek.rpgu.entity;

import me.udnek.itemscoreu.customentitylike.entity.ConstructableCustomEntityType;
import me.udnek.itemscoreu.customentitylike.entity.CustomTickingEntityType;
import org.bukkit.entity.Breeze;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.jetbrains.annotations.NotNull;

public class AncientBreezeType extends ConstructableCustomEntityType<Breeze> implements CustomTickingEntityType<AncientBreeze> {
    @Override
    public @NotNull EntityType getVanillaType() {
        return EntityType.BREEZE;
    }

    @Override
    public void load(@NotNull Entity entity) {

    }

    @Override
    public void unload(@NotNull Entity entity) {

    }

    @Override
    public @NotNull String getRawId() {
        return "ancient_breeze";
    }

    @Override
    public @NotNull AncientBreeze createNewClass() {
        return new AncientBreeze();
    }
}
