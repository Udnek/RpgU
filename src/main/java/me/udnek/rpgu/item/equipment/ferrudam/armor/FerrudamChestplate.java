package me.udnek.rpgu.item.equipment.ferrudam.armor;

import me.udnek.itemscoreu.customattribute.AttributeUtils;
import me.udnek.itemscoreu.customitem.ConstructableCustomItem;
import me.udnek.itemscoreu.customrecipe.choice.CustomCompatibleRecipeChoice;
import me.udnek.itemscoreu.customrecipe.choice.CustomSingleRecipeChoice;
import me.udnek.rpgu.RpgU;
import me.udnek.rpgu.item.Items;
import me.udnek.rpgu.item.RpgUCustomItem;
import me.udnek.rpgu.mechanic.alloying.AlloyingRecipe;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Tag;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.components.EquippableComponent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

public class FerrudamChestplate extends ConstructableCustomItem implements RpgUCustomItem, FerrudamArmorItemProperties {
    @Override
    public @NotNull String getRawId() {return "ferrudam_chestplate";}
    @Override
    public @NotNull Material getMaterial() {return Material.DIAMOND_CHESTPLATE;}
    @Override
    public @Nullable EquippableComponent getEquippable() {return FerrudamArmorItemProperties.super.getEquippable();}
    @Override
    protected void generateRecipes(@NotNull Consumer<@NotNull Recipe> consumer) {
        ShapedRecipe recipe = new ShapedRecipe(getNewRecipeKey(), getItem());
        recipe.shape(
                "F F",
                "FFF",
                "FFF");

        RecipeChoice.ExactChoice ferrudam = new RecipeChoice.ExactChoice(Items.FERRUDAM_INGOT.getItem());
        recipe.setIngredient('F', ferrudam);

        consumer.accept(recipe);

        var ingot = new CustomSingleRecipeChoice(Items.FERRUDAM_INGOT);

        AlloyingRecipe recipeAlloy = new AlloyingRecipe(
                getNewRecipeKey(),
                List.of(ingot, ingot, ingot, ingot),
                new CustomCompatibleRecipeChoice(Set.of(), Tag.ITEMS_COALS.getValues()),
                new CustomSingleRecipeChoice(Material.IRON_CHESTPLATE),
                getItem()
        );

        consumer.accept(recipeAlloy);
    }

    @Override
    protected void modifyFinalItemMeta(ItemMeta itemMeta) {
        super.modifyFinalItemMeta(itemMeta);
        AttributeUtils.appendAttribute(itemMeta, Attribute.GENERIC_MAX_HEALTH, new NamespacedKey(RpgU.getInstance(), "max_health_chestplate"), 4, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.CHEST);
        itemMeta.removeAttributeModifier(Attribute.GENERIC_ARMOR);
        AttributeUtils.addAttribute(itemMeta, Attribute.GENERIC_ARMOR, new NamespacedKey(RpgU.getInstance(), "base_armor_chestplate"), 3, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.CHEST);
        itemMeta.removeAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS);
    }
}
