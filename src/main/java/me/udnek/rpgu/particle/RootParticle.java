package me.udnek.rpgu.particle;

import me.udnek.itemscoreu.customparticle.ConstructableCustomParticle;
import me.udnek.rpgu.effect.Effects;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.BlockDisplay;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffect;
import org.bukkit.util.Transformation;
import org.checkerframework.checker.index.qual.Positive;
import org.jetbrains.annotations.NotNull;

public class RootParticle extends ConstructableCustomParticle<BlockDisplay> {

    public static final int UP_DURATION = 3;
    public static final int DOWN_DURATION = 10;

    protected int duration;
    protected LivingEntity target;
    protected float size;

    public RootParticle(@NotNull LivingEntity target, float size){
        PotionEffect effect = Effects.ROOT_EFFECT.get(target);
        this.duration = effect == null ? 0 : effect.getDuration();
        this.target = target;
        this.size = size;
    }

    @Override
    public @Positive int getFramesAmount() {
        return duration;
    }

    @Override
    public @NotNull EntityType getType() {
        return EntityType.BLOCK_DISPLAY;
    }

    public void play() {
        super.play(target.getLocation());
    }

    @Override
    protected void nextFrame() {
        if (!Effects.ROOT_EFFECT.has(target)){
            stop();
            return;
        }
        else if (frameNumber == 1) {
            display.setInterpolationDelay(-1);
            display.setInterpolationDuration(UP_DURATION);
            Transformation transformation = display.getTransformation();
            transformation.getTranslation().add(0, size, 0);
            display.setTransformation(transformation);
        } else if (frameNumber == getFramesAmount() - DOWN_DURATION - 1) {
            display.setInterpolationDelay(-1);
            display.setInterpolationDuration(DOWN_DURATION);
            Transformation transformation = display.getTransformation();
            transformation.getTranslation().add(0, -size, 0);
            display.setTransformation(transformation);
        }
        Location tp = target.getLocation();
        tp.setYaw(0);
        tp.setPitch(0);
        display.teleport(tp);
    }
    @Override
    protected void spawn() {
        super.spawn();
        display.setRotation(0, 0);
        display.setBlock(Material.HANGING_ROOTS.createBlockData());
        Transformation transformation = display.getTransformation();
        transformation.getScale().set(size, -size, size);
        transformation.getTranslation().add(-size/2, 0, -size/2);
        display.setTransformation(transformation);
    }

}
