package me.udnek.rpgu.item;

import me.udnek.itemscoreu.customitem.ConstructableCustomItem;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.Recipe;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class RawMagnetite extends ConstructableCustomItem implements RpgUCustomItem{
    @Override
    public @Nullable Integer getCustomModelData() {return 3109;}
    @Override
    public @NotNull String getRawId() {return "raw_magnetite";}
    @Override
    public @NotNull Material getMaterial() {return Material.GUNPOWDER;}
    @Override
    public void afterInitialization() {
        super.afterInitialization();
    }
}
