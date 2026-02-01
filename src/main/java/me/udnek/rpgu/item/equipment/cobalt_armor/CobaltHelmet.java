package me.udnek.rpgu.item.equipment.cobalt_armor;

import org.bukkit.Material;
import org.bukkit.inventory.Recipe;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class CobaltHelmet extends CobaltArmor {

    public CobaltHelmet() {
        super(Material.DIAMOND_HELMET, "cobalt_helmet", "Cobalt Helmet", "Кобальтовый шлем");
    }

    @Override
    protected void generateRecipes(@NotNull Consumer<Recipe> consumer) {

    }
}
