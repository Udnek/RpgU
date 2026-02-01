package me.udnek.rpgu.item.equipment.Illagerite.armor;

import org.bukkit.Material;
import org.bukkit.inventory.Recipe;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class IllageriteHelmet extends IllageriteArmor {

    public IllageriteHelmet() {
        super(Material.DIAMOND_HELMET, "illagerite_helmet", "Illagerite Helmet", "Злодеянитовый шлем");
    }

    @Override
    protected void generateRecipes(@NotNull Consumer<Recipe> consumer) {

    }
}
