package me.udnek.rpgu.item.ingredients;

import me.udnek.itemscoreu.customitem.ConstructableCustomItem;
import me.udnek.rpgu.item.RpgUCustomItem;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class RawMagnetite extends ConstructableCustomItem implements RpgUCustomItem {
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
