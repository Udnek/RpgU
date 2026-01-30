package me.udnek.rpgu.mechanic.machine;

import me.udnek.coreu.custom.recipe.CustomRecipe;
import me.udnek.coreu.custom.recipe.CustomRecipeType;
import me.udnek.coreu.custom.registry.CustomRegistries;
import me.udnek.jeiu.component.VisualizableRecipeType;
import me.udnek.jeiu.visualizer.Visualizer;
import me.udnek.rpgu.RpgU;
import me.udnek.rpgu.mechanic.enchanting.EnchantingRecipe;
import me.udnek.rpgu.mechanic.enchanting.EnchantingRecipeVisualizer;
import me.udnek.rpgu.mechanic.machine.alloying.AlloyingRecipe;
import me.udnek.rpgu.mechanic.machine.alloying.AlloyingRecipeVisualizer;
import org.jetbrains.annotations.NotNull;

public class RecipeTypes {

    public static final CustomRecipeType<AlloyingRecipe> ALLOYING = register(new CustomRecipeType<>("alloying"));
    public static final CustomRecipeType<EnchantingRecipe> ENCHANTING = register(new CustomRecipeType<>("enchanting"));

    static {
        ALLOYING.getComponents().set(new VisualizableRecipeType() {
            @Override
            public @NotNull Visualizer getVisualizer(@NotNull CustomRecipe customRecipe) {
                return new AlloyingRecipeVisualizer((AlloyingRecipe) customRecipe);
            }
        });
        ENCHANTING.getComponents().set(new VisualizableRecipeType() {
            @Override
            public @NotNull Visualizer getVisualizer(@NotNull CustomRecipe customRecipe) {
                return new EnchantingRecipeVisualizer((EnchantingRecipe) customRecipe);
            }
        });
    }

    private static @NotNull <T extends CustomRecipeType<?>> T register(@NotNull T recipeType){
        return CustomRegistries.RECIPE_TYPE.register(RpgU.getInstance(), recipeType);
    }
}
