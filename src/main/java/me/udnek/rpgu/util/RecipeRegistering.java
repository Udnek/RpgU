package me.udnek.rpgu.util;

import me.udnek.itemscoreu.customrecipe.RecipeManager;
import me.udnek.itemscoreu.customrecipe.choice.CustomSingleRecipeChoice;
import me.udnek.rpgu.RpgU;
import me.udnek.rpgu.item.Items;
import me.udnek.rpgu.mechanic.alloying.AlloyingRecipe;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Tag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;

import java.util.ArrayList;
import java.util.List;

public class RecipeRegistering {


    public static void register(){

        RecipeManager.getInstance().unregister(NamespacedKey.minecraft("netherite_ingot"));

        List<CustomSingleRecipeChoice> alloys = new ArrayList<>();
        CustomSingleRecipeChoice magnetiteIngot = new CustomSingleRecipeChoice(Items.MAGNETITE_INGOT);
        alloys.add(magnetiteIngot);
        alloys.add(magnetiteIngot);
        alloys.add(magnetiteIngot);
        alloys.add(magnetiteIngot);
        CustomSingleRecipeChoice netheriteScrap = new CustomSingleRecipeChoice(Material.NETHERITE_SCRAP);
        alloys.add(netheriteScrap);
        alloys.add(netheriteScrap);
        alloys.add(netheriteScrap);
        alloys.add(netheriteScrap);

        AlloyingRecipe netheriteRecipe = new AlloyingRecipe(
                new NamespacedKey(RpgU.getInstance(), "test"),
                alloys,
                new CustomSingleRecipeChoice(Material.COAL),
                new CustomSingleRecipeChoice(Material.CLAY_BALL),
                new ItemStack(Material.NETHERITE_INGOT)
        );

        RecipeManager.getInstance().register(netheriteRecipe);

        ///////////////////////////////////////////////////////////////////

        RecipeManager.getInstance().unregister(NamespacedKey.minecraft("blast_furnace"));


        ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(RpgU.getInstance() ,"blast_furnace"), new ItemStack(Material.BLAST_FURNACE));
        recipe.shape("SSS",
                "FFA",
                "BBB");

        RecipeChoice.MaterialChoice bricks = new RecipeChoice.MaterialChoice(Material.BRICKS);
        RecipeChoice.MaterialChoice furnace = new RecipeChoice.MaterialChoice(Material.FURNACE);
        RecipeChoice.MaterialChoice smoothStone = new RecipeChoice.MaterialChoice(Material.SMOOTH_STONE);
        RecipeChoice.MaterialChoice amethystBlock = new RecipeChoice.MaterialChoice(Material.AMETHYST_BLOCK);
        recipe.setIngredient('F', furnace);
        recipe.setIngredient('S', smoothStone);
        recipe.setIngredient('B', bricks);
        recipe.setIngredient('A', amethystBlock);

        RecipeManager.getInstance().register(recipe);

    }
}
