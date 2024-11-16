package me.udnek.rpgu.item.equipment;

import me.udnek.itemscoreu.customattribute.CustomAttributesContainer;
import me.udnek.itemscoreu.customcomponent.instance.CustomItemAttributesComponent;
import me.udnek.itemscoreu.customequipmentslot.CustomEquipmentSlot;
import me.udnek.itemscoreu.customitem.ConstructableCustomItem;
import me.udnek.rpgu.attribute.Attributes;
import me.udnek.rpgu.attribute.RpgUAttributeUtils;
import me.udnek.rpgu.equipment.slot.EquipmentSlots;
import me.udnek.rpgu.item.RpgUCustomItem;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.*;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class CeremonialDagger extends ConstructableCustomItem implements RpgUCustomItem {

    public static final double BACKSTAB_DAMAGE_MULTIPLIER = 2;
    public static final double BACKSTAB_DAMAGE_MULTIPLIER_ARTIFACT =  0.75;

    @Override
    public @NotNull Material getMaterial() {
        return Material.DIAMOND_SWORD;
    }
    @Override
    public @NotNull String getRawId() {
        return "ceremonial_dagger";
    }
    @Override
    public ItemFlag[] getTooltipHides() {
        return new ItemFlag[]{ItemFlag.HIDE_ATTRIBUTES};
    }
    @Override
    public boolean getAddDefaultAttributes() {return true;}
    @Override
    protected void modifyFinalItemStack(@NotNull ItemStack itemStack) {
        super.modifyFinalItemStack(itemStack);
        RpgUAttributeUtils.addSuitableAttribute(itemStack, Attribute.ATTACK_DAMAGE, null, -2);
    }

    @Override
    public void initializeComponents() {
        super.initializeComponents();
        setComponent(new CustomItemAttributesComponent(new CustomAttributesContainer.Builder()
                .add(Attributes.BACKSTAB_DAMAGE_MULTIPLIER, BACKSTAB_DAMAGE_MULTIPLIER, AttributeModifier.Operation.ADD_SCALAR, CustomEquipmentSlot.MAIN_HAND)
                .add(Attributes.BACKSTAB_DAMAGE_MULTIPLIER, BACKSTAB_DAMAGE_MULTIPLIER_ARTIFACT, AttributeModifier.Operation.ADD_SCALAR, EquipmentSlots.ARTIFACTS)
                .build()));
    }

    @Override
    protected void generateRecipes(@NotNull Consumer<@NotNull Recipe> consumer) {
        ShapedRecipe recipe = new ShapedRecipe(getNewRecipeKey(), this.getItem());
        recipe.shape(
                "DSG",
                "GSD",
                " T ");

        recipe.setIngredient('D', new RecipeChoice.MaterialChoice(Material.DIAMOND));
        recipe.setIngredient('G', new RecipeChoice.MaterialChoice(Material.GOLD_INGOT));
        recipe.setIngredient('S', new RecipeChoice.MaterialChoice(Material.COBBLESTONE));
        recipe.setIngredient('T', new RecipeChoice.MaterialChoice(Material.STICK));

        consumer.accept(recipe);
    }
}




























