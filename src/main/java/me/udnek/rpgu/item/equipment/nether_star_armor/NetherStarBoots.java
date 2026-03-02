package me.udnek.rpgu.item.equipment.nether_star_armor;

import org.bukkit.Material;
import org.bukkit.inventory.Recipe;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class NetherStarBoots extends NetherStarArmor {

    public NetherStarBoots() {
        super(Material.DIAMOND_BOOTS, "nether_star_boots", "Nether Star Boots", "Незер звёздные ботинки");
    }

    @Override
    protected void generateRecipes(@NotNull Consumer<Recipe> consumer) {

    }
}
