package me.udnek.rpgu.item.equipment.Illagerite.armor;

import org.bukkit.Material;
import org.bukkit.inventory.Recipe;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class IllageriteBoots extends IllageriteArmor {

    public IllageriteBoots() {
        super(Material.DIAMOND_BOOTS, "illagerite_boots", "Illagerite Boots", "Злодеянитовые ботинки");
    }

    @Override
    protected void generateRecipes(@NotNull Consumer<Recipe> consumer) {

    }
}
