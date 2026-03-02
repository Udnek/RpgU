package me.udnek.rpgu.item.equipment.nether_star_armor;

import org.bukkit.Material;
import org.bukkit.inventory.Recipe;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class NetherStarLeggings extends NetherStarArmor {

    public NetherStarLeggings() {
        super(Material.DIAMOND_LEGGINGS, "nether_star_leggings", "Nether Star Leggings", "Незер звёздные поножи");
    }
    
    @Override
    protected void generateRecipes(@NotNull Consumer<Recipe> consumer) {

    }
}
