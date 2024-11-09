package me.udnek.rpgu.particle;

import me.udnek.itemscoreu.customparticle.CustomFlatParticle;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Transformation;
import org.jetbrains.annotations.NotNull;

public class StunnedParticle extends CustomFlatParticle {
    public LivingEntity targetEntity;

    public StunnedParticle(@NotNull Location location, @NotNull LivingEntity entity) {
        super(location);
        targetEntity = entity;
    }

    @Override
    public double getXScale() {
        return 0.6;
    }

    @Override
    public double getYScale() {
        return 0.6;
    }

    @Override
    protected ItemStack getItemStack() {
        ItemStack itemStack = new ItemStack(Material.FEATHER);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setCustomModelData(4000);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    @Override
    public int getDuration() {
        return 15;
    }

    @Override
    protected void nextFrame() {}

    @Override
    protected void afterSpawned() {
        super.afterSpawned();
        targetEntity.addPassenger(display);
        Transformation transformation = display.getTransformation();
        transformation.getTranslation().set(0, targetEntity.getEyeHeight()/2f + 0.2, 0);
        display.setTransformation(transformation);
    }
}
