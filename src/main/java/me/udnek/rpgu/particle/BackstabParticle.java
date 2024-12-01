package me.udnek.rpgu.particle;

import io.papermc.paper.datacomponent.DataComponentTypes;
import io.papermc.paper.datacomponent.item.DyedItemColor;
import me.udnek.itemscoreu.customparticle.CustomFlatParticle;
import me.udnek.rpgu.RpgU;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.checkerframework.checker.index.qual.Positive;
import org.jetbrains.annotations.NotNull;

public class BackstabParticle extends CustomFlatParticle {
    private final static Color color = Color.fromRGB(255, 10, 10);

    @Override
    public @Positive int getFramesAmount() {return 8;}
    @Override
    public int getFrameTime() {return 1;}
    @Override
    public double getScale() {return 2;}

    @Override
    protected @NotNull ItemStack createDisplayItem() {
        ItemStack itemStack = new ItemStack(Material.LEATHER_HELMET);
        itemStack.setData(DataComponentTypes.DYED_COLOR, DyedItemColor.dyedItemColor(color, true));
        return itemStack;
    }

    @Override
    protected @NotNull NamespacedKey getCurrentModelPath() {
        return new NamespacedKey(RpgU.getInstance(), "particle/sweep/"+frameNumber);
    }
}
