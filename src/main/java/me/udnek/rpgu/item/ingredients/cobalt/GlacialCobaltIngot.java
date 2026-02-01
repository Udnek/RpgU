package me.udnek.rpgu.item.ingredients.cobalt;

import me.udnek.coreu.custom.component.instance.TranslatableThing;
import me.udnek.coreu.custom.item.ConstructableCustomItem;
import org.bukkit.inventory.Recipe;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class GlacialCobaltIngot extends ConstructableCustomItem {
    @Override
    public @NotNull String getRawId() {
        return "glacial_cobalt_ingot";
    }
    @Override
    public @Nullable TranslatableThing getTranslations() {
        return TranslatableThing.ofEngAndRu("Glacial Cobalt Ingot", "Ледяной кобальтовый слиток");
    }

    @Override
    protected void generateRecipes(@NotNull Consumer<Recipe> consumer) {
        //TODO
    }
}
