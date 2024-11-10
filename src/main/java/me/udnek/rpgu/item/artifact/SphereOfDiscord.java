package me.udnek.rpgu.item.artifact;

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
import me.udnek.rpgu.item.Items;
import me.udnek.rpgu.item.RpgUCustomItem;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class SphereOfDiscord extends ConstructableCustomItem implements RpgUCustomItem {

    @Override
    public @NotNull Material getMaterial() {return Material.GUNPOWDER;}

    @Override
    public @NotNull String getRawId() {return "sphere_of_discord";}

    @Override
    protected void generateRecipes(@NotNull Consumer<@NotNull Recipe> consumer) {
        ShapedRecipe recipe = new ShapedRecipe(getNewRecipeKey(), this.getItem());
        recipe.shape(
                "QSS",
                "QNS",
                "QQS");

        RecipeChoice.MaterialChoice quartz = new RecipeChoice.MaterialChoice(Material.QUARTZ);
        RecipeChoice.MaterialChoice netherStar = new RecipeChoice.MaterialChoice(Material.NETHER_STAR);
        RecipeChoice.ExactChoice sphere = new RecipeChoice.ExactChoice(Items.SPHERE_OF_BALANCE.getItem());
        recipe.setIngredient('S', sphere);
        recipe.setIngredient('Q', quartz);
        recipe.setIngredient('N', netherStar);

        consumer.accept(recipe);
    }

    @Override
    public void initializeComponents() {
        super.initializeComponents();

        CustomKeyedAttributeModifier attributeDamage = new CustomKeyedAttributeModifier(new NamespacedKey(RpgU.getInstance(), "base_attack_damage_" + getRawId()), -0.8, AttributeModifier.Operation.MULTIPLY_SCALAR_1, EquipmentSlots.ARTIFACTS);
        setComponent(new VanillaAttributesComponent(new VanillaAttributesContainer.Builder().add(Attribute.ATTACK_DAMAGE, attributeDamage).build()));

        CustomAttributeModifier attributeMP = new CustomAttributeModifier(8, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlots.ARTIFACTS);
        setComponent(new CustomItemAttributesComponent(new CustomAttributesContainer.Builder().add(Attributes.MAGICAL_POTENTIAL, attributeMP).build()));
    }

}
