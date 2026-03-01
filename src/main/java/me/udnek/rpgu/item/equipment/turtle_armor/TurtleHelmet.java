package me.udnek.rpgu.item.equipment.turtle_armor;

import org.bukkit.Material;
import org.bukkit.inventory.Recipe;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class TurtleHelmet extends TurtleArmor {

    public TurtleHelmet() {
        super(Material.DIAMOND_HELMET, "turtle_helmet", "Turtle Helmet", "Черепаший шлем");
    }

    @Override
    protected void generateRecipes(@NotNull Consumer<Recipe> consumer) {

    }
}
