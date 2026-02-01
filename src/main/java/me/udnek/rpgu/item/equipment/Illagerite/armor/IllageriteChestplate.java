package me.udnek.rpgu.item.equipment.Illagerite.armor;

import org.bukkit.Material;
import org.bukkit.inventory.Recipe;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class IllageriteChestplate extends IllageriteArmor {

    public IllageriteChestplate() {
        super(Material.DIAMOND_CHESTPLATE, "illagerite_chestplate", "Illagerite Chestplate", "Злодеянитовый нагрудник");
    }

    @Override
    protected void generateRecipes(@NotNull Consumer<Recipe> consumer) {

    }
}
