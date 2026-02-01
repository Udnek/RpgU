package me.udnek.rpgu.item.equipment.cobalt_armor;

import org.bukkit.Material;
import org.bukkit.inventory.Recipe;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class CobaltBoots extends CobaltArmor {

    public CobaltBoots() {
        super(Material.DIAMOND_BOOTS, "cobalt_boots", "Cobalt Boots", "Кобальтовые ботинки");
    }

    @Override
    protected void generateRecipes(@NotNull Consumer<Recipe> consumer) {

    }
}
