package me.udnek.rpgu.item.equipment.Illagerite;

import me.udnek.coreu.custom.component.instance.AutoGeneratingFilesItem;
import me.udnek.coreu.custom.component.instance.TranslatableThing;
import me.udnek.coreu.custom.item.ConstructableCustomItem;
import org.bukkit.Material;
import org.bukkit.inventory.Recipe;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class IlliterateCrossbow extends ConstructableCustomItem {

    @Override
    public @NotNull Material getMaterial() {return Material.CROSSBOW;}
    @Override
    public @NotNull String getRawId() {return "illagerite_crossbow";}
    @Override
    public @Nullable TranslatableThing getTranslations() {return TranslatableThing.ofEngAndRu("Iillagerite Crossbow", "Злодейский арбалет");}

    @Override
    protected void generateRecipes(@NotNull Consumer<Recipe> consumer) {
        //TODO
    }

    @Override
    public void initializeComponents() {
        super.initializeComponents();
        getComponents().set(AutoGeneratingFilesItem.CROSSBOW);
    }

}
