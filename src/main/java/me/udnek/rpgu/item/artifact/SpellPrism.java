package me.udnek.rpgu.item.artifact;

import me.udnek.coreu.custom.attribute.CustomAttributeModifier;
import me.udnek.coreu.custom.attribute.CustomAttributesContainer;
import me.udnek.coreu.custom.component.instance.CustomAttributedItem;
import me.udnek.coreu.custom.component.instance.TranslatableThing;
import me.udnek.coreu.custom.item.ConstructableCustomItem;
import me.udnek.coreu.rpgu.attribute.RPGUAttributes;
import me.udnek.rpgu.attribute.Attributes;
import me.udnek.rpgu.equipment.slot.EquipmentSlots;
import org.bukkit.Material;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class SpellPrism extends ConstructableCustomItem {
    @Override
    public @NotNull String getRawId() {return "spell_prism";}
    @Override
    public @Nullable TranslatableThing getTranslations() {return TranslatableThing.ofEngAndRu("Spell Prism", "Призма заклинателя");}

    @Override
    protected void generateRecipes(@NotNull Consumer<Recipe> consumer) {
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

        getComponents().set(new CustomAttributedItem(new CustomAttributesContainer.Builder()
                .add(RPGUAttributes.ABILITY_COOLDOWN_TIME, new CustomAttributeModifier(-0.15, AttributeModifier.Operation.MULTIPLY_SCALAR_1, EquipmentSlots.ARTIFACTS))
                .add(Attributes.MAGICAL_POTENTIAL, new CustomAttributeModifier(0.2, AttributeModifier.Operation.ADD_SCALAR, EquipmentSlots.ARTIFACTS))
                .build()));
    }
}
