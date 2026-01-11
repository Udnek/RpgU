package me.udnek.rpgu.item.equipment.Illagerite;

import me.udnek.coreu.custom.component.instance.TranslatableThing;
import me.udnek.rpgu.item.equipment.flint.FlintTool;
import org.bukkit.Material;
import org.bukkit.inventory.Recipe;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class IllageriteAxe extends FlintTool {
    @Override
    public @NotNull String getRawId() {return "illagerite_axe";}
    @Override
    public @NotNull Material getMaterial() {return Material.DIAMOND_AXE;}
    @Override
    public @Nullable TranslatableThing getTranslations() {return TranslatableThing.ofEngAndRu("Illagerite Axe", "Злодеянитовый топор");}

    @Override
    protected void generateRecipes(@NotNull Consumer<Recipe> consumer) {
        //TODO
    }
}
