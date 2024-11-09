package me.udnek.rpgu.particle;

import me.udnek.itemscoreu.customparticle.CustomFlatAnimatedParticle;
import me.udnek.rpgu.RpgU;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.jetbrains.annotations.NotNull;

public class BackstabParticle extends CustomFlatAnimatedParticle {
    private final static Color color = Color.fromRGB(255, 10, 10);

    public BackstabParticle(@NotNull Location location) {
        super(location);
    }

    @Override
    public int getDuration() {
        return 8;
    }

    @Override
    public int frameTime() {
        return 1;
    }

    @Override
    public double getXScale() {
        return -2;
    }
    public double getYScale() {
        return 2;
    }

    @Override
    protected ItemStack getItemStack() {
        ItemStack itemStack = new ItemStack(Material.LEATHER_HELMET);
        itemStack.editMeta(LeatherArmorMeta.class, meta -> meta.setColor(color));
        return itemStack;
    }

    @Override
    protected @NotNull NamespacedKey getCurrentModelPath() {
        return new NamespacedKey(RpgU.getInstance(), "particle/sweep/"+frameNumber);
    }
}
