package me.udnek.rpgu.item.equipment.turtle_armor;

import org.bukkit.Material;
import org.bukkit.inventory.Recipe;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class TurtleChestplate extends TurtleArmor {

    public TurtleChestplate() {
        super(Material.DIAMOND_CHESTPLATE, "turtle_chestplate", "Turtle Chestplate", "Черепаший нагрудник");
    }

    @Override
    protected void generateRecipes(@NotNull Consumer<Recipe> consumer) {

    }
}
