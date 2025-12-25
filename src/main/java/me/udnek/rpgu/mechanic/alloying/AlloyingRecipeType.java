package me.udnek.rpgu.mechanic.alloying;

import me.udnek.coreu.custom.recipe.CustomRecipeType;

public class AlloyingRecipeType extends CustomRecipeType<AlloyingRecipe> {
    // TODO: 8/24/2024 MOVE TO REGISTRY
    private static AlloyingRecipeType instance;
    private AlloyingRecipeType(){}
    public static AlloyingRecipeType getInstance() {
        if (instance == null) instance = new AlloyingRecipeType();
        return instance;
    }
}
