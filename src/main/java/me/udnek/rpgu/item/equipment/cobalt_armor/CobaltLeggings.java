package me.udnek.rpgu.item.equipment.cobalt_armor;

import org.bukkit.Material;
import org.bukkit.inventory.Recipe;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class CobaltLeggings extends CobaltArmor {

    public CobaltLeggings() {
        super(Material.DIAMOND_LEGGINGS, "cobalt_leggings", "Cobalt Leggings", "Кобальтовые поножи");
    }
    
    @Override
    protected void generateRecipes(@NotNull Consumer<Recipe> consumer) {

    }
}
