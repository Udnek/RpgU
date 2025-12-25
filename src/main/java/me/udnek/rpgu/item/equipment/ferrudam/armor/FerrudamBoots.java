package me.udnek.rpgu.item.equipment.ferrudam.armor;

import me.udnek.coreu.custom.attribute.AttributeUtils;
import me.udnek.coreu.custom.recipe.choice.CustomCompatibleRecipeChoice;
import me.udnek.coreu.custom.recipe.choice.CustomSingleRecipeChoice;
import me.udnek.rpgu.RpgU;
import me.udnek.rpgu.item.Items;
import me.udnek.rpgu.mechanic.alloying.AlloyingRecipe;
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
    @Override
    public @NotNull String getRawId() {return "ferrudam_boots";}
    @Override
    public @NotNull Material getMaterial() {return Material.DIAMOND_BOOTS;}
    @Override
    protected void generateRecipes(@NotNull Consumer<@NotNull Recipe> consumer) {
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
                new CustomSingleRecipeChoice(Material.IRON_BOOTS),
                getItem()
        );

        consumer.accept(recipeAlloy);
    }

    @Override
    public void initializeAdditionalAttributes(@NotNull ItemStack itemStack) {
        super.initializeAdditionalAttributes(itemStack);
        AttributeUtils.appendAttribute(itemStack, Attribute.MAX_HEALTH, new NamespacedKey(RpgU.getInstance(), "base_max_health_boots"), 2, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.FEET);
        AttributeUtils.removeAttribute(itemStack, Attribute.ARMOR);
        AttributeUtils.addAttribute(itemStack, Attribute.ARMOR, new NamespacedKey(RpgU.getInstance(), "base_armor_boots"), 1, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.FEET);
        AttributeUtils.removeAttribute(itemStack, Attribute.ARMOR_TOUGHNESS);
    }

}
