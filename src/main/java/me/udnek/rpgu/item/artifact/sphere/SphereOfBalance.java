package me.udnek.rpgu.item.artifact.sphere;

import me.udnek.itemscoreu.customattribute.CustomAttributeModifier;
import me.udnek.itemscoreu.customattribute.CustomAttributesContainer;
import me.udnek.itemscoreu.customattribute.CustomKeyedAttributeModifier;
import me.udnek.itemscoreu.customattribute.VanillaAttributesContainer;
import me.udnek.itemscoreu.customcomponent.instance.CustomItemAttributesComponent;
import me.udnek.itemscoreu.customcomponent.instance.VanillaAttributesComponent;
import me.udnek.itemscoreu.customitem.ConstructableCustomItem;
import me.udnek.rpgu.RpgU;
import me.udnek.rpgu.attribute.Attributes;
import me.udnek.rpgu.equipment.slot.EquipmentSlots;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class SphereOfBalance extends ConstructableCustomItem {

    @Override
    public @NotNull Material getMaterial() {return Material.GUNPOWDER;}

    @Override
    public @NotNull String getRawId() {return "sphere_of_balance";}

    @Override
    protected void generateRecipes(@NotNull Consumer<@NotNull Recipe> consumer) {
        ShapedRecipe recipe = new ShapedRecipe(getNewRecipeKey(), this.getItem());
        recipe.shape(
                "SFF",
                "S F",
                "SSF");

        RecipeChoice.MaterialChoice snowball = new RecipeChoice.MaterialChoice(Material.SNOWBALL);
        RecipeChoice.MaterialChoice fireCharge = new RecipeChoice.MaterialChoice(Material.FIRE_CHARGE);
        recipe.setIngredient('S', snowball);
        recipe.setIngredient('F', fireCharge);

        consumer.accept(recipe);
    }

    @Override
    public void initializeComponents() {
        super.initializeComponents();

        CustomKeyedAttributeModifier attributeDamage = new CustomKeyedAttributeModifier(new NamespacedKey(RpgU.getInstance(), "attack_damage_"+getRawId()), -0.3, AttributeModifier.Operation.MULTIPLY_SCALAR_1, EquipmentSlots.ARTIFACTS);
        getComponents().set(new VanillaAttributesComponent(new VanillaAttributesContainer.Builder().add(Attribute.ATTACK_DAMAGE, attributeDamage).build()));

        CustomAttributeModifier attributeMP = new CustomAttributeModifier(3, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlots.ARTIFACTS);
        getComponents().set(new CustomItemAttributesComponent(new CustomAttributesContainer.Builder().add(Attributes.MAGICAL_POTENTIAL, attributeMP).build()));
    }

}
