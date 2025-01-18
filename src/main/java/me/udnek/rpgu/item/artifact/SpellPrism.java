package me.udnek.rpgu.item.artifact;

import me.udnek.itemscoreu.customattribute.CustomAttributeModifier;
import me.udnek.itemscoreu.customattribute.CustomAttributesContainer;
import me.udnek.itemscoreu.customcomponent.instance.CustomItemAttributesComponent;
import me.udnek.itemscoreu.customitem.ConstructableCustomItem;
import me.udnek.rpgu.attribute.Attributes;
import me.udnek.rpgu.equipment.slot.EquipmentSlots;
import org.bukkit.Material;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class SpellPrism extends ConstructableCustomItem {
    @Override
    public @NotNull String getRawId() {
        return "spell_prism";
    }

    @Override
    protected void generateRecipes(@NotNull Consumer<@NotNull Recipe> consumer) {
        ShapedRecipe recipe = new ShapedRecipe(getNewRecipeKey(), getItem());
        recipe.shape(
                " PS",
                "CCP",
                "PC ");

        recipe.setIngredient('P', new RecipeChoice.MaterialChoice(Material.PRISMARINE_SHARD));
        recipe.setIngredient('S', new RecipeChoice.MaterialChoice(Material.STRING));
        recipe.setIngredient('C', new RecipeChoice.MaterialChoice(Material.PRISMARINE_CRYSTALS));
        consumer.accept(recipe);
    }

    @Override
    public void initializeComponents() {
        super.initializeComponents();

        CustomAttributeModifier attribute = new CustomAttributeModifier(-0.15, AttributeModifier.Operation.MULTIPLY_SCALAR_1, EquipmentSlots.ARTIFACTS);
        getComponents().set(new CustomItemAttributesComponent(new CustomAttributesContainer.Builder().add(Attributes.COOLDOWN_TIME, attribute).build()));
    }
}
