package me.udnek.rpgu.particle;

import me.udnek.itemscoreu.customparticle.CustomFlatAnimatedParticle;
import me.udnek.itemscoreu.customparticle.CustomFlatParticle;
import org.bukkit.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class BackstabParticle extends CustomFlatAnimatedParticle {
    private final static Color color = Color.fromRGB(255, 10, 10);

    public BackstabParticle(Location location) {
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
        LeatherArmorMeta itemMeta = (LeatherArmorMeta) itemStack.getItemMeta();
        itemMeta.setColor(color);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    @Override
    protected int currentModelData() {
        return 1000+frameNumber;
    }


}
