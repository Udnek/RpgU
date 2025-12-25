package me.udnek.rpgu.item.artifact.sphere;

import me.udnek.coreu.custom.attribute.CustomAttributeModifier;
import me.udnek.coreu.custom.attribute.CustomAttributesContainer;
import me.udnek.coreu.custom.attribute.CustomKeyedAttributeModifier;
import me.udnek.coreu.custom.attribute.VanillaAttributesContainer;
import me.udnek.coreu.custom.component.instance.CustomAttributedItem;
import me.udnek.coreu.custom.component.instance.VanillaAttributedItem;
import me.udnek.coreu.custom.item.ConstructableCustomItem;
import me.udnek.rpgu.RpgU;
import me.udnek.rpgu.attribute.Attributes;
import me.udnek.rpgu.equipment.slot.EquipmentSlots;
import me.udnek.rpgu.item.Items;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class SphereOfDiscord extends ConstructableCustomItem {

    private static final double MAGICAL_POTENTIAL = 6;
    private static final double MEELE_MULTIPLIER = -0.2;
    private static final double ATTACK_DAMAGE = -0.6;

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

        CustomKeyedAttributeModifier attributeDamage = new CustomKeyedAttributeModifier(new NamespacedKey(RpgU.getInstance(), "attack_damage_" + getRawId()), ATTACK_DAMAGE, AttributeModifier.Operation.MULTIPLY_SCALAR_1, EquipmentSlots.ARTIFACTS);
        getComponents().set(new VanillaAttributedItem(new VanillaAttributesContainer.Builder().add(Attribute.ATTACK_DAMAGE, attributeDamage).build()));

        CustomAttributeModifier magicalPotential = new CustomAttributeModifier(MAGICAL_POTENTIAL, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlots.ARTIFACTS);
        CustomAttributeModifier meeleMultiplier = new CustomAttributeModifier(MEELE_MULTIPLIER, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlots.ARTIFACTS);
        getComponents().set(new CustomAttributedItem(new CustomAttributesContainer.Builder().add(Attributes.MAGICAL_POTENTIAL, magicalPotential).add(Attributes.MELEE_MAGICAL_DAMAGE_MULTIPLIER, meeleMultiplier).build()));
    }

}
