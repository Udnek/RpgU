package me.udnek.rpgu.item.equipment.Illagerite.armor;

import org.bukkit.Material;
import org.bukkit.inventory.Recipe;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class IllageriteLeggings extends IllageriteArmor {

    public IllageriteLeggings() {
        super(Material.DIAMOND_LEGGINGS, "illagerite_leggings", "Illagerite Leggings", "Злодеянитовыe поножи");
    }
    
    @Override
    protected void generateRecipes(@NotNull Consumer<Recipe> consumer) {

    }
}
