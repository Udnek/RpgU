/*
package me.udnek.rpgu.item.equipment;

import me.udnek.itemscoreu.customitem.ConstructableCustomItem;
import me.udnek.itemscoreu.util.RightClickable;
import me.udnek.rpgu.RpgU;
import me.udnek.rpgu.item.RpgUCustomItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.components.FoodComponent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Collections;
import java.util.List;

public class Injector extends ConstructableCustomItem implements RightClickable, RpgUCustomItem {
    @Override
    public String getRawId() {return "injector";}
    @Override
    public Integer getMaxStackSize() {return 1;}
    @Override
    public Integer getCustomModelData() {return 3107;}
    @Override
    public FoodComponent getFoodComponent() {
        FoodComponent food = new ItemStack(Material.POTATO).getItemMeta().getFood();
        food.setCanAlwaysEat(true);
        food.setEatSeconds(8);
        food.setNutrition(0);
        food.setSaturation(0);
        return food;
    }

    @Override
    public Material getMaterial() {
        return Material.GUNPOWDER;
    }

    @Override
    public void onRightClicks(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        new BukkitRunnable(){
            @Override
            public void run() {
                if (!player.isHandRaised()) {cancel(); return;}
                if (player.getActiveItemUsedTime() >= 32) {cancel(); inject(event);}
            }
        }.runTaskTimer(RpgU.getInstance(), 1, 1);
    }
    public void inject(PlayerInteractEvent event){
        Player player = event.getPlayer();
        player.getInventory().setItem(event.getHand(), null);
        player.addPotionEffect(new PotionEffect(PotionEffectType.NAUSEA, 20*9, 0, false));
        player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20, 0, false));
    }

    @Override
    protected List<Recipe> generateRecipes() {
        ShapedRecipe recipe = new ShapedRecipe(getRecipeNamespace(0), getItem());
        recipe.shape(
                "  I",
                " G ",
                "A  "
        );
        recipe.setIngredient('A', Material.AMETHYST_SHARD);
        recipe.setIngredient('G', Material.GLASS);
        recipe.setIngredient('I', Material.IRON_INGOT);

        return Collections.singletonList(recipe);
    }
}











*/
