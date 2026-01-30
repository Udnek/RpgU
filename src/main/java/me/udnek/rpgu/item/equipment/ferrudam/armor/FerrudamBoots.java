package me.udnek.rpgu.item.equipment.ferrudam.armor;

import me.udnek.coreu.custom.attribute.AttributeUtils;
import me.udnek.coreu.custom.recipe.choice.CustomCompatibleRecipeChoice;
import me.udnek.coreu.custom.recipe.choice.CustomSingleRecipeChoice;
import me.udnek.rpgu.RpgU;
import me.udnek.rpgu.item.Items;
import me.udnek.rpgu.mechanic.machine.alloying.AlloyingRecipe;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Tag;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.*;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

public class FerrudamBoots extends FerrudamArmor {

    public FerrudamBoots() {
        super(Material.DIAMOND_BOOTS, "ferrudam_boots", "Ferrudam Boots", "Ферродамовые ботинки");
    }

    @Override
    protected void generateRecipes(@NotNull Consumer<Recipe> consumer) {
        ShapedRecipe recipe = new ShapedRecipe(getNewRecipeKey(), getItem());
        recipe.shape(
                "F F",
                "F F");

        RecipeChoice.ExactChoice ferrudam = new RecipeChoice.ExactChoice(Items.FERRUDAM_INGOT.getItem());
        recipe.setIngredient('F', ferrudam);

        consumer.accept(recipe);

        var ingot = new CustomSingleRecipeChoice(Items.FERRUDAM_INGOT);

        AlloyingRecipe recipeAlloy = new AlloyingRecipe(
                getNewRecipeKey(),
                List.of(ingot, ingot),
                new CustomCompatibleRecipeChoice(Set.of(), Tag.ITEMS_COALS.getValues()),
                List.of(),
                new CustomSingleRecipeChoice(Material.IRON_BOOTS),
                getItem()
        );

        consumer.accept(recipeAlloy);
    }

    @Override
    protected void modifyFinalItemStack(@NotNull ItemStack itemStack) {
        super.modifyFinalItemStack(itemStack);
        AttributeUtils.appendAttribute(itemStack, Attribute.MAX_HEALTH, new NamespacedKey(RpgU.getInstance(), "base_max_health_boots"), 2, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.FEET);
        AttributeUtils.removeAttribute(itemStack, Attribute.ARMOR);
        AttributeUtils.addAttribute(itemStack, Attribute.ARMOR, new NamespacedKey(RpgU.getInstance(), "base_armor_boots"), 1, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.FEET);
        AttributeUtils.removeAttribute(itemStack, Attribute.ARMOR_TOUGHNESS);
    }
}
