package me.udnek.rpgu.particle;

import me.udnek.itemscoreu.customparticle.ConstructableCustomParticle;
import org.bukkit.Material;
import org.bukkit.entity.BlockDisplay;
import org.bukkit.entity.EntityType;
import org.bukkit.util.Transformation;
import org.checkerframework.checker.index.qual.Positive;
import org.jetbrains.annotations.NotNull;

public class AmethystSpikeParticle extends ConstructableCustomParticle<BlockDisplay> {

    public static final int UP_DURATION = 3;
    public static final int DOWN_DURATION = 10;

    protected float size;

    public AmethystSpikeParticle(float size){this.size = size;}

    @Override
    public @Positive int getFramesAmount() {
        return 20;
    }

    @Override
    public @NotNull EntityType getType() {
        return EntityType.BLOCK_DISPLAY;
    }

    @Override
    protected void nextFrame() {
        if (frameNumber == 1) {
            display.setInterpolationDelay(-1);
            display.setInterpolationDuration(UP_DURATION);
            Transformation transformation = display.getTransformation();
            transformation.getTranslation().add(0, size*(4f/3f)/2, 0);
            display.setTransformation(transformation);
        } else if (frameNumber == getFramesAmount() - DOWN_DURATION - 1) {
            display.setInterpolationDelay(-1);
            display.setInterpolationDuration(DOWN_DURATION);
            Transformation transformation = display.getTransformation();
            transformation.getTranslation().add(0, -size, 0);
            display.setTransformation(transformation);
        }
    }
    @Override
    protected void spawn() {
        super.spawn();
        display.setRotation(0, 0);
        display.setBlock(Material.AMETHYST_CLUSTER.createBlockData());
        Transformation transformation = display.getTransformation();
        transformation.getScale().set(size);
        transformation.getTranslation().add(-size/2, -size, -size/2);
        display.setTransformation(transformation);
    }
}
