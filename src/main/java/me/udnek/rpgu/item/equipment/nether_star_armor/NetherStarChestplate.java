package me.udnek.rpgu.item.equipment.nether_star_armor;

import org.bukkit.Material;
import org.bukkit.inventory.Recipe;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class NetherStarChestplate extends NetherStarArmor {

    public NetherStarChestplate() {
        super(Material.DIAMOND_CHESTPLATE, "nether_star_chestplate", "Nether Star Chestplate", "Незер звёздный нагрудник");
    }

    @Override
    protected void generateRecipes(@NotNull Consumer<Recipe> consumer) {

    }
}
