package me.udnek.rpgu.item.equipment.cobalt_armor;

import org.bukkit.Material;
import org.bukkit.inventory.Recipe;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class CobaltChestplate extends CobaltArmor {

    public CobaltChestplate() {
        super(Material.DIAMOND_CHESTPLATE, "cobalt_chestplate", "Cobalt Chestplate", "Кобальтовый нагрудник");
    }

    @Override
    protected void generateRecipes(@NotNull Consumer<Recipe> consumer) {

    }
}
