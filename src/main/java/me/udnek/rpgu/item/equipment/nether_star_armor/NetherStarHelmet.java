package me.udnek.rpgu.item.equipment.nether_star_armor;

import org.bukkit.Material;
import org.bukkit.inventory.Recipe;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class NetherStarHelmet extends NetherStarArmor {

    public NetherStarHelmet() {
        super(Material.DIAMOND_HELMET, "nether_star_helmet", "Nether Star Helmet", "Незер звёздный шлем");
    }

    @Override
    protected void generateRecipes(@NotNull Consumer<Recipe> consumer) {

    }
}
