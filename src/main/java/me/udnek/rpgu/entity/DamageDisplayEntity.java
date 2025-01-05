package me.udnek.rpgu.entity;

import me.udnek.itemscoreu.customentity.ConstructableCustomEntity;
import me.udnek.itemscoreu.customentity.CustomEntityType;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.TextDisplay;
import org.jetbrains.annotations.NotNull;

public class DamageDisplayEntity extends ConstructableCustomEntity<TextDisplay> {
    @Override
    public @NotNull EntityType getVanillaEntityType() {
        return EntityType.TEXT_DISPLAY;
    }

    public @NotNull TextDisplay getEntity(){
        return entity;
    }

    @Override
    public void afterSpawn() {}

    @Override
    public void unload() {
        remove();
    }

    @Override
    public void tick() {}

    @Override
    public @NotNull CustomEntityType<?> getType() {
        return EntityTypes.DAMAGE_DISPLAY;
    }
}
