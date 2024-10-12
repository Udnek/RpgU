package me.udnek.rpgu.util;

import me.udnek.itemscoreu.customrecipe.RecipeManager;
import me.udnek.itemscoreu.customrecipe.choice.CustomSingleRecipeChoice;
import me.udnek.rpgu.RpgU;
import me.udnek.rpgu.mechanic.alloying.AlloyingRecipe;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class RecipeRegistering {


    public static void register(){

        List<CustomSingleRecipeChoice> alloys = new ArrayList<>();
        CustomSingleRecipeChoice goldenIngot = new CustomSingleRecipeChoice(Material.GOLD_INGOT);
        alloys.add(goldenIngot);
        alloys.add(goldenIngot);
        alloys.add(goldenIngot);
        alloys.add(goldenIngot);
        CustomSingleRecipeChoice netheriteScrap = new CustomSingleRecipeChoice(Material.NETHERITE_SCRAP);
        alloys.add(netheriteScrap);
        alloys.add(netheriteScrap);
        alloys.add(netheriteScrap);
        alloys.add(netheriteScrap);

        AlloyingRecipe test = new AlloyingRecipe(
                new NamespacedKey(RpgU.getInstance(), "test"),
                alloys,
                new CustomSingleRecipeChoice(Material.COAL),
                new CustomSingleRecipeChoice(Material.CLAY_BALL),
                new ItemStack(Material.NETHERITE_INGOT)
        );

        RecipeManager.getInstance().register(test);












    }

}
