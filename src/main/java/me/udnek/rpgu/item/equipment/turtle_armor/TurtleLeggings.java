package me.udnek.rpgu.item.equipment.turtle_armor;

import org.bukkit.Material;
import org.bukkit.inventory.Recipe;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class TurtleLeggings extends TurtleArmor {

    public TurtleLeggings() {
        super(Material.DIAMOND_LEGGINGS, "turtle_leggings", "Turtle Leggings", "Черепашьи поножи");
    }
    
    @Override
    protected void generateRecipes(@NotNull Consumer<Recipe> consumer) {

    }
}
