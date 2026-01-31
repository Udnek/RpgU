package me.udnek.rpgu.item.ingredients;

import me.udnek.coreu.custom.component.instance.TranslatableThing;
import me.udnek.coreu.custom.item.ConstructableCustomItem;
import org.bukkit.inventory.Recipe;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class IllageriteKey extends ConstructableCustomItem {
    @Override
    public @NotNull String getRawId() {return "illagerite_key";}
    @Override
    public @Nullable TranslatableThing getTranslations() {return TranslatableThing.ofEngAndRu("Illagerite Key", "Злодеянитовый ключь");}

    @Override
    protected void generateRecipes(@NotNull Consumer<Recipe> consumer) {
        //TODO
    }
}
