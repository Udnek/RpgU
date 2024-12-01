package me.udnek.rpgu.particle;

import me.udnek.itemscoreu.customparticle.CustomFlatAnimatedParticle;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Display;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Transformation;
import org.jetbrains.annotations.NotNull;

public class AmethystSpikeParticle extends CustomFlatAnimatedParticle {

    public AmethystSpikeParticle(@NotNull Location location) {
        super(location);
    }

    @Override
    public int getDuration() {
        return 20;
    }

    @Override
    public int frameTime() {
        return 1;
    }

    @Override
    public double getXScale() {
        return 1;
    }
    public double getYScale() {
        return 1;
    }

    @Override
    protected ItemStack getItemStack() {
        return new ItemStack(Material.GUNPOWDER);
    }

    @Override
    protected void afterSpawned() {
        super.afterSpawned();

        display.setBrightness(new Display.Brightness(15, 15));
    }

    @Override
    protected void nextFrame() {
        super.nextFrame();

        if (frameNumber == 1) {
            display.setInterpolationDelay(-1);
            display.setInterpolationDuration(20);
            Transformation transformation = display.getTransformation();
            transformation.getTranslation().add(0, 2, 0);
            display.setTransformation(transformation);
        }
    }

    @Override
    protected @NotNull NamespacedKey getCurrentModelPath() {
        return NamespacedKey.minecraft("amethyst_shard");
    }
}
