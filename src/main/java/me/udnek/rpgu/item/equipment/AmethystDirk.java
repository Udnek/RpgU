package me.udnek.rpgu.item.equipment;

import me.udnek.coreu.custom.attribute.CustomAttributeModifier;
import me.udnek.coreu.custom.attribute.CustomAttributesContainer;
import me.udnek.coreu.custom.component.instance.AutoGeneratingFilesItem;
import me.udnek.coreu.custom.component.instance.CustomAttributedItem;
import me.udnek.coreu.custom.component.instance.TranslatableThing;
import me.udnek.coreu.custom.equipment.slot.CustomEquipmentSlot;
import me.udnek.coreu.custom.item.ConstructableCustomItem;
import me.udnek.coreu.custom.item.RepairData;
import me.udnek.rpgu.attribute.Attributes;
import me.udnek.rpgu.attribute.RpgUAttributeUtils;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class AmethystDirk extends ConstructableCustomItem {

    private static final double MELEE_MAGICAL_DAMAGE_MULTIPLIER = 0.2;

    @Override
    public @NotNull Material getMaterial() {
        return Material.STONE_SWORD;
    }

    @Override
    public @NotNull String getRawId() {
        return "amethyst_dirk";
    }

    @Override
    public @Nullable TranslatableThing getTranslations() {
        return TranslatableThing.ofEngAndRu("Amethyst Dirk", "Аметистовый кортик");
    }



    @Override
    protected void modifyFinalItemStack(@NotNull ItemStack itemStack) {
        super.modifyFinalItemStack(itemStack);
        RpgUAttributeUtils.addSuitableAttribute(itemStack, Attribute.ATTACK_DAMAGE, null, -1);
    }

    @Override
    protected void generateRecipes(@NotNull Consumer<Recipe> consumer) {
        ShapedRecipe recipe = new ShapedRecipe(getNewRecipeKey(), this.getItem());
        recipe.shape(
                "A",
                "B");

        RecipeChoice.MaterialChoice amethyst = new RecipeChoice.MaterialChoice(Material.AMETHYST_SHARD);
        RecipeChoice.MaterialChoice bone = new RecipeChoice.MaterialChoice(Material.BONE);
        recipe.setIngredient('A', amethyst);
        recipe.setIngredient('B', bone);

        consumer.accept(recipe);
    }

    @Override
    public void initializeComponents() {
        super.initializeComponents();

        getComponents().set(AutoGeneratingFilesItem.HANDHELD);

        CustomAttributeModifier attribute = new CustomAttributeModifier(MELEE_MAGICAL_DAMAGE_MULTIPLIER,  AttributeModifier.Operation.ADD_NUMBER, CustomEquipmentSlot.MAIN_HAND);
        getComponents().set(new CustomAttributedItem(new CustomAttributesContainer.Builder().add(Attributes.MELEE_MAGICAL_DAMAGE_MULTIPLIER, attribute).build()));
    }

    @Override
    public @Nullable RepairData initializeRepairData() {
        return new RepairData(Material.AMETHYST_SHARD);
    }
}




























