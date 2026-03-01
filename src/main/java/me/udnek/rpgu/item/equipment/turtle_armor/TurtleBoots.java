package me.udnek.rpgu.item.equipment.turtle_armor;

import org.bukkit.Material;
import org.bukkit.inventory.Recipe;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class TurtleBoots extends TurtleArmor {

    public TurtleBoots() {
        super(Material.DIAMOND_BOOTS, "turtle_boots", "Turtle Boots", "Черепаший ботинки");
    }

    @Override
    protected void generateRecipes(@NotNull Consumer<Recipe> consumer) {

    }
}
