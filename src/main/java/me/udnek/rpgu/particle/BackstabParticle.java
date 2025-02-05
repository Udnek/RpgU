package me.udnek.rpgu.particle;

import io.papermc.paper.datacomponent.DataComponentTypes;
import io.papermc.paper.datacomponent.item.CustomModelData;
import me.udnek.itemscoreu.customparticle.CustomFlatParticle;
import me.udnek.rpgu.RpgU;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.checkerframework.checker.index.qual.Positive;
import org.jetbrains.annotations.NotNull;

public class BackstabParticle extends CustomFlatParticle {
    public final static Color COLOR = Color.fromRGB(255, 10, 10);
    protected double scale;

    public BackstabParticle(double scale){
        this.scale = scale;
    }

    @Override
    public @Positive int getFramesAmount() {return 8;}
    @Override
    public int getFrameTime() {return 1;}
    @Override
    public double getScale() {return scale;}

    @Override
    protected @NotNull ItemStack createDisplayItem() {
        ItemStack itemStack = new ItemStack(Material.LEATHER_HELMET);
        itemStack.setData(DataComponentTypes.CUSTOM_MODEL_DATA, CustomModelData.customModelData().addColor(COLOR).build());
        return itemStack;
    }

    @Override
    protected @NotNull NamespacedKey getCurrentModelPath() {
        return new NamespacedKey(RpgU.getInstance(), "particle/sweep/"+frameNumber);
    }
}
