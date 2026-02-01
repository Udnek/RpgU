package me.udnek.rpgu.item.equipment;

import io.papermc.paper.datacomponent.item.Equippable;
import me.udnek.coreu.custom.attribute.AttributeUtils;
import me.udnek.coreu.custom.attribute.CustomAttributeModifier;
import me.udnek.coreu.custom.attribute.CustomAttributesContainer;
import me.udnek.coreu.custom.component.instance.CustomAttributedItem;
import me.udnek.coreu.custom.component.instance.TranslatableThing;
import me.udnek.coreu.custom.equipment.slot.CustomEquipmentSlot;
import me.udnek.coreu.custom.item.ConstructableCustomItem;
import me.udnek.coreu.custom.item.RepairData;
import me.udnek.coreu.rpgu.attribute.RPGUAttributes;
import me.udnek.rpgu.RpgU;
import me.udnek.rpgu.attribute.Attributes;
import me.udnek.rpgu.item.Items;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Set;
import java.util.function.Consumer;

public class EvocationRobe extends ConstructableCustomItem {
    @Override
    public @NotNull String getRawId() {
        return "evocation_robe";
    }

    @Override
    public @Nullable DataSupplier<ItemRarity> getRarity() {
        return DataSupplier.of(ItemRarity.UNCOMMON);
    }

    @Override
    public @NotNull Material getMaterial() {
        return Material.DIAMOND_CHESTPLATE;
    }

    @Override
    public @Nullable TranslatableThing getTranslations() {
        return TranslatableThing.ofEngAndRu("Evocation Robe", "Мантия заклинателя");
    }

    @Override
    public @Nullable DataSupplier<Equippable> getEquippable() {
        Equippable build = Equippable.equippable(getMaterial().getEquipmentSlot()).assetId(new NamespacedKey(RpgU.getInstance(), "evocation_robe")).build();
        return DataSupplier.of(build);
    }

    @Override
    protected void generateRecipes(@NotNull Consumer<Recipe> consumer) {
        ShapedRecipe recipe = new ShapedRecipe(getNewRecipeKey(), getItem());
        recipe.shape(
                "IEI",
                "FLF",
                "FTF");

        recipe.setIngredient('E', new RecipeChoice.MaterialChoice(Material.ENDER_EYE));
        recipe.setIngredient('I', new RecipeChoice.MaterialChoice(Material.IRON_INGOT));
        recipe.setIngredient('F', new RecipeChoice.ExactChoice(Items.FABRIC.getItem()));
        recipe.setIngredient('T', new RecipeChoice.MaterialChoice(Material.TOTEM_OF_UNDYING));
        recipe.setIngredient('L', new RecipeChoice.MaterialChoice(Material.LEATHER_CHESTPLATE));

        consumer.accept(recipe);
    }

    @Override
    public @Nullable RepairData initializeRepairData() {
        return new RepairData(Set.of(Items.FABRIC), Set.of(Material.IRON_INGOT));
    }

    @Override
    public void initializeComponents() {
        super.initializeComponents();
        getComponents().set(new CustomAttributedItem(new CustomAttributesContainer.Builder()
                .add(Attributes.MAGICAL_POTENTIAL, new CustomAttributeModifier(5,  AttributeModifier.Operation.ADD_NUMBER, CustomEquipmentSlot.CHEST))
                .add(RPGUAttributes.ABILITY_COOLDOWN_TIME, new CustomAttributeModifier(-0.3,  AttributeModifier.Operation.MULTIPLY_SCALAR_1, CustomEquipmentSlot.CHEST))
                .add(RPGUAttributes.ABILITY_CAST_RANGE, new CustomAttributeModifier(0.5,  AttributeModifier.Operation.ADD_SCALAR, CustomEquipmentSlot.CHEST))
                .add(Attributes.MAGICAL_DEFENSE_MULTIPLIER, new CustomAttributeModifier(0.5,  AttributeModifier.Operation.ADD_NUMBER, CustomEquipmentSlot.CHEST))
        .build()));
    }

    @Override
    protected void modifyFinalItemStack(@NotNull ItemStack itemStack) {
        super.modifyFinalItemStack(itemStack);
        AttributeUtils.addAttribute(itemStack, Attribute.MAX_HEALTH, new NamespacedKey(RpgU.getInstance(), "chestplate_max_health"), 2, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.CHEST);
        AttributeUtils.addAttribute(itemStack, Attribute.ARMOR, new NamespacedKey(RpgU.getInstance(), "chestplate_armor"), 1, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.CHEST);
    }
}
