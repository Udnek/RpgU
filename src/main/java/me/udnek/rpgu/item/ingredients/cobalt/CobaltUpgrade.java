package me.udnek.rpgu.item.ingredients.cobalt;

import me.udnek.coreu.custom.component.instance.TranslatableThing;
import me.udnek.coreu.custom.item.ConstructableCustomItem;
import org.bukkit.inventory.Recipe;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class CobaltUpgrade extends ConstructableCustomItem {
    @Override
    public @NotNull String getRawId() {
        return "cobalt_upgrade";
    }
    @Override
    public @Nullable TranslatableThing getTranslations() {
        return TranslatableThing.ofEngAndRu("Cobalt Upgrade", "Кобольтовое улучшение");
    }

    @Override
    protected void generateRecipes(@NotNull Consumer<Recipe> consumer) {
        //TODO
    }
}
