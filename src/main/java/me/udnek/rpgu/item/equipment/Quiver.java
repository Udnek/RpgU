package me.udnek.rpgu.item.equipment;

import me.udnek.itemscoreu.customitem.ConstructableCustomItem;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;

public class Quiver extends ConstructableCustomItem {
    @Override
    public @NotNull String getRawId() {return "quiver";}
    @Override
    public @NotNull Material getMaterial() {return Material.BUNDLE;}
}
