package me.udnek.rpgu.particle;

import io.papermc.paper.datacomponent.DataComponentTypes;
import me.udnek.itemscoreu.customparticle.CustomFlatParticle;
import me.udnek.rpgu.RpgU;
import me.udnek.rpgu.effect.Effects;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Display;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Transformation;
import org.checkerframework.checker.index.qual.Positive;
import org.jetbrains.annotations.NotNull;

public class StunnedParticle extends CustomFlatParticle {
    protected LivingEntity targetEntity;
    protected int duration;

    public StunnedParticle(@NotNull LivingEntity target, @Positive int duration) {
        targetEntity = target;
        this.duration = duration;
    }

    public StunnedParticle(@NotNull LivingEntity target){
        this(target, 15);
    }

    @Override
    public @Positive int getFramesAmount() {return duration;}
    @Override
    public double getScale() {return 0.6;}
    @Override
    protected @NotNull ItemStack createDisplayItem() {
        ItemStack itemStack = new ItemStack(Material.GUNPOWDER);
        itemStack.setData(DataComponentTypes.ITEM_MODEL, new NamespacedKey(RpgU.getInstance(), "particle/stunned"));
        return itemStack;
    }
    @Override
    protected @NotNull NamespacedKey getCurrentModelPath() {
        return new NamespacedKey(RpgU.getInstance(), "particle/stunned");
    }

    @Override
    public void setStartTransformation() {
        Transformation transformation = display.getTransformation();
        transformation.getTranslation().set(0, targetEntity.getEyeHeight()/2f + 0.2, 0);
        display.setTransformation(transformation);
        display.setBrightness(new Display.Brightness(15, 15));
    }

    @Override
    protected void nextFrame() {
        if (!Effects.STUN_EFFECT.has(targetEntity)){
            stop();
        }
    }
    @Override
    protected void spawn() {
        super.spawn();
        targetEntity.addPassenger(display);
    }
}
